package com.example.railway_postgres_app.service;

import com.example.railway_postgres_app.model.Despesa;
import com.example.railway_postgres_app.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
  
  public List<Map<String, Object>> getVariaveisAgrupadas(String unidade, String dataInicio, String dataFim) {
    List<Despesa> todasDespesas = repo.findAll();
    
    // Filtrar apenas despesas variáveis
    List<Despesa> variaveisFiltered = todasDespesas.stream()
        .filter(d -> "variavel".equalsIgnoreCase(d.getTipo()))
        .filter(d -> unidade == null || unidade.equalsIgnoreCase(d.getUnidade()))
        .filter(d -> {
            if (dataInicio == null && dataFim == null) return true;
            
            LocalDate despesaData = d.getData();
            if (despesaData == null) return false;
            
            if (dataInicio != null) {
                LocalDate inicio = LocalDate.parse(dataInicio);
                if (despesaData.isBefore(inicio)) return false;
            }
            
            if (dataFim != null) {
                LocalDate fim = LocalDate.parse(dataFim);
                if (despesaData.isAfter(fim)) return false;
            }
            
            return true;
        })
        .collect(Collectors.toList());
    
    // Agrupar por fornecedor e somar valores
    Map<String, Map<String, Object>> agrupamento = variaveisFiltered.stream()
        .collect(Collectors.groupingBy(
            Despesa::getFornecedor,
            Collectors.collectingAndThen(
                Collectors.toList(),
                despesas -> {
                    Map<String, Object> dados = new HashMap<>();
                    BigDecimal valorTotal = despesas.stream()
                        .map(Despesa::getValor)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                    
                    dados.put("fornecedor", despesas.get(0).getFornecedor());
                    dados.put("tipo", "variavel");
                    dados.put("valor", valorTotal);
                    dados.put("unidade", despesas.get(0).getUnidade());
                    dados.put("pago", true); // Variáveis são sempre pagas
                    dados.put("banco", "N/A");
                    dados.put("metodo", "dinheiro");
                    dados.put("quantidade", despesas.size());
                    
                    // Data mais recente
                    Optional<LocalDate> dataRecente = despesas.stream()
                        .map(Despesa::getData)
                        .filter(Objects::nonNull)
                        .max(LocalDate::compareTo);
                    
                    dados.put("data", dataRecente.orElse(LocalDate.now()));
                    
                    return dados;
                }
            )
        ));
    
    return new ArrayList<>(agrupamento.values());
  }
}