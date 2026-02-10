package com.crudFrontend.crud.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudFrontend.crud.Model.Jogo;
import com.crudFrontend.crud.ResponseDTOS.JogoResponseDTO;
import com.crudFrontend.crud.Service.JogoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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

    @GetMapping("/getjogobynome/{nome}")
    public ResponseEntity<JogoResponseDTO> getMethodName(@PathVariable String nome) {
        Optional<Jogo> jogo = jogoservice.getJogoByNome(nome);

        if (jogo.isPresent()) {
            JogoResponseDTO dto = new JogoResponseDTO("jogo encontrado!", jogo.get() );
            return ResponseEntity.ok(dto);
        } else {
            JogoResponseDTO dto2 = new JogoResponseDTO("jogo não encontrado", null);
            return ResponseEntity.status(404).body(dto2);
            //  return ResponseEntity.ok(dto2);
        }
    }

    @PostMapping("/postjogo")
    public ResponseEntity<Jogo> createJogo(@RequestBody Jogo jogo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jogoservice.savejogo(jogo));
    }

    @PutMapping("/updateJogo/{id}")
    public ResponseEntity<Jogo> updateJogo(@PathVariable Long id, @RequestBody Jogo jogo) {
        return ResponseEntity.ok(jogoservice.updateJogo(id, jogo));
    }

    @DeleteMapping("/deleteJogo/{id}")
    public ResponseEntity<Void> deleteJogo(@PathVariable Long id) {
        jogoservice.deleteJogo(id);
        return ResponseEntity.noContent().build();
    }
}
