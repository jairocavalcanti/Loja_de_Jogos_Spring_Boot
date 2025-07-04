package com.crudFrontend.crud.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.DTO.CarrinhoComNomeDTO;
import com.crudFrontend.crud.DTO.CarrinhoComPessoaDTO;
import com.crudFrontend.crud.Model.Carrinho;
import com.crudFrontend.crud.Service.CarrinhoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoservice;

    @PostMapping("/{cpf}")
    public ResponseEntity<CarrinhoComPessoaDTO> createCarrinho(@PathVariable String cpf) {
        CarrinhoComPessoaDTO carrinho = carrinhoservice.buscarOuCriarCarrinho(cpf);
        return ResponseEntity.ok(carrinho);
    }

    @PostMapping("/postcarrinho/{cpf}")
    public ResponseEntity<CarrinhoComNomeDTO> createCarrinhoComNome(@PathVariable String cpf) {
        CarrinhoComNomeDTO carrinho = carrinhoservice.buscarOuCriarCarrinhoNome(cpf);
        return ResponseEntity.ok(carrinho);
    }

}
