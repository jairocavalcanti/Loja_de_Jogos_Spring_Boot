package com.crudFrontend.crud.Security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.crudFrontend.crud.Repository.PessoaRepository;

@Component
public class OneUserDetailsService implements UserDetailsService {

    private final PessoaRepository repository;

    OneUserDetailsService(PessoaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // nao é necessaria mais nenhuma implementação aqui pois a implementação ja está na model de Pessoa
        return this.repository.findByCpf(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
    }
}
