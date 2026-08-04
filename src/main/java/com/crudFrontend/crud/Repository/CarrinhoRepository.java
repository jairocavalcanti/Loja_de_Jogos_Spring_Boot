package com.crudFrontend.crud.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crudFrontend.crud.Model.Carrinho;
import com.crudFrontend.crud.Model.Pessoa;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    // Esse metodo busca por um carrinho que pertença a pessoa informada
    // Um carrinho cujo campo pessoa seja igual ao objeto Pessoa recebido como argumento
    Optional<Carrinho> findByPessoa(Pessoa pessoa);
}
