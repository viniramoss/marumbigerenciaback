package com.example.railway_postgres_app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/test")
    public ResponseEntity<String> testConnection() {
        try {

            return ResponseEntity.ok("Database connection successful! Table 'test_entity' exists and has entries.");
        } catch (Exception e) {
            // Log the exception for debugging
            // logger.error("Database connection failed", e);
            return ResponseEntity.status(500).body("Database connection failed: " + e.getMessage());
        }
    }
}

