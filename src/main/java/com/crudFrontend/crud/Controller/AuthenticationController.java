package com.crudFrontend.crud.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.DTO.LoginRequest;
import com.crudFrontend.crud.Service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

// a anotação restcontroller define a classe como um controller
@RestController
// a anotação requestmapping é responsável por definir a rota de acesso aos metodos da classe
@RequestMapping("/login")
public class AuthenticationController {
    // a anotação autowired é responsavel por ?
    @Autowired
    private AuthenticationService authenticationService;

    // @postmapping vai garantir que para a rota definida, o metodo retornado será do tipo POST
    @PostMapping("/autenticar")
    public String login(@RequestBody LoginRequest Loginrequest){
        boolean autenticado = authenticationService.autenticar(Loginrequest.getCpf(), Loginrequest.getNome());
        // "?" abaixo se trata de um operador ternário e ele é responsável por decidir qual resposta retornar com base no valor booleano
        // se o valor em questão for 'True', retornará "login bem sucedido" caso contrário 'False' retornará "Usuário nao encontrado!"
        return autenticado ? "Login bem-sucedido!" : "Usuário não existente na base de dados!";
    }
}
