package com.crudFrontend.crud.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudFrontend.crud.Repository.PessoaRepository;
import com.crudFrontend.crud.DTO.CarrinhoComPessoaDTO;
import com.crudFrontend.crud.Model.Carrinho;
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

    public CarrinhoComPessoaDTO buscarOuCriarCarrinho(String cpf){
       Pessoa pessoa = pessoaRepository.findByCpf(cpf)
          .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        Carrinho carrinho = carrinhoRepository.findByPessoa(pessoa)
          .orElseGet(() -> carrinhoRepository.save(new Carrinho(pessoa)));

          return new CarrinhoComPessoaDTO(pessoa,carrinho);
    }


}
