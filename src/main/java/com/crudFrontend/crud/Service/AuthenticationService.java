package com.crudFrontend.crud.Service;

import org.springframework.stereotype.Service;

import com.crudFrontend.crud.Repository.PessoaRepository;

@Service
public class AuthenticationService {
    
    private PessoaRepository pessoaRepository;

    public AuthenticationService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public boolean autenticar(String cpf, String nome){
        return pessoaRepository.findByCpfAndNome(cpf, nome).isPresent();
    }
}
