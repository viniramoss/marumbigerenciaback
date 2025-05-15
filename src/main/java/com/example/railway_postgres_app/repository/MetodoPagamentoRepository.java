package com.example.railway_postgres_app.repository;

import com.example.railway_postgres_app.model.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Long> {
    // Find payment method by its code
    Optional<MetodoPagamento> findByCodigo(String codigo);
} 