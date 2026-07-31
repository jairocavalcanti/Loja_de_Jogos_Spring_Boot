package com.crudFrontend.crud.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.DTO.PessoaDTO;
import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Service.PessoaService;


@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/getpessoa")
    public List<Pessoa> getAllPessoas() {
        return pessoaService.getAllPessoas();
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable Long id) {
        return pessoaService.getPessoaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/postpessoa")
    public ResponseEntity<PessoaDTO> createPessoa(@RequestBody Pessoa pessoa) {
        Optional<Pessoa> pessoa3 = pessoaService.getByCpfandNome(pessoa.getCpf(), pessoa.getNome());


        if(pessoa3.isPresent()){
            PessoaDTO pessoa2 = new PessoaDTO(null, null, "Pessoa já existe no banco de dados!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(pessoa2);
        }

        if(pessoa.getCpf().length() < 11){
            PessoaDTO pessoa4 = new PessoaDTO(null, null, "cpf inválido!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(pessoa4);
        }

        if(pessoa.getIdade() > 120){
            PessoaDTO pessoa5 = new PessoaDTO(null, null, "Idade inválida!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(pessoa5);
        }

        for(int i = 0; i< pessoa.getCpf().length(); i++){
            char numeros_detectaveis = pessoa.getCpf().charAt(i);
            if(!Character.isDigit(numeros_detectaveis)){
                PessoaDTO pessoa20 = new PessoaDTO(null, null, "cpf nao pode conter caracteres!");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(pessoa20);
            }
        }

        PessoaDTO pessoa2 = new PessoaDTO(pessoa.getNome(), pessoa.getCpf(), "pessoa criada com sucesso!");
        pessoaService.savePessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa2);
    }

    @PutMapping("/updatepessoa/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.updatePessoa(id, pessoa));
    }

    @DeleteMapping("/deletepessoa/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/path")
    public String Teste() {
        return "komarov";
    }
    
}
