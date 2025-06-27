package com.example.railway_postgres_app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "despesas_fixas")
public class DespesaFixa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String unidade;
    
    @Column(nullable = false)
    private Double valorTotal;
    
    @Column(nullable = false)
    private LocalDate dataCriacao = LocalDate.now();
    
    @Column(nullable = false)
    private String mesAno; // formato "2024-01"
    
    // Construtores
    public DespesaFixa() {}
    
    public DespesaFixa(String nome, String unidade, Double valorTotal, String mesAno) {
        this.nome = nome;
        this.unidade = unidade;
        this.valorTotal = valorTotal;
        this.mesAno = mesAno;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public String getMesAno() { return mesAno; }
    public void setMesAno(String mesAno) { this.mesAno = mesAno; }
} 