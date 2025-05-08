package com.example.railway_postgres_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity 
@Table(name = "entrada")
@Data
public class Entrada {

  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private LocalDate data;

  @NotNull
  private String operador;
  
  @NotNull
  private String unidade;
  
  // Valores por método de pagamento
  private BigDecimal dinheiro = BigDecimal.ZERO;
  private BigDecimal debito = BigDecimal.ZERO;
  private BigDecimal credito = BigDecimal.ZERO;
  private BigDecimal pix = BigDecimal.ZERO;
  private BigDecimal voucher = BigDecimal.ZERO;
  
  // Total calculado
  @NotNull
  private BigDecimal total = BigDecimal.ZERO;
  
  // Método para calcular o total a partir dos valores individuais
  @PrePersist
  @PreUpdate
  public void calcularTotal() {
    this.total = BigDecimal.ZERO;
    if (this.dinheiro != null) this.total = this.total.add(this.dinheiro);
    if (this.debito != null) this.total = this.total.add(this.debito);
    if (this.credito != null) this.total = this.total.add(this.credito);
    if (this.pix != null) this.total = this.total.add(this.pix);
    if (this.voucher != null) this.total = this.total.add(this.voucher);
  }
}