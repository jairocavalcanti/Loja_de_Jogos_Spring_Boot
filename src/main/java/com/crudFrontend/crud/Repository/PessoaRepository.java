package com.crudFrontend.crud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudFrontend.crud.Model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
    
}
