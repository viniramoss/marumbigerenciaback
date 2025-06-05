package com.example.railway_postgres_app.repository;

import com.example.railway_postgres_app.model.Encargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EncargoRepository extends JpaRepository<Encargo, Long> {
    List<Encargo> findByLoja(String loja);
    List<Encargo> findByMesAno(String mesAno);
    List<Encargo> findByLojaAndMesAno(String loja, String mesAno);
    List<Encargo> findByTipo(String tipo);
} 