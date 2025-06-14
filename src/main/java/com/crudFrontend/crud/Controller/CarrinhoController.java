package com.crudFrontend.crud.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.DTO.CarrinhoComPessoaDTO;
import com.crudFrontend.crud.Service.CarrinhoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    
    @Autowired
    private CarrinhoService carrinhoservice;

    @PostMapping("/postcarrinho/{cpf}")
    public ResponseEntity<CarrinhoComPessoaDTO> createCarrinho(@PathVariable String cpf) {
       CarrinhoComPessoaDTO carrinho = carrinhoservice.buscarOuCriarCarrinho(cpf);
       return ResponseEntity.ok(carrinho);
    }

}
