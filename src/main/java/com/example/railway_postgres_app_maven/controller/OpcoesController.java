package com.example.railway_postgres_app_maven.controller;

import com.example.railway_postgres_app_maven.model.Despesa;
import com.example.railway_postgres_app_maven.service.DespesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/opcoes") // Base path for options endpoints
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OpcoesController {

    private final DespesaService despesaService;

    @GetMapping("/unidades")
    public List<String> getUnidades() {
        return despesaService.listar().stream()
                .map(Despesa::getUnidade)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @GetMapping("/tipos")
    public List<String> getTipos() {
        return despesaService.listar().stream()
                .map(Despesa::getTipo)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
    
    @GetMapping("/bancos")
    public List<String> getBancos() {
        return despesaService.listar().stream()
                .map(Despesa::getBanco)
                .filter(b -> b != null && !b.isBlank()) // Filter out null or blank banks
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @GetMapping("/metodos")
    public List<String> getMetodos() {
        return despesaService.listar().stream()
                .map(Despesa::getMetodo)
                .filter(m -> m != null && !m.isBlank()) // Filter out null or blank methods
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}

