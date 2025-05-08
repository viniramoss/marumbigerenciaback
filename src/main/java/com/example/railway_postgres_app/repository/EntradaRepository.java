package com.example.railway_postgres_app.repository;

import com.example.railway_postgres_app.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    // Método para buscar entradas por período
    List<Entrada> findByDataBetween(LocalDate inicio, LocalDate fim);
    
    // Método para buscar entradas por unidade
    List<Entrada> findByUnidade(String unidade);
}