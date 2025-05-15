package com.example.railway_postgres_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity 
@Table(name = "capital_giro")
@Data
public class CapitalGiro {

  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @PositiveOrZero
  private BigDecimal valor;

  @NotNull
  private LocalDateTime dataAtualizacao;
  
  // Automatically set the update date before persisting
  @PrePersist
  @PreUpdate
  public void prePersist() {
    if (dataAtualizacao == null) {
      dataAtualizacao = LocalDateTime.now();
    }
  }
} 