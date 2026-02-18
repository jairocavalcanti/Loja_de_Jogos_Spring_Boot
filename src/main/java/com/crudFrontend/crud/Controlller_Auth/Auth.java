package com.crudFrontend.crud.Controlller_Auth;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.Records.DTOregistro;
import com.crudFrontend.crud.Records.DTOresposta;
import com.crudFrontend.crud.Records.LoginRequest;
import com.crudFrontend.crud.Records.RespostaErro;
import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Repository.PessoaRepository;
import com.crudFrontend.crud.Security.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class Auth {
    
   // @Autowired
   // private AuthenticationManager authenticationManager;
   
    private AuthenticationManager authenticationManager;

    private final PessoaRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService service;

    public Auth(AuthenticationManager authenticationManager, PessoaRepository repository, PasswordEncoder passwordEncoder, TokenService service) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    // SENHA DO BIORNO: "VALHALLA"

    @PostMapping("/login")
    // ? - generic type (classes, records , lists ...)
    public ResponseEntity<?> login(@RequestBody LoginRequest body) {
        Pessoa user = this.repository.findByCpf(body.cpf()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.senha(), user.getSenha())) {
            String token = this.service.generateToken(user);
            return ResponseEntity.ok(new DTOresposta(user.getNome(), token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RespostaErro("unauthorized"));
    }

    @PostMapping("/register")
    public ResponseEntity<DTOresposta> registraResponseEntity(@RequestBody DTOregistro body) {
        Optional<Pessoa> pessoa = this.repository.findByCpf(body.cpf());
        if(pessoa.isEmpty()){
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
        
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/login2")
    public ResponseEntity<DTOresposta> postMethodName(@RequestBody LoginRequest body) {
        UsernamePasswordAuthenticationToken authtoken= new UsernamePasswordAuthenticationToken(body.gmail(), body.senha());

        Authentication authentication = authenticationManager.authenticate(authtoken);

        Pessoa pessoa = (Pessoa) authentication.getPrincipal();

        String token = service.generateToken(pessoa);
        
        return ResponseEntity.ok(new DTOresposta(token, pessoa.getNome()));
    }
    
    

}
