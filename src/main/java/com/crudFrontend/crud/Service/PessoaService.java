package com.crudFrontend.crud.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudFrontend.crud.Model.Pessoa;
import com.crudFrontend.crud.Repository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> getAllPessoas(){
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> getByCpfandNome(String cpf, String nome){
        return pessoaRepository.findByCpfAndNome(cpf, nome);
    }

    //A implementação de um optional define que o metodo pode ou não ter retorno
    public Optional<Pessoa> getPessoaById(Long id){
        return pessoaRepository.findById(id);
    }
    
    public Pessoa savePessoa(Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }

    public Pessoa updatePessoa(Long id, Pessoa pessoaAtualizada){
        Pessoa pessoa = pessoaRepository.findById(id).
               orElseThrow(() -> new RuntimeException("Pessoa não encontrada!"));
        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setIdade(pessoaAtualizada.getIdade());
        pessoa.setCpf(pessoaAtualizada.getCpf());
        pessoa.setSenha(pessoaAtualizada.getSenha());
        pessoa.setGmail(pessoaAtualizada.getGmail());
        return pessoaRepository.save(pessoa);
    }

    public void deletePessoa(Long id){
        pessoaRepository.deleteById(id);
    }

}
