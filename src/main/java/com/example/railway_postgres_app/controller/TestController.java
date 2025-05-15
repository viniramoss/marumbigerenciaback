package com.example.railway_postgres_app.controller;

import com.example.railway_postgres_app.model.TestEntity;
import com.example.railway_postgres_app.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @GetMapping("/test")
    public ResponseEntity<String> testConnection() {
        try {
            // Try to save a simple entity to test the connection and table creation
            TestEntity entity = new TestEntity("Connection test successful!");
            // testRepository.save(entity);
            return ResponseEntity.ok("Database connection successful! Table 'test_entity' exists and has entries.");
        } catch (Exception e) {
            // Log the exception for debugging
            // logger.error("Database connection failed", e);
            return ResponseEntity.status(500).body("Database connection failed: " + e.getMessage());
        }
    }
}