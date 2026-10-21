package com.crudFrontend.crud.Controlller_Auth;

import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.DTO.CarrinhoDTOs.CarrinhoComNomeDTO;
import com.crudFrontend.crud.DTO.CarrinhoDTOs.ItemCarrinhoRequestDTO;
import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Service.CarrinhoService;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoservice;

    CarrinhoController(CarrinhoService carrinhoservice) {
        this.carrinhoservice = carrinhoservice;
    }

    @GetMapping
    public ResponseEntity<CarrinhoComNomeDTO> visualizarCarrinho(@AuthenticationPrincipal Pessoa pessoa) {
        CarrinhoComNomeDTO carrinho = carrinhoservice.buscarCarrinho(pessoa);
        return ResponseEntity.ok(carrinho);
    }

    @PostMapping("/adicionar") 
    public ResponseEntity<Void> adicionarItem(
        @AuthenticationPrincipal Pessoa usuarioLogado,
        @RequestBody @Valid ItemCarrinhoRequestDTO dto ) {
        carrinhoservice.adicionarAoCarrinho(usuarioLogado, dto.jogoId(), dto.quantidade());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/excluirItem/{idjogo}")
    public ResponseEntity<Void> excluirItem(@AuthenticationPrincipal Pessoa usuarioLogado,
            @PathVariable Long idjogo,
            @RequestParam(defaultValue = "1") int quantidade) {
        carrinhoservice.ExcluirItemCarrinho(usuarioLogado, idjogo, quantidade);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/excluirCarrinho")
    public ResponseEntity<Void> excluircarrinho(@AuthenticationPrincipal Pessoa usuarioLogado) {
        carrinhoservice.ExcluirCarrinho(usuarioLogado);
        return ResponseEntity.noContent().build();
    }

}



