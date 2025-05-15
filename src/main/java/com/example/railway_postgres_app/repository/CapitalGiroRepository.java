package com.example.railway_postgres_app.repository;

import com.example.railway_postgres_app.model.CapitalGiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapitalGiroRepository extends JpaRepository<CapitalGiro, Long> {
    // Find the most recent capital giro entry
    CapitalGiro findTopByOrderByDataAtualizacaoDesc();
} 