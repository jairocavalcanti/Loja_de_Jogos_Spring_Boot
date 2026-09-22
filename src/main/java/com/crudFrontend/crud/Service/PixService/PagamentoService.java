package com.crudFrontend.crud.Service.PixService;

import com.crudFrontend.crud.DTO.PIXdtos.PixResponseDTO;
import com.crudFrontend.crud.Enum.StatusPedido;
import com.crudFrontend.crud.Model.Carrinho;
import com.crudFrontend.crud.Model.ItemCarrinho;
import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Model.Pixmodels.ItemPedido;
import com.crudFrontend.crud.Model.Pixmodels.Pedido;
import com.crudFrontend.crud.Repository.CarrinhoRepository;
import com.crudFrontend.crud.Repository.PessoaRepository;
import com.crudFrontend.crud.Repository.PixRepository.PedidoRepository;
import com.crudFrontend.crud.Service.CarrinhoService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
public class PagamentoService {

    private final PedidoRepository pedidoRepository;
    private final PessoaRepository pessoaRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoService carrinhoService;
    private final MercadoPagoSignatureValidator signatureValidator;

    public PagamentoService(PedidoRepository pedidoRepository,
            PessoaRepository pessoaRepository,
            CarrinhoRepository carrinhoRepository,
            CarrinhoService carrinhoService,
            MercadoPagoSignatureValidator signatureValidator,
            @Value("${mercadopago.access-token}") String accessToken) {
        this.pedidoRepository = pedidoRepository;
        this.pessoaRepository = pessoaRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.carrinhoService = carrinhoService;
        this.signatureValidator = signatureValidator;

        MercadoPagoConfig.setAccessToken(accessToken);
    }

    // 1. Sem @Transactional para não prender a ligação à base de dados durante o
    // pedido HTTP externo
    public PixResponseDTO gerarPixPorCpf(String cpf) {
        Pedido pedido = criarPedidoInicial(cpf);

        try {
            PaymentClient client = new PaymentClient();

            // Expiração configurada para 30 minutos em UTC
            OffsetDateTime dataExpiracao = OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(30);

            String nomeCompleto = pedido.getPessoa().getNome() != null ? pedido.getPessoa().getNome().trim()
                    : "Cliente";
            String primeiroNome = nomeCompleto;
            String sobrenome = "Consumidor"; // fallback caso o usuário só tenha cadastrado o primeiro nome

            int espacoIdx = nomeCompleto.indexOf(" ");
            if (espacoIdx != -1) {
                primeiroNome = nomeCompleto.substring(0, espacoIdx);
                sobrenome = nomeCompleto.substring(espacoIdx + 1).trim();
            }

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(pedido.getValorTotal())
                .description("Pedido #" + pedido.getId())
                .paymentMethodId("pix")
                .dateOfExpiration(dataExpiracao)
                .payer(PaymentPayerRequest.builder()
                        .email(pedido.getPessoa().getGmail())
                        .firstName(primeiroNome)
                        .lastName(sobrenome)
                        .identification(
                                com.mercadopago.client.common.IdentificationRequest.builder()
                                        .type("CPF")
                                        .number(cpf.replaceAll("\\D", ""))
                                        .build())
                        .build())
                .build();

        Payment payment = client.create(paymentCreateRequest);

        String txId = String.valueOf(payment.getId());
        String qrCodeBase64 = payment.getPointOfInteraction().getTransactionData().getQrCodeBase64();
        String copiaECola = payment.getPointOfInteraction().getTransactionData().getQrCode();

        vincularTransactionId(pedido.getId(), txId);

        return new PixResponseDTO(
                pedido.getId(),
                pedido.getValorTotal(),
                txId,
                qrCodeBase64,
                copiaECola);

    } catch (com.mercadopago.exceptions.MPApiException e) {
        System.err.println(">>> CÓDIGO DE STATUS DO MERCADO PAGO: " + e.getStatusCode());
        System.err.println(">>> RESPOSTA DETALHADA DA API: " + e.getApiResponse().getContent());
        throw new RuntimeException("Erro retornado pelo Mercado Pago: " + e.getApiResponse().getContent(), e);
    } catch (Exception e) {
        throw new RuntimeException("Erro ao gerar PIX com a administradora de pagamento: " + e.getMessage(), e);
    }
    }

