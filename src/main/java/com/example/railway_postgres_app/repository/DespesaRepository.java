package com.example.railway_postgres_app.repository;

import com.example.railway_postgres_app.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    // Remove todas as despesas cujo fornecedor comece com o prefixo informado (ex: "DF-123-")
    @Modifying
    @Query("delete from Despesa d where d.fornecedor like concat(:prefix, '%')")
    void deleteByFornecedorStartingWith(@Param("prefix") String prefix);
}