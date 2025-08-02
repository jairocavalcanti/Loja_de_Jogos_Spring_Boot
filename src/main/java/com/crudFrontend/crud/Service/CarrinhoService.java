package com.crudFrontend.crud.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudFrontend.crud.Repository.PessoaRepository;
import com.crudFrontend.crud.DTO.CarrinhoComNomeDTO;
import com.crudFrontend.crud.DTO.ItemCarrinhoDTO;
import com.crudFrontend.crud.Model.Carrinho;
import com.crudFrontend.crud.Model.ItemCarrinho;
import com.crudFrontend.crud.Model.Jogo;
import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Repository.CarrinhoRepository;
import com.crudFrontend.crud.Repository.JogoRepository;

@Service
public class CarrinhoService {

  @Autowired
  private CarrinhoRepository carrinhoRepository;

  @Autowired
  private JogoRepository jogoRepository;

  @Autowired
  private PessoaRepository pessoaRepository;

  // metodo destinado a pesquisa de carrinho por CPF
  public CarrinhoComNomeDTO buscarCarrinho(String cpf) {
    Pessoa pessoa = pessoaRepository.findByCpf(cpf)
        .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

    Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
        .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

    List<ItemCarrinhoDTO> itensDTO = new ArrayList<>();

    // para cada objeto ItemCarrinho "item" na lista: carrinho.getItens()
    for (ItemCarrinho item : carrinho.getItens()) {
      // novo objeto jogo armazenando jogo presente na lista de itens
      Jogo jogo = item.getJogo();
      // lista de DTO de itens adicionando novo objeto dto na lista tipo ItemCarrinho
      // com atributos
      // de objeto jogo para visualização
      // abaixo estamos adicionando um DTO do objeto jogo dentro de uma lista do tipo
      // item carrinho DTO
      itensDTO.add(new ItemCarrinhoDTO(
          jogo.getId(),
          jogo.getNome(),
          jogo.getDescricao(),
          item.getQuantidade(),
          jogo.getPreco()));
    }

    // finalmente retornando um novo DTO que possui a lista dos DTOS de jogo feita
    // anteriormente (linha acima)
    // Esse DTO serve para visualização tanto do usuario que adicionou o item no
    // carrinho quanto que para a visualização
    // do total (valor) e dos itens adicionados
    return new CarrinhoComNomeDTO(
        pessoa.getNome(),
        pessoa.getCpf(),
        carrinho.getTotal(),
        itensDTO);
  }

  public String CriarCarrinho(String cpf) {
    Pessoa pessoa = pessoaRepository.findByCpf(cpf)
        .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

    Optional<Carrinho> carrinhoexistente = carrinhoRepository.findByPessoa(pessoa);

    if (carrinhoexistente.isPresent()) {
      return "carrinho já existe para usuário <<" + pessoa.getNome() + ">> !";
    } else {
      carrinhoRepository.save(new Carrinho(pessoa));
    }
    /*
     * Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
     * .orElseGet(() -> carrinhoRepository.save(new Carrinho(pessoa)));
     */
    return "Carrinho criado!";
  }

  // metodo publico para adicionar jogo ao carrinho
  public void adicionarAoCarrinho(String cpf, Long Idjogo, int quantidade) {
    // variavel do tipo pessoa armazenando objeto pessoa identificado com o cpf
    // fornecido na assinatura do metodo
    // caso a pessoa não seja encontra uma excessão será executada com a mensagem
    // abaixo
    Pessoa pessoa = pessoaRepository.findByCpf(cpf)
        .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

    // variavel do tipo carrinho armazenando objeto carrinho associado a um objeto
    // pessoa
    // esse objeto pessoa é um atributo da classe carrinho e é recebido como
    // argumento no construtor da classe
    // o metodo findByPessoa é um optional criado para que a pessoa associada seja
    // encontrada
    // esse metodo personalizado só é possivel graças ao recebimento de pessoa no
    // construtor da classe carrinho
    // caso a pessoa seja encontrada ela é armazenada e caso contrario será criado
    // um novo objeto de carrinho
    Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
        .orElseGet(() -> carrinhoRepository.save(new Carrinho(pessoa)));

    // variavel jogo criada para armazenar jogo encontrado por ID
    // metodo findById ja existe na JPA, podemos utiliza-lo passando apenas um ID
    // tipo long
    // caso o jogo seja encontrado ele será armazenado e caso contrário uma exceção
    // será lançada
    Jogo jogo = jogoRepository.findById(Idjogo)
        .orElseThrow(() -> new RuntimeException("Jogo não encontrado!"));

    // variavel ItemCarrinho sendo criada inicialmente com valor null
    ItemCarrinho itemExistente = null;
    // abaixo uma iteração sobre todos os itens presentes na lista de itens do
    // carrinho em questão
    // a lista de itens do carrinho é populada com objetos do tipo itemcarrinho e
    // por isso deve
    // ser iterada desta maneira
    for (ItemCarrinho item : carrinho.getItens()) {
      // objetos do tipo item carrinho possuem atributos tipo jogo em suas classes
      // caso o id recebido como argumento no metodo, a variavel itemexistente vai
      // armazenar esse jogo
      if (item.getJogo().getId().equals(Idjogo)) {
        itemExistente = item;
        break;
      }
    }

    if (itemExistente != null) {
      itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
    } else {
      ItemCarrinho novoItem = new ItemCarrinho();
      novoItem.setCarrinho(carrinho);
      novoItem.setJogo(jogo);
      novoItem.setQuantidade(quantidade);
      carrinho.getItens().add(novoItem);
    }
    carrinhoRepository.save(carrinho);
  }

  public void ExcluirItemCarrinho(String cpf, Long Idjogo, int quantidade) {

    Pessoa pessoa = pessoaRepository.findByCpf(cpf)
        .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

    Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
        .orElseGet(() -> carrinhoRepository.save(new Carrinho(pessoa)));

    ItemCarrinho itemExistente = null;

    for (ItemCarrinho item : carrinho.getItens()) {
      if (item.getJogo().getId().equals(Idjogo)) {
        itemExistente = item;
        break;
      }
    }

    if (itemExistente != null) {
      int novaQuantidade = itemExistente.getQuantidade() - quantidade;

      if (novaQuantidade > 0) {
        itemExistente.setQuantidade(novaQuantidade);
      } else {
        carrinho.getItens().remove(itemExistente); // Remove o item do carrinho
      }
      carrinhoRepository.save(carrinho); // Salva o carrinho atualizado
    }
  }

  public void ExcluirCarrinho(String cpf) {

    Pessoa pessoa = pessoaRepository.findByCpf(cpf)
        .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

    Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
        .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

    carrinhoRepository.delete(carrinho);
  }

}
