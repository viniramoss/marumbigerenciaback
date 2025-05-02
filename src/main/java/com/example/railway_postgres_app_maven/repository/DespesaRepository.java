package com.example.railway_postgres_app_maven.repository;

import com.example.railway_postgres_app_maven.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Added @Repository for clarity, though not strictly required for JpaRepository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {}

