package com.crudFrontend.crud.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudFrontend.crud.Model.Jogo;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {
    Optional<Jogo> findByNome(String nome);
}
