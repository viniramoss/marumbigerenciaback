package com.example.railway_postgres_app.repository;

import com.example.railway_postgres_app.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {}