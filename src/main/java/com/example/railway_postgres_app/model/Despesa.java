package com.example.railway_postgres_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity 
@Table(name = "despesa")
@Data
public class Despesa {

  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private LocalDate data;

  private String fornecedor;
  private String tipo;
  private String unidade;
  
  // Novos campos para banco e método de pagamento
  private String banco;
  private String metodo;

  @NotNull 
  @PositiveOrZero
  private BigDecimal valor;

  @NotNull
  private Boolean pago = false;

  // Getters e Setters para corrigir os erros de compilação
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public String getFornecedor() {
    return fornecedor;
  }

  public void setFornecedor(String fornecedor) {
    this.fornecedor = fornecedor;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getUnidade() {
    return unidade;
  }

  public void setUnidade(String unidade) {
    this.unidade = unidade;
  }

  public String getBanco() {
    return banco;
  }

  public void setBanco(String banco) {
    this.banco = banco;
  }

  public String getMetodo() {
    return metodo;
  }

  public void setMetodo(String metodo) {
    this.metodo = metodo;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public Boolean getPago() {
    return pago;
  }

  public void setPago(Boolean pago) {
    this.pago = pago;
  }
}