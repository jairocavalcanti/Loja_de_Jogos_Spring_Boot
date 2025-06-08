package com.crudFrontend.crud.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudFrontend.crud.Model.Carrinho;
import com.crudFrontend.crud.Model.Pessoa;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Optional<Carrinho> findByUsuario(Pessoa pessoa);
}
