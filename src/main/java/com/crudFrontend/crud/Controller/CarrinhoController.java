package com.crudFrontend.crud.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.DTO.CarrinhoComNomeDTO;
import com.crudFrontend.crud.Service.CarrinhoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoservice;

    @GetMapping("/{cpf}")
    public ResponseEntity<CarrinhoComNomeDTO> visualizarCarrinho(@PathVariable String cpf) {
        CarrinhoComNomeDTO carrinho = carrinhoservice.buscarCarrinho(cpf);
        return ResponseEntity.ok(carrinho);
    }

    /// metodo criarCarrinho alterado para retornar apenas mensagem de criação ou não
    @PostMapping("/postcarrinho/{cpf}")
    public ResponseEntity<String> CriarCarrinho(@PathVariable String cpf) {
        String carrinho = carrinhoservice.CriarCarrinho(cpf);
        return ResponseEntity.ok(carrinho);
    }

    @PostMapping("/{cpf}/adicionar") 
    public ResponseEntity<Void> adicionarItem(@PathVariable String cpf,
            @RequestParam Long idJogo,
            @RequestParam int quantidade) {
        carrinhoservice.adicionarAoCarrinho(cpf, idJogo, quantidade);
        return ResponseEntity.ok().build();
    }

}
