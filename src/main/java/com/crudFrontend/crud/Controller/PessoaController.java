package com.crudFrontend.crud.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Service.PessoaService;


@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping()
    public List<Pessoa> getAllPessoas() {
        return pessoaService.getAllPessoas();
    }

    @GetMapping("/getbyid/{id}")
    // tipo ResponseEntity é utilizado para retornar status HTTP
    // no caso abaixo devemos tratar de forma coesa pois o metodo retorna um tipo
    // optional
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable Long id) {
        return pessoaService.getPessoaById(id)
                // .map já faz a verificação se o Optional contém um valor
                // Se o Optional estiver presente, o método .map executa a função e retorna um
                // HTTP status 200 (OK) com o valor no corpo
                // o uso do metodo map deixa o codigo mais limpo, removendo a necessidade de IF
                // ou ispresent()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/postpessoa")
    public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) {
        // o método retorna um status HTTP de objeto criado juntamente com o corpo do
        // objeto que foi criado
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.savePessoa(pessoa));
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
