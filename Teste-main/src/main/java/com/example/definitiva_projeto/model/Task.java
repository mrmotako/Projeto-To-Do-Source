package com.example.definitiva_projeto.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    private Boolean feito = false;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "finalizado_em")
    private LocalDateTime finalizadoEm;

    // Construtor padrão (obrigatório para JPA)
    public Task() {}

    // Construtor útil
    public Task(String nome) {
        this.nome = nome;
        this.feito = false;
        this.dataCriacao = LocalDateTime.now(); // ✅ trocado de new Date()
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getFeito() {
        return feito;
    }

    public void setFeito(Boolean feito) {
        this.feito = feito;
    }

    public LocalDateTime getDataCriacao() { // ✅ trocado de Date
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) { // ✅ trocado de Date
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getFinalizadoEm() { // ✅ trocado de Date
        return finalizadoEm;
    }

    public void setFinalizadoEm(LocalDateTime finalizadoEm) { // ✅ trocado de Date
        this.finalizadoEm = finalizadoEm;
    }
}