package com.crudFrontend.crud.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudFrontend.crud.Model.Carrinho;
import com.crudFrontend.crud.Model.Pessoa;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    // Esse metodo busca por um carrinho que pertença a pessoa informada
    // Um carrinho cujo campo pessoa seja igual ao objeto Pessoa recebido como argumento
    Optional<Carrinho> findByPessoa(Pessoa pessoa);
}
