package com.crudFrontend.crud.DTO.PessoaDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DTOverificacaocadastro (
    
    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotBlank(message = "O CPF é obrigatório")
    // Garante exatamente 11 dígitos numéricos (sem letras nem símbolos)
    @Pattern(regexp = "^\\d{11}$", message = "O CPF deve conter exatamente 11 dígitos numéricos")
    String cpf,

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 0, message = "A idade não pode ser negativa")
    @Max(value = 120, message = "Idade inválida (máximo 120)")
    Integer idade,

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Insira um formato de e-mail válido")
    String gmail,

    @NotBlank(message = "A senha é obrigatória")
    String senha
){}

