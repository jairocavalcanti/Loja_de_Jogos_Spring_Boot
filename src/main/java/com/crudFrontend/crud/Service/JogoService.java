package com.crudFrontend.crud.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudFrontend.crud.Repository.JogoRepository;

@Service
public class JogoService {
    
    @Autowired
    JogoRepository jogoRepository;

}
