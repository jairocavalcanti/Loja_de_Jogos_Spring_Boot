package com.crudFrontend.crud.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Repository.PessoaRepository;

@Service
public class CadastroService {
    
    @Autowired
    private PessoaRepository pessoaRepository;

    public CadastroService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa savePessoa(Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }
}
