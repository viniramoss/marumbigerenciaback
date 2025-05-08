package com.example.railway_postgres_app.service;

import com.example.railway_postgres_app.model.Entrada;
import com.example.railway_postgres_app.repository.EntradaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntradaService {

  private final EntradaRepository repo;

  public Entrada salvar(Entrada e) { 
    // Garantir que o total seja calculado antes de salvar
    e.calcularTotal();
    return repo.save(e); 
  }
  
  public List<Entrada> listar() { 
    return repo.findAll(); 
  }
  
  public List<Entrada> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
    return repo.findByDataBetween(inicio, fim);
  }
  
  public List<Entrada> listarPorUnidade(String unidade) {
    return repo.findByUnidade(unidade);
  }
  
  public Entrada buscarPorId(Long id) {
    return repo.findById(id).orElseThrow(() -> 
        new RuntimeException("Entrada não encontrada com o ID: " + id));
  }
  
  public void excluir(Long id) { 
    repo.deleteById(id); 
  }
}