package com.crudFrontend.crud.DTO;

import java.math.BigDecimal;

import java.util.List;

public record CarrinhoComNomeDTO(String nomePessoa, String cpf, BigDecimal total, List<ItemCarrinhoDTO> itens) {
}
