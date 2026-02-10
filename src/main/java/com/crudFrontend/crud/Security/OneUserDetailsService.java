package com.crudFrontend.crud.Security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Repository.PessoaRepository;

@Component
public class OneUserDetailsService implements UserDetailsService {
    
   
    @Autowired
    private PessoaRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         
        Pessoa usuario = this.repository.findByCpf(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //implementando interface user details com objeto "User"
        //-- arraylist serve como autorização para pessoas credenciadas
        // Ele define o que o usuário pode fazer no sistema (ex: ROLE_ADMIN, ROLE_USER, PODE_DELETAR)
        return new org.springframework.security.core.userdetails.User(usuario.getCpf(), usuario.getSenha(), new ArrayList<>());
        // return new org.springframework.secutiry.core.userdetails.Pessoa(usuario.getNome(), usuario.getCpf(), new ArrayList<>());
    }

    
  
}
