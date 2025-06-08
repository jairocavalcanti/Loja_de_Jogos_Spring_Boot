package com.crudFrontend.crud.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.Model.Jogo;
import com.crudFrontend.crud.Service.JogoService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/jogos")
public class JogoController {

    @Autowired
    private JogoService jogo;

    @GetMapping("/getjogos")
    public List<Jogo> getAllJogos(){
        return jogo.getAllJogos();
    }
    
}
