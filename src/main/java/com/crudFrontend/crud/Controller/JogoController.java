package com.crudFrontend.crud.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.Model.Jogo;
import com.crudFrontend.crud.Service.JogoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    @Autowired
    private JogoService jogoservice;

    @GetMapping("/getjogos")
    public List<Jogo> getAllJogos() {
        return jogoservice.getAllJogos();
    }

    @GetMapping("/getjogobyid/{id}")
    public ResponseEntity<Jogo> getJogoById(@PathVariable Long id) {
        return jogoservice.getJogoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/postjogo")
    public ResponseEntity<Jogo> createJogo(@RequestBody Jogo jogo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jogoservice.savejogo(jogo));
    }

}
