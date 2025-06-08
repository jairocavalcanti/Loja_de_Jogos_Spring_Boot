package com.crudFrontend.crud.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudFrontend.crud.Model.Jogo;
import com.crudFrontend.crud.Repository.JogoRepository;

@Service
public class JogoService {

    @Autowired
    JogoRepository jogoRepository;

    public List<Jogo> getAllJogos() {
        return jogoRepository.findAll();
    }
}
