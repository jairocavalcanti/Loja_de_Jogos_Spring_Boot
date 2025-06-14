package com.crudFrontend.crud.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.Service.CarrinhoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    
    @Autowired
    private CarrinhoService carrinhoservice;
}
