package com.crudFrontend.crud.Controlller_Auth;


//import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.DTO.Records.DTOregistro;
import com.crudFrontend.crud.DTO.Records.DTOresposta;
import com.crudFrontend.crud.DTO.Records.LoginRequest;
import com.crudFrontend.crud.DTO.Records.RespostaErro;
import com.crudFrontend.crud.GlobalException.UsuarioJaExistenteException;
import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Repository.PessoaRepository;
import com.crudFrontend.crud.Security.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class Auth {
    
    private final PessoaRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService service;

    public Auth(AuthenticationManager authenticationManager, PessoaRepository repository, PasswordEncoder passwordEncoder, TokenService service) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.service = service;
    }
 
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest body) {
        Pessoa user = this.repository.findByCpfAndGmail(body.cpf(), body.gmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.senha(), user.getSenha()) && user.getGmail() != null) {
            String token = this.service.generateToken(user);
            return ResponseEntity.ok(new LoginRequest(user.getCpf(), user.getGmail(), token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RespostaErro("Login não autorizado, verifique email ou senha!"));
    }

    @PostMapping("/register")
    public ResponseEntity<DTOresposta> registraResponseEntity(@RequestBody DTOregistro body) {
        this.repository.findByCpf(body.cpf()).ifPresent(p -> {throw new UsuarioJaExistenteException("Usuário Já existente!"); });
            Pessoa nova_pessoa = new Pessoa();
            nova_pessoa.setSenha(passwordEncoder.encode(body.senha()));
            nova_pessoa.setCpf(body.cpf());
            nova_pessoa.setNome(body.nome());
            nova_pessoa.setIdade(body.idade());
            nova_pessoa.setGmail(body.gmail());
            this.repository.save(nova_pessoa);
            String token = this.service.generateToken(nova_pessoa);
            return ResponseEntity.ok(new DTOresposta(nova_pessoa.getNome(), token));

    }

}
