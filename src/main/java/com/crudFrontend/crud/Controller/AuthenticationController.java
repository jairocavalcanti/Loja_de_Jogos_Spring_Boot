package com.crudFrontend.crud.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.DTO.LoginRequest;
import com.crudFrontend.crud.Service.AuthenticationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/autenticar")
    public ResponseEntity<String> login(@RequestBody LoginRequest Loginrequest){
        boolean autenticado = authenticationService.autenticar(Loginrequest.getCpf(), Loginrequest.getNome());
        if(autenticado != false){
            return ResponseEntity.ok("Login bem sucedido!");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha no login! Usuário não encontrado!");
        }
    }
}
