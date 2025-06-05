package com.example.railway_postgres_app.repository;

import com.example.railway_postgres_app.model.Outros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OutrosRepository extends JpaRepository<Outros, Long> {
    List<Outros> findByLoja(String loja);
    List<Outros> findByMesAno(String mesAno);
    List<Outros> findByLojaAndMesAno(String loja, String mesAno);
    List<Outros> findByTipo(String tipo);
    List<Outros> findByFuncionarioId(Long funcionarioId);
} 