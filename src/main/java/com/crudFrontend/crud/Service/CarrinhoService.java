package com.crudFrontend.crud.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudFrontend.crud.Repository.PessoaRepository;
import com.crudFrontend.crud.Model.Carrinho;
import com.crudFrontend.crud.Model.ItemCarrinho;
import com.crudFrontend.crud.Model.Jogo;
import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Repository.CarrinhoRepository;
import com.crudFrontend.crud.Repository.JogoRepository;

@Service
public class CarrinhoService {
    
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Carrinho buscarOuCriarCarrinho(String cpf){
       Pessoa pessoa = pessoaRepository.findByCpf(cpf)
          .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        return carrinhoRepository.findByPessoa(pessoa)
          .orElseGet(() -> carrinhoRepository.save(new Carrinho(pessoa)));
    }


/* 
    public void adicionarAoCarrinho(String cpf, Long idJogo, int quantidade) {
    Carrinho carrinho = buscarOuCriarCarrinho(emailUsuario);
    Jogo jogo = jogoRepo.findById(idJogo).orElseThrow();

    Optional<ItemCarrinho> itemExistente = carrinho.getItens().stream()
        .filter(i -> i.getJogo().getId().equals(idJogo))
        .findFirst();

    if (itemExistente.isPresent()) {
        itemExistente.get().setQuantidade(itemExistente.get().getQuantidade() + quantidade);
    } else {
        ItemCarrinho novoItem = new ItemCarrinho();
        novoItem.setCarrinho(carrinho);
        novoItem.setJogo(jogo);
        novoItem.setQuantidade(quantidade);
        carrinho.getItens().add(novoItem);
    }

    carrinhoRepo.save(carrinho);
}
*/









    /*
     * public Carrinho buscarOuCriarCarrinho(String emailUsuario) {
        Usuario usuario = usuarioRepo.findByEmail(emailUsuario);
        return carrinhoRepo.findByUsuario(usuario)
            .orElseGet(() -> carrinhoRepo.save(new Carrinho(usuario)));
    }

     */


}
