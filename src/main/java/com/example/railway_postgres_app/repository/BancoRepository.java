package com.example.railway_postgres_app.repository;

import com.example.railway_postgres_app.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {
    // Find bank by its code
    Optional<Banco> findByCodigo(String codigo);
} 