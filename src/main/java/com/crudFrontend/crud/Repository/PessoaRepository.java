package com.crudFrontend.crud.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.crudFrontend.crud.Model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
    Optional<Pessoa> findByCpfAndNome(String cpf, String nome);
}
