package com.crudFrontend.crud.DTO;

import java.math.BigDecimal;

public record ItemCarrinhoDTO(Long jogoId, String nomeJogo, String descricao, int quantidade, BigDecimal preco) {
}
