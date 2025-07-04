package com.crudFrontend.crud.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private List<ItemCarrinho> itens = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Pessoa pessoa; // <--- este é o campo que o "findByPessoa" do repository faz referência

    public Carrinho() {

    }

    public Carrinho(Pessoa pessoa) {
        this.pessoa = pessoa;
        this.itens = new ArrayList<>();
    }

    public BigDecimal getTotal() {
       BigDecimal soma = BigDecimal.ZERO;
       for (ItemCarrinho item : itens){
           BigDecimal subtotal = item.getJogo().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
           soma = soma.add(subtotal);
       }
       return soma;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

}
