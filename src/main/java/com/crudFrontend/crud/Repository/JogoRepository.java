package com.crudFrontend.crud.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crudFrontend.crud.Model.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long> {
    Optional<Jogo> findByNome(String nome);
}
