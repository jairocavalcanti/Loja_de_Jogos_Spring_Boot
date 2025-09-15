package com.crudFrontend.crud.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private Integer idade;
   
    @Column(nullable = false, unique = true)
    private String cpf;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    /*import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PessoaService, Pessoa } from '../../services/pessoa.service';

@Component({
  selector: 'app-pessoa-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pessoa-list.html',
  styleUrls: ['./pessoa-list.css']
})
export class PessoaList implements OnInit {

  pessoas: Pessoa[] = [];
  carregando = true;

  constructor(private pessoaService: PessoaService) {}

  ngOnInit(): void {
    this.pessoaService.getPessoas().subscribe({
      next: (data) => {
        this.pessoas = data;
        this.carregando = false;
      },
      error: (err) => {
        console.error('Erro ao carregar pessoas', err);
        this.carregando = false;
      }
    });
  }
}
 */


}
