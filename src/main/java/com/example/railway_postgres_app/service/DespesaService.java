package com.example.railway_postgres_app.service;

import com.example.railway_postgres_app.model.Despesa;
import com.example.railway_postgres_app.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DespesaService {

  private final DespesaRepository repo;

  public Despesa salvar(Despesa d) { 
    return repo.save(d); 
  }
  
  public List<Despesa> listar() { 
    return repo.findAll(); 
  }
  
  public void excluir(Long id) { 
    repo.deleteById(id); 
  }
}