package com.crudFrontend.crud.DTO.PessoaDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DTOverificacaologin(
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Insira um formato de e-mail válido")
    String gmail,

    @NotBlank(message = "A senha é obrigatória")
    String senha
) {
}