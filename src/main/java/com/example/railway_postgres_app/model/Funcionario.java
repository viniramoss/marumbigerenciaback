package com.example.railway_postgres_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionarios")
public class Funcionario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private Double salario;
    
    @Column(nullable = false)
    private String loja;
    
    @Column(nullable = true)
    private Boolean pago = false;
    
    // Construtores
    public Funcionario() {}
    
    public Funcionario(String nome, Double salario, String loja) {
        this.nome = nome;
        this.salario = salario;
        this.loja = loja;
        this.pago = false;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }
    
    public String getLoja() { return loja; }
    public void setLoja(String loja) { this.loja = loja; }
    
    public Boolean getPago() { return pago != null ? pago : false; }
    public void setPago(Boolean pago) { this.pago = pago; }
} 