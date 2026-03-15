package com.crudFrontend.crud.GlobalException;

public class UsuarioJaExistenteException extends RuntimeException {
   
    public UsuarioJaExistenteException(String mensagem){
       super(mensagem);
   }    

}
