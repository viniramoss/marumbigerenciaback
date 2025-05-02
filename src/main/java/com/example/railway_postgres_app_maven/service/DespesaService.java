package com.example.railway_postgres_app_maven.service;

import com.example.railway_postgres_app_maven.model.Despesa;
import com.example.railway_postgres_app_maven.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok creates constructor with final fields
public class DespesaService {

  private final DespesaRepository repo;

  public Despesa salvar(Despesa d) {
    // Add any business logic before saving if needed
    return repo.save(d);
  }

  public List<Despesa> listar() {
    return repo.findAll();
  }

  public void excluir(Long id) {
    repo.deleteById(id);
  }
}

