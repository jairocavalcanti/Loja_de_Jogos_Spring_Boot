package com.crudFrontend.crud.DTO.CarrinhoDTOs;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemCarrinhoDTO(
    @NotNull(message = "O ID do jogo é obrigatório")
    Long jogoId, 
    String nomeJogo, 
    String descricao, 
    @Min(value = 1, message = "A quantidade deve ser de no mínimo 1")
    int quantidade, 
    BigDecimal preco) {
}
