package br.com.marumbi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class StatusController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatusController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    public Map<String, String> status() {
        return Collections.singletonMap("status", "Servidor Marumbi está rodando!");
    }

    @GetMapping("/db-status")
    public Map<String, String> dbStatus() {
        try {
            String result = jdbcTemplate.queryForObject("SELECT 'Conexão com o banco de dados estabelecida com sucesso!' as status", String.class);
            return Collections.singletonMap("status", result);
        } catch (Exception e) {
            return Collections.singletonMap("status", "Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}