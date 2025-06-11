package com.crudFrontend.crud.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudFrontend.crud.Model.Jogo;
import com.crudFrontend.crud.Repository.JogoRepository;

@Service
public class JogoService {

    @Autowired
    JogoRepository jogoRepository;

    public List<Jogo> getAllJogos() {
        return jogoRepository.findAll();
    }

    public Optional<Jogo> getJogoById(Long id){
        return jogoRepository.findById(id);
    }

    public Jogo savejogo(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    public Jogo updateJogo(Long id, Jogo jogoAtualizado){
        Jogo jogo = jogoRepository.findById(id).
             orElseThrow(() -> new RuntimeException("jogo não encontrado!"));
        jogo.setNome(jogoAtualizado.getNome());
        jogo.setDescrição(jogoAtualizado.getDescricao());
        jogo.setPreco(jogoAtualizado.getPreco());
        return jogoRepository.save(jogoAtualizado);
    }

    public void deleteJogo(Long id){
        jogoRepository.deleteById(id);
    }

}
