package com.crudFrontend.crud.DTO;

import com.crudFrontend.crud.Model.Carrinho;

public class CarrinhoComNomeDTO {
    
    private String nome;
    private String cpf;
    private Carrinho carrinho;

    
    public CarrinhoComNomeDTO(){

    }

    public CarrinhoComNomeDTO(String nome, Carrinho carrinho,String cpf) {
        this.nome = nome;
        this.carrinho = carrinho;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
