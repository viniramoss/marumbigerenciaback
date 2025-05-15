package com.example.railway_postgres_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity 
@Table(name = "metodo_pagamento")
@Data
public class MetodoPagamento {

  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min = 1, max = 50)
  private String codigo; // Campo identificador único, equivalente ao id no JSON
  
  @NotNull
  @Size(min = 1, max = 100)
  private String nome; // Campo texto de exibição, equivalente ao label no JSON
} 