package com.example.railway_postgres_app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "outros")
public class Outros {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String tipo; // BONUS, FERIAS, DESTAQUE, ABONO, AJUDA_CUSTO, OUTROS
    
    @Column(nullable = false)
    private String descricao;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
    
    @Column
    private Long funcionarioId; // null = geral
    
    @Column(nullable = false)
    private String loja; // UN1, UN2
    
    @Column(nullable = false)
    private String mesAno; // formato YYYY-MM
    
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(nullable = false)
    private LocalDate dataCriacao = LocalDate.now();
    
    @Column(nullable = true)
    private Boolean pago = false;
    
    // Construtores
    public Outros() {}
    
    public Outros(String tipo, String descricao, BigDecimal valor, Long funcionarioId, String loja, String mesAno, String observacoes) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.funcionarioId = funcionarioId;
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
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public BigDecimal getValor() {
        return valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    public Long getFuncionarioId() {
        return funcionarioId;
    }
    
    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
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
    
    public Boolean getPago() {
        return pago != null ? pago : false;
    }
    
    public void setPago(Boolean pago) {
        this.pago = pago;
    }
} 