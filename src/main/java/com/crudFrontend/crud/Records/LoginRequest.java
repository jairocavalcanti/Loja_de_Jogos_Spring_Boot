package com.crudFrontend.crud.Records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(String cpf, @NotBlank @Email String gmail, String senha) {
    
}
