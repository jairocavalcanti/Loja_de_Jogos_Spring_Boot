package com.crudFrontend.crud.DTO.CarrinhoDTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemCarrinhoRequestDTO(    
  @NotNull(message = "O ID do jogo é obrigatório") Long jogoId,
  @Min(value = 1, message = "A quantidade deve ser de no mínimo 1") int quantidade
) {}
