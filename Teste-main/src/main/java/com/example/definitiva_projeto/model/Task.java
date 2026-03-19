package com.example.definitiva_projeto.model;

import jakarta.persistence.*;
import java.util.Date;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name = "finalizado_em")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finalizadoEm;

    // Construtor padrão (obrigatório para JPA)
    public Task() {}

    // Construtor útil
    public Task(String nome) {
        this.nome = nome;
        this.feito = false;
        this.dataCriacao = new Date();
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

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getFinalizadoEm() {
        return finalizadoEm;
    }

    public void setFinalizadoEm(Date finalizadoEm) {
        this.finalizadoEm = finalizadoEm;
    }
}