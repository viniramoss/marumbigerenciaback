package com.example.railway_postgres_app.controller;

import com.example.railway_postgres_app.model.Banco;
import com.example.railway_postgres_app.model.CapitalGiro;
import com.example.railway_postgres_app.model.MetodoPagamento;
import com.example.railway_postgres_app.repository.BancoRepository;
import com.example.railway_postgres_app.repository.CapitalGiroRepository;
import com.example.railway_postgres_app.repository.MetodoPagamentoRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping("/api/opcoes")
@CrossOrigin(origins = "*")
@Slf4j
public class OpcoesController {

    private static final String OPCOES_FILE = "opcoes.json";
    private final ObjectMapper mapper = new ObjectMapper();

    // Repositories are now autowired instead of final to avoid constructor failures
    @Autowired
    private BancoRepository bancoRepository;
    
    @Autowired
    private MetodoPagamentoRepository metodoPagamentoRepository;
    
    @Autowired
    private CapitalGiroRepository capitalGiroRepository;
    
    // Fallback to file-based approach if database is not working yet
    private boolean useFileBasedFallback = false;

    @GetMapping
    public ResponseEntity<Map<String, List<OpcaoDTO>>> getOpcoes() {
        Map<String, List<OpcaoDTO>> opcoes = new HashMap<>();
        
        try {
            if (!useFileBasedFallback) {
                // Try to get from database first
                List<OpcaoDTO> bancos = bancoRepository.findAll().stream()
                    .map(banco -> new OpcaoDTO(banco.getCodigo(), banco.getNome()))
                    .collect(Collectors.toList());
                
                List<OpcaoDTO> metodos = metodoPagamentoRepository.findAll().stream()
                    .map(metodo -> new OpcaoDTO(metodo.getCodigo(), metodo.getNome()))
                    .collect(Collectors.toList());
                
                opcoes.put("bancos", bancos);
                opcoes.put("metodos", metodos);
            } else {
                // Fallback to file-based approach
                opcoes = loadOptions();
            }
        } catch (Exception e) {
            log.error("Error accessing database. Using file-based fallback", e);
            useFileBasedFallback = true;
            opcoes = loadOptions();
        }
        
        return ResponseEntity.ok(opcoes);
    }

    @PostMapping
    public ResponseEntity<Map<String, List<OpcaoDTO>>> saveOpcoes(@RequestBody Map<String, List<OpcaoDTO>> opcoes) {
        try {
            if (!useFileBasedFallback) {
                if (opcoes.containsKey("bancos")) {
                    List<OpcaoDTO> bancos = opcoes.get("bancos");
                    
                    // Process banks
                    for (OpcaoDTO opcaoDTO : bancos) {
                        try {
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
                        } catch (Exception e) {
                            log.error("Error saving bank: " + opcaoDTO.getId(), e);
                        }
                    }
                }
                
                if (opcoes.containsKey("metodos")) {
                    List<OpcaoDTO> metodos = opcoes.get("metodos");
                    
                    // Process payment methods
                    for (OpcaoDTO opcaoDTO : metodos) {
                        try {
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
                        } catch (Exception e) {
                            log.error("Error saving payment method: " + opcaoDTO.getId(), e);
                        }
                    }
                }
            } else {
                // Fallback to file-based approach
                File file = new File(OPCOES_FILE);
                mapper.writeValue(file, opcoes);
            }
            
            return ResponseEntity.ok(opcoes);
        } catch (Exception e) {
            log.error("Error saving options", e);
            useFileBasedFallback = true;
            
            // Try file-based fallback
            try {
                File file = new File(OPCOES_FILE);
                mapper.writeValue(file, opcoes);
            } catch (IOException ex) {
                log.error("Error saving to file", ex);
                return ResponseEntity.internalServerError().build();
            }
            
            return ResponseEntity.ok(opcoes);
        }
    }

    @PostMapping("/salvar-capital")
    public ResponseEntity<Map<String, Object>> salvarCapitalGiro(@RequestBody CapitalGiroDTO capitalDto) {
        try {
            if (!useFileBasedFallback) {
                // Try to save to database
                try {
                    CapitalGiro capitalGiro = new CapitalGiro();
                    capitalGiro.setValor(BigDecimal.valueOf(capitalDto.getCapitalGiro()));
                    capitalGiro.setDataAtualizacao(LocalDateTime.now());
                    
                    capitalGiroRepository.save(capitalGiro);
                } catch (Exception e) {
                    log.error("Error saving capital giro to database", e);
                    useFileBasedFallback = true;
                }
            }
            
            if (useFileBasedFallback) {
                // Fallback to file-based approach
                Map<String, Object> opcoes = loadAllOptions();
                opcoes.put("capitalGiro", capitalDto.getCapitalGiro());
                
                File file = new File(OPCOES_FILE);
                mapper.writeValue(file, opcoes);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("capitalGiro", capitalDto.getCapitalGiro());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error saving capital giro", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/capital-giro")
    public ResponseEntity<Map<String, Object>> getCapitalGiro() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!useFileBasedFallback) {
                // Try to get from database first
                try {
                    CapitalGiro capitalGiro = capitalGiroRepository.findTopByOrderByDataAtualizacaoDesc();
                    
                    if (capitalGiro != null) {
                        response.put("capitalGiro", capitalGiro.getValor());
                        return ResponseEntity.ok(response);
                    }
                } catch (Exception e) {
                    log.error("Error retrieving capital giro from database", e);
                    useFileBasedFallback = true;
                }
            }
            
            // Fallback to file-based approach
            Map<String, Object> opcoes = loadAllOptions();
            
            // If no capital giro value found, return zero
            if (!opcoes.containsKey("capitalGiro")) {
                opcoes.put("capitalGiro", 0.0);
            }
            
            response.put("capitalGiro", opcoes.get("capitalGiro"));
            
        } catch (Exception e) {
            log.error("Error retrieving capital giro", e);
            response.put("capitalGiro", 0.0);
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("time", LocalDateTime.now().toString());
        
        try {
            // Check database connection by listing banks
            List<Banco> bancos = bancoRepository.findAll();
            status.put("database", "Connected");
            status.put("banksCount", bancos.size());
            status.put("usingFileFallback", useFileBasedFallback);
            
            // Check if opcoes.json exists
            File opcoes = new File(OPCOES_FILE);
            status.put("opcoesFileExists", opcoes.exists());
            
        } catch (Exception e) {
            status.put("database", "Error: " + e.getMessage());
            status.put("usingFileFallback", true);
        }
        
        return ResponseEntity.ok(status);
    }

    private Map<String, List<OpcaoDTO>> loadOptions() {
        Map<String, List<OpcaoDTO>> opcoes = new HashMap<>();
        opcoes.put("bancos", new ArrayList<>());
        opcoes.put("metodos", new ArrayList<>());

        try {
            Path path = Paths.get(OPCOES_FILE);
            if (Files.exists(path)) {
                // Using TypeReference for simpler deserialization
                TypeReference<Map<String, List<OpcaoDTO>>> typeRef = 
                    new TypeReference<Map<String, List<OpcaoDTO>>>() {};
                opcoes = mapper.readValue(path.toFile(), typeRef);
            }
        } catch (IOException e) {
            log.error("Error loading options from file", e);
        }

        return opcoes;
    }
    
    private Map<String, Object> loadAllOptions() {
        Map<String, Object> opcoes = new HashMap<>();
        
        try {
            Path path = Paths.get(OPCOES_FILE);
            if (Files.exists(path)) {
                TypeReference<Map<String, Object>> typeRef = 
                    new TypeReference<Map<String, Object>>() {};
                opcoes = mapper.readValue(path.toFile(), typeRef);
            }
        } catch (IOException e) {
            log.error("Error loading all options from file", e);
        }
        
        return opcoes;
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