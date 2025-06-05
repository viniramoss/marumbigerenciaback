package com.example.railway_postgres_app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "encargos")
public class Encargo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String tipo; // INSS, FGTS, VALE_TRANSPORTE
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
    
    @Column(nullable = false)
    private String loja; // UN1, UN2
    
    @Column(nullable = false)
    private String mesAno; // formato YYYY-MM
    
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(nullable = false)
    private LocalDate dataCriacao = LocalDate.now();
    
    // Construtores
    public Encargo() {}
    
    public Encargo(String tipo, BigDecimal valor, String loja, String mesAno, String observacoes) {
        this.tipo = tipo;
        this.valor = valor;
        this.loja = loja;
        this.mesAno = mesAno;
        this.observacoes = observacoes;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public BigDecimal getValor() {
        return valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    public String getLoja() {
        return loja;
    }
    
    public void setLoja(String loja) {
        this.loja = loja;
    }
    
    public String getMesAno() {
        return mesAno;
    }
    
    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
} 