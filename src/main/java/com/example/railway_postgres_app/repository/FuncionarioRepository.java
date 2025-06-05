package com.example.railway_postgres_app.repository;

import com.example.railway_postgres_app.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    
    List<Funcionario> findByLoja(String loja);
    
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
    
    List<Funcionario> findByLojaAndNomeContainingIgnoreCase(String loja, String nome);
} 