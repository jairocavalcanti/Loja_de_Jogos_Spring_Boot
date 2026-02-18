package com.crudFrontend.crud.Security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.crudFrontend.crud.Model.Pessoa;

@Service
public class TokenService {
    

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Pessoa pessoa){
        try {
            
            // Algorithm - biblioteca java-jwt (Auth0)
            // HMAC256 - define o algoritmo de assinatura do token 
            // HMAC256 - algoritmo definido para ser o padrão de criação da assinatura token
            // Secret - 'chave secreta' definida em application.properties 
            Algorithm algorithm = Algorithm.HMAC256(secret);

            
            String token = JWT.create()
                   .withIssuer("Crud_SP_Front_End")
                   .withSubject(pessoa.getCpf())
                   .withExpiresAt(this.generateExpirationDate())
                   .sign(algorithm);
        return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                   .withIssuer("Crud_SP_Front_End")
                   .build()
                   .verify(token)
                   .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now()
               .plusHours(2)
               .toInstant(ZoneOffset.of("-3"));
    }


}
