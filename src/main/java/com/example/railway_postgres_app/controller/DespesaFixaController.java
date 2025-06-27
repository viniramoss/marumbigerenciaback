package com.example.railway_postgres_app.controller;

import com.example.railway_postgres_app.model.DespesaFixa;
import com.example.railway_postgres_app.model.Despesa;
import com.example.railway_postgres_app.repository.DespesaFixaRepository;
import com.example.railway_postgres_app.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/despesas-fixas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DespesaFixaController {

    private final DespesaFixaRepository despesaFixaRepository;
    private final DespesaRepository despesaRepository;

    @PostMapping
    public ResponseEntity<DespesaFixa> criar(@RequestBody DespesaFixa despesaFixa) {
        // Garantir que o mesAno está preenchido
        if (despesaFixa.getMesAno() == null || despesaFixa.getMesAno().isEmpty()) {
            LocalDate agora = LocalDate.now();
            String mesAno = agora.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            despesaFixa.setMesAno(mesAno);
        }
        
        DespesaFixa salva = despesaFixaRepository.save(despesaFixa);
        return ResponseEntity.ok(salva);
    }

    @GetMapping
    public List<DespesaFixa> listar(
            @RequestParam(required = false) String mesAno,
            @RequestParam(required = false) String unidade
    ) {
        if (mesAno != null && unidade != null) {
            return despesaFixaRepository.findByMesAnoAndUnidade(mesAno, unidade);
        } else if (mesAno != null) {
            return despesaFixaRepository.findByMesAno(mesAno);
        } else if (unidade != null) {
            return despesaFixaRepository.findByUnidade(unidade);
        } else {
            return despesaFixaRepository.findAll();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaFixa> buscarPorId(@PathVariable Long id) {
        Optional<DespesaFixa> despesaFixa = despesaFixaRepository.findById(id);
        return despesaFixa.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaFixa> atualizar(@PathVariable Long id, @RequestBody DespesaFixa despesaFixa) {
        if (!despesaFixaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        despesaFixa.setId(id);
        DespesaFixa atualizada = despesaFixaRepository.save(despesaFixa);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!despesaFixaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // 1) Remove a despesa fixa
        despesaFixaRepository.deleteById(id);

        // 2) Remove todas as retiradas relacionadas (fornecedor começando com "DF-{id}-")
        String prefixo = "DF-" + id + "-";
        despesaRepository.deleteByFornecedorStartingWith(prefixo);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/resumo")
    public ResponseEntity<Map<String, Object>> getResumo(@PathVariable Long id) {
        Optional<DespesaFixa> despesaFixaOpt = despesaFixaRepository.findById(id);
        
        if (despesaFixaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        DespesaFixa despesaFixa = despesaFixaOpt.get();
        
        // Buscar todas as despesas com nome que inclui a referência da despesa fixa
        String padraoNome = "DF-" + despesaFixa.getId() + "-";
        List<Despesa> despesasRetiradas = despesaRepository.findAll()
            .stream()
            .filter(d -> d.getFornecedor() != null && d.getFornecedor().contains(padraoNome))
            .toList();
        
        double valorRetirado = despesasRetiradas.stream()
            .mapToDouble(d -> d.getValor() != null ? d.getValor().doubleValue() : 0.0)
            .sum();
        
        double valorRestante = despesaFixa.getValorTotal() - valorRetirado;
        
        Map<String, Object> resumo = new HashMap<>();
        resumo.put("id", despesaFixa.getId());
        resumo.put("nome", despesaFixa.getNome());
        resumo.put("unidade", despesaFixa.getUnidade());
        resumo.put("valorTotal", despesaFixa.getValorTotal());
        resumo.put("valorRetirado", valorRetirado);
        resumo.put("valorRestante", valorRestante);
        resumo.put("mesAno", despesaFixa.getMesAno());
        resumo.put("quantidadeRetiradas", despesasRetiradas.size());
        
        return ResponseEntity.ok(resumo);
    }
} 