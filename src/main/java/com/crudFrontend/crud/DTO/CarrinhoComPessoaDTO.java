package com.crudFrontend.crud.DTO;

import com.crudFrontend.crud.Model.Carrinho;
import com.crudFrontend.crud.Model.Pessoa;

public class CarrinhoComPessoaDTO {
    
    private Pessoa pessoa;
    private Carrinho carrinho;
    

    public CarrinhoComPessoaDTO(){

    }

    public CarrinhoComPessoaDTO(Pessoa pessoa, Carrinho carrinho) {
        this.pessoa = pessoa;
        this.carrinho = carrinho;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }
 
    
}