    // 2. Transação rápida apenas para persistir o pedido inicial
    @Transactional
    public Pedido criarPedidoInicial(String cpf) {
        Pessoa pessoa = pessoaRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o CPF informado!"));

        Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado para esta pessoa!"));

        if (carrinho.getItens() == null || carrinho.getItens().isEmpty()) {
            throw new RuntimeException("O carrinho está vazio!");
        }

        BigDecimal valorTotal = carrinho.getItens().stream()
                .map(item -> item.getJogo().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Pedido pedido = new Pedido();
        pedido.setPessoa(pessoa);
        pedido.setValorTotal(valorTotal);
        pedido.setStatus(StatusPedido.PENDENTE);

        for (ItemCarrinho itemCarrinho : carrinho.getItens()) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setJogo(itemCarrinho.getJogo());
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());
            itemPedido.setPrecoUnitario(itemCarrinho.getJogo().getPreco());
            pedido.getItens().add(itemPedido);
        }

        return pedidoRepository.save(pedido);
    }

    // 3. Transação rápida para associar o ID retornado pelo Mercado Pago
    @Transactional
    public void vincularTransactionId(Long pedidoId, String transactionId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + pedidoId));
        pedido.setTransactionId(transactionId);
        pedidoRepository.save(pedido);
    }

    public void processarWebhook(Map<String, Object> payload, String xSignature, String xRequestId) {
        System.out.println(">>> Webhook recebido! Payload: " + payload);

        String action = (String) payload.get("action");
        String type = (String) payload.get("type");

        boolean isPaymentEvent = "payment.created".equalsIgnoreCase(action)
                || "payment.updated".equalsIgnoreCase(action)
                || "payment".equalsIgnoreCase(type);

        if (!isPaymentEvent) {
            System.out.println(">>> Evento ignorado (não é de pagamento): " + action + " / " + type);
            return;
        }

        Map<String, Object> data = (Map<String, Object>) payload.get("data");
        if (data == null || data.get("id") == null) {
            System.out.println(">>> Payload não contém data.id");
            return;
        }

        String transactionId = String.valueOf(data.get("id"));
        System.out.println(">>> Transaction ID recebido: " + transactionId);

        if (!signatureValidator.isAssinaturaValida(xSignature, xRequestId, transactionId)) {
            System.err.println(">>> Assinatura do Webhook INVÁLIDA! Requisição rejeitada.");
            return;
        }

        try {
            PaymentClient client = new PaymentClient();
            Payment payment = client.get(Long.parseLong(transactionId));

            System.out.println(">>> Status do Pagamento no Mercado Pago: " + payment.getStatus());

            if ("approved".equalsIgnoreCase(payment.getStatus())) {
                confirmarPagamento(transactionId);
            } else {
                System.out.println(">>> Pagamento ainda não foi APROVADO no MP. Status atual: " + payment.getStatus());
            }
        } catch (Exception e) {
            System.err.println(">>> Erro no Webhook: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void confirmarPagamento(String transactionId) {
        pedidoRepository.findByTransactionId(transactionId).ifPresentOrElse(pedido -> {
            if (pedido.getStatus() == StatusPedido.PAGO) {
                System.out.println(">>> Pedido #" + pedido.getId() + " já estava marcado como PAGO. Ignorando.");
                return;
            }

            pedido.setStatus(StatusPedido.PAGO);
            pedidoRepository.save(pedido);
            carrinhoService.ExcluirCarrinho(pedido.getPessoa().getCpf());
            System.out.println(">>> Sucesso: Pedido #" + pedido.getId() + " alterado para PAGO!");
        }, () -> {
            System.out.println(">>> ALERTA: Nenhum pedido encontrado no banco com transactionId = " + transactionId);
        });
    }

    public StatusPedido consultarStatus(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .map(Pedido::getStatus)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));
    }
}