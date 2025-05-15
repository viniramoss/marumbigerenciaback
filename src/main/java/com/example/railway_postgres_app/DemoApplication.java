package com.example.railway_postgres_app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class DemoApplication {

	private static final String OPCOES_FILE = "opcoes.json";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializeOpcoesFile() {
		return args -> {
			File opcoesFile = new File(OPCOES_FILE);
			if (!opcoesFile.exists()) {
				try {
					// Create default options structure
					Map<String, Object> defaultOptions = new HashMap<>();
					
					// Default banks
					List<Map<String, String>> banks = new ArrayList<>();
					Map<String, String> bank1 = new HashMap<>();
					bank1.put("id", "itau");
					bank1.put("label", "Itaú");
					banks.add(bank1);
					
					Map<String, String> bank2 = new HashMap<>();
					bank2.put("id", "bradesco");
					bank2.put("label", "Bradesco");
					banks.add(bank2);
					
					// Default payment methods
					List<Map<String, String>> methods = new ArrayList<>();
					Map<String, String> method1 = new HashMap<>();
					method1.put("id", "pix");
					method1.put("label", "Pix");
					methods.add(method1);
					
					Map<String, String> method2 = new HashMap<>();
					method2.put("id", "boleto");
					method2.put("label", "Boleto");
					methods.add(method2);
					
					// Add to options
					defaultOptions.put("bancos", banks);
					defaultOptions.put("metodos", methods);
					defaultOptions.put("capitalGiro", 0.0);
					
					// Write to file
					ObjectMapper mapper = new ObjectMapper();
					mapper.writeValue(opcoesFile, defaultOptions);
					
					System.out.println("Created default opcoes.json file");
				} catch (IOException e) {
					System.err.println("Failed to create default opcoes.json file: " + e.getMessage());
				}
			}
		};
	}
}
