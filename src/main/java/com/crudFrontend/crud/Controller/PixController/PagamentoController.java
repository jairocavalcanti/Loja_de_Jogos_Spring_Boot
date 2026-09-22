package com.crudFrontend.crud.Controller.PixController;

import com.crudFrontend.crud.DTO.PIXdtos.PixRequestDTO;
import com.crudFrontend.crud.DTO.PIXdtos.PixResponseDTO;
import com.crudFrontend.crud.Enum.StatusPedido;
import com.crudFrontend.crud.Service.PixService.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin(origins = "http://localhost:5173")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/pix")
    public ResponseEntity<PixResponseDTO> criarPix(@RequestBody PixRequestDTO request) {
        PixResponseDTO response = pagamentoService.gerarPixPorCpf(request.cpf());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{pedidoId}")
    public ResponseEntity<Map<String, String>> checarStatus(@PathVariable Long pedidoId) {
        StatusPedido status = pagamentoService.consultarStatus(pedidoId);
        return ResponseEntity.ok(Map.of("status", status.name()));
    }

    @PostMapping("/webhook/pix")
    public ResponseEntity<Void> receberWebhook(
            @RequestBody Map<String, Object> payload,
            @RequestHeader(value = "x-signature", required = false) String xSignature,
            @RequestHeader(value = "x-request-id", required = false) String xRequestId) {

        pagamentoService.processarWebhook(payload, xSignature, xRequestId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/teste/confirmar/{txId}")
    public ResponseEntity<String> testeConfirmar(@PathVariable String txId) {
        pagamentoService.confirmarPagamento(txId);
        return ResponseEntity.ok("Pedido confirmado!");
    }

}