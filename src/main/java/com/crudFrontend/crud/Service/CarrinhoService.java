package com.crudFrontend.crud.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

  private final CarrinhoRepository carrinhoRepository;

  private final JogoRepository jogoRepository;

  private final PessoaRepository pessoaRepository;

  CarrinhoService(CarrinhoRepository carrinhoRepository, PessoaRepository pessoaRepository,
      JogoRepository jogoRepository) {
    this.carrinhoRepository = carrinhoRepository;
    this.pessoaRepository = pessoaRepository;
    this.jogoRepository = jogoRepository;
  }

  public CarrinhoComNomeDTO buscarCarrinho(String cpf) {
    Pessoa pessoa = pessoaRepository.findByCpf(cpf)
        .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

    Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
        .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

    List<ItemCarrinhoDTO> itensDTO = new ArrayList<>();

    for (ItemCarrinho item : carrinho.getItens()) {

      Jogo jogo = item.getJogo();

      itensDTO.add(new ItemCarrinhoDTO(
          jogo.getId(),
          jogo.getNome(),
          jogo.getDescricao(),
          item.getQuantidade(),
          jogo.getPreco()));
    }

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

    return "Carrinho criado!";
  }

  public void adicionarAoCarrinho(String cpf, Long Idjogo, int quantidade) {

    Pessoa pessoa = pessoaRepository.findByCpf(cpf)
        .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

    Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
        .orElseGet(() -> carrinhoRepository.save(new Carrinho(pessoa)));

    Jogo jogo = jogoRepository.findById(Idjogo)
        .orElseThrow(() -> new RuntimeException("Jogo não encontrado!"));

    ItemCarrinho itemExistente = null;

    for (ItemCarrinho item : carrinho.getItens()) {
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
        carrinho.getItens().remove(itemExistente);
      }
      carrinhoRepository.save(carrinho);
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
