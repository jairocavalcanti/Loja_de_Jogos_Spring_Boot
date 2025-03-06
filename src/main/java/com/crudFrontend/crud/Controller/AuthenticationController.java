package com.crudFrontend.crud.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.DTO.LoginRequest;
import com.crudFrontend.crud.Service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/autenticar")
    public String login(@RequestBody LoginRequest Loginrequest){
        boolean autenticado = authenticationService.autenticar(Loginrequest.getCpf(), Loginrequest.getNome());
        return autenticado ? "Login bem-sucedido!" : "Usuário não encontrado!";
    }
}
