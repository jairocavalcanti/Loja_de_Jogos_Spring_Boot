package com.crudFrontend.crud.GlobalException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crudFrontend.crud.DTO.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handlerRuntimeException(RuntimeException ex){
       ErrorResponseDTO erro = new ErrorResponseDTO("Erro ao processar requisição", ex.getMessage());
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

}
