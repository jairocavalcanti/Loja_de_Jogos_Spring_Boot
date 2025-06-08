package com.crudFrontend.crud.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crudFrontend.crud.Model.Carrinho;
import com.crudFrontend.crud.Model.Pessoa;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Optional<Carrinho> findByUsuario(Pessoa pessoa);
}
