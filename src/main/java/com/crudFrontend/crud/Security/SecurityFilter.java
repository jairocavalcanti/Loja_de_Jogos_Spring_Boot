package com.crudFrontend.crud.Security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Repository.PessoaRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    PessoaRepository pessoaRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // definindo liberação da rota /pessoas
        // "startswith" trabalha com qualquer rota dentro de '/pessoa'
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = recoverToken(request);

        if (token != null) {
            String login = tokenService.validateToken(token);

            if (login != null) {
                // implementar FINDBYEMAIL()
                // consulta deve receber o mesmo corpo passado para o token na classe token
                // service com "withSubjetc."
                Pessoa user = pessoaRepository.findByCpf(login)
                        .orElseThrow(() -> new RuntimeException("Usuário nao encontrado!"));

                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        // armazena header authorization da requisição
        var authHeader = request.getHeader("Authorization");
        // se o header authorization for = null
        if (authHeader == null)
            // é retornado null
            return null;
        // se não, o 'bearer' da requisição é substituído por uma string vazia
        return authHeader.replace("Bearer", "").trim();
    }

}
