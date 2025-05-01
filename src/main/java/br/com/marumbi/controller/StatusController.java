package br.com.marumbi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StatusController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatusController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> status() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "Servidor Marumbi está rodando!");
        response.put("message", "API está funcionando corretamente");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Collections.singletonMap("status", "UP"));
    }

    @GetMapping("/db-status")
    public ResponseEntity<Map<String, String>> dbStatus() {
        try {
            String result = jdbcTemplate.queryForObject("SELECT 'OK' as status", String.class);
            Map<String, String> response = new HashMap<>();
            response.put("status", "Conexão com o banco de dados estabelecida com sucesso!");
            response.put("result", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "Erro ao conectar com o banco de dados");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}