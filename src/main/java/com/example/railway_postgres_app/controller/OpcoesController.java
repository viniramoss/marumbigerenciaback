package com.example.railway_postgres_app.controller;

import com.example.railway_postgres_app.model.Banco;
import com.example.railway_postgres_app.model.CapitalGiro;
import com.example.railway_postgres_app.model.MetodoPagamento;
import com.example.railway_postgres_app.repository.BancoRepository;
import com.example.railway_postgres_app.repository.CapitalGiroRepository;
import com.example.railway_postgres_app.repository.MetodoPagamentoRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/opcoes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OpcoesController {

    private final BancoRepository bancoRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final CapitalGiroRepository capitalGiroRepository;

    @GetMapping
    public ResponseEntity<Map<String, List<OpcaoDTO>>> getOpcoes() {
        Map<String, List<OpcaoDTO>> opcoes = new HashMap<>();
        
        // Converter bancos para DTOs
        List<OpcaoDTO> bancos = bancoRepository.findAll().stream()
            .map(banco -> new OpcaoDTO(banco.getCodigo(), banco.getNome()))
            .collect(Collectors.toList());
        
        // Converter métodos de pagamento para DTOs
        List<OpcaoDTO> metodos = metodoPagamentoRepository.findAll().stream()
            .map(metodo -> new OpcaoDTO(metodo.getCodigo(), metodo.getNome()))
            .collect(Collectors.toList());
        
        opcoes.put("bancos", bancos);
        opcoes.put("metodos", metodos);
        
        return ResponseEntity.ok(opcoes);
    }

    @PostMapping
    public ResponseEntity<Map<String, List<OpcaoDTO>>> saveOpcoes(@RequestBody Map<String, List<OpcaoDTO>> opcoes) {
        try {
            if (opcoes.containsKey("bancos")) {
                List<OpcaoDTO> bancos = opcoes.get("bancos");
                
                // Limpar bancos existentes e adicionar os novos
                for (OpcaoDTO opcaoDTO : bancos) {
                    bancoRepository.findByCodigo(opcaoDTO.getId())
                        .ifPresentOrElse(
                            banco -> {
                                banco.setNome(opcaoDTO.getLabel());
                                bancoRepository.save(banco);
                            },
                            () -> {
                                Banco novoBanco = new Banco();
                                novoBanco.setCodigo(opcaoDTO.getId());
                                novoBanco.setNome(opcaoDTO.getLabel());
                                bancoRepository.save(novoBanco);
                            }
                        );
                }
            }
            
            if (opcoes.containsKey("metodos")) {
                List<OpcaoDTO> metodos = opcoes.get("metodos");
                
                // Limpar métodos existentes e adicionar os novos
                for (OpcaoDTO opcaoDTO : metodos) {
                    metodoPagamentoRepository.findByCodigo(opcaoDTO.getId())
                        .ifPresentOrElse(
                            metodo -> {
                                metodo.setNome(opcaoDTO.getLabel());
                                metodoPagamentoRepository.save(metodo);
                            },
                            () -> {
                                MetodoPagamento novoMetodo = new MetodoPagamento();
                                novoMetodo.setCodigo(opcaoDTO.getId());
                                novoMetodo.setNome(opcaoDTO.getLabel());
                                metodoPagamentoRepository.save(novoMetodo);
                            }
                        );
                }
            }
            
            return ResponseEntity.ok(opcoes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/salvar-capital")
    public ResponseEntity<Map<String, Object>> salvarCapitalGiro(@RequestBody CapitalGiroDTO capitalDto) {
        try {
            // Criar nova entrada de capital de giro
            CapitalGiro capitalGiro = new CapitalGiro();
            capitalGiro.setValor(BigDecimal.valueOf(capitalDto.getCapitalGiro()));
            capitalGiro.setDataAtualizacao(LocalDateTime.now());
            
            capitalGiroRepository.save(capitalGiro);
            
            Map<String, Object> response = new HashMap<>();
            response.put("capitalGiro", capitalDto.getCapitalGiro());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/capital-giro")
    public ResponseEntity<Map<String, Object>> getCapitalGiro() {
        // Buscar o capital de giro mais recente
        CapitalGiro capitalGiro = capitalGiroRepository.findTopByOrderByDataAtualizacaoDesc();
        
        Map<String, Object> response = new HashMap<>();
        if (capitalGiro != null) {
            response.put("capitalGiro", capitalGiro.getValor());
        } else {
            response.put("capitalGiro", 0.0);
        }
        
        return ResponseEntity.ok(response);
    }

    @Data
    public static class OpcaoDTO {
        private String id;
        private String label;
        
        public OpcaoDTO() {}
        
        public OpcaoDTO(String id, String label) {
            this.id = id;
            this.label = label;
        }
    }
    
    @Data
    public static class CapitalGiroDTO {
        private Double capitalGiro;
    }
}