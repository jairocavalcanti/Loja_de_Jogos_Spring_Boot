package com.crudFrontend.crud.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.crudFrontend.crud.Model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
    // o metodo abaixo executa a query automaticamente através do JpaRepository
    // o padrão de nome "findby" é determinante para que a consulta ocorra de forma correta 
    /*SELECT * FROM pessoa WHERE cpf = ? AND nome = ?; */
    Optional<Pessoa> findByCpfAndNome(String cpf, String nome);
    Optional<Pessoa> findByCpf(String cpf);
}
