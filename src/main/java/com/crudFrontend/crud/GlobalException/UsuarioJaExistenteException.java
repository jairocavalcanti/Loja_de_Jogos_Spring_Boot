package com.crudFrontend.crud.GlobalException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsuarioJaExistenteException extends RuntimeException {

    public UsuarioJaExistenteException(String mensagem) {
        super(mensagem);
    }

}
