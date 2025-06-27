package com.example.railway_postgres_app.repository;

import com.example.railway_postgres_app.model.DespesaFixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaFixaRepository extends JpaRepository<DespesaFixa, Long> {
    
    List<DespesaFixa> findByMesAno(String mesAno);
    List<DespesaFixa> findByUnidade(String unidade);
    List<DespesaFixa> findByMesAnoAndUnidade(String mesAno, String unidade);
} 