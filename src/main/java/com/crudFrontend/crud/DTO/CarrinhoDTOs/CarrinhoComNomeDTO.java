package com.crudFrontend.crud.DTO.CarrinhoDTOs;

import java.math.BigDecimal;

import java.util.List;

import com.crudFrontend.crud.Model.Carrinho;
import com.crudFrontend.crud.Model.Pessoa;

public record CarrinhoComNomeDTO(
        String nomePessoa,
        String cpf,
        BigDecimal total,
        List<ItemCarrinhoDTO> itens) {

    public CarrinhoComNomeDTO(Pessoa pessoa, Carrinho carrinho) {
        this(
                pessoa.getNome(),
                pessoa.getCpf(),
                carrinho.getTotal(),
                carrinho.getItens().stream()
                        .map(item -> new ItemCarrinhoDTO(
                                item.getJogo().getId(),
                                item.getJogo().getNome(),
                                item.getJogo().getDescricao(),
                                item.getQuantidade(),
                                item.getJogo().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()))))
                        .toList());
    }
}
