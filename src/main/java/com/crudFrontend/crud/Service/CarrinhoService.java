package com.crudFrontend.crud.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.crudFrontend.crud.DTO.CarrinhoDTOs.CarrinhoComNomeDTO;
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

  CarrinhoService(CarrinhoRepository carrinhoRepository,
      JogoRepository jogoRepository) {
    this.carrinhoRepository = carrinhoRepository;
    this.jogoRepository = jogoRepository;
  }

  @Transactional
  public CarrinhoComNomeDTO buscarCarrinho(Pessoa pessoa) {
    Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
        .orElseGet(() -> carrinhoRepository.save(new Carrinho(pessoa)));
    return new CarrinhoComNomeDTO(pessoa, carrinho);
  }


  @Transactional
  public void adicionarAoCarrinho(Pessoa pessoa, Long Idjogo, int quantidade) {

    Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
        .orElseGet(() -> carrinhoRepository.save(new Carrinho(pessoa)));

    Jogo jogo = jogoRepository.findById(Idjogo)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogo não encontrado!"));

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

  @Transactional 
  public void ExcluirItemCarrinho(Pessoa pessoa, Long Idjogo, int quantidade) {
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


  @Transactional 
  public void ExcluirCarrinho(Pessoa pessoa) {

    Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrinho não encontrado"));

    carrinho.getItens().clear();
    carrinhoRepository.save(carrinho);
  }

}
