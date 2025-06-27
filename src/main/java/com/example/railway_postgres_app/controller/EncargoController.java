package com.example.railway_postgres_app.controller;

import com.example.railway_postgres_app.model.Encargo;
import com.example.railway_postgres_app.repository.EncargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/encargos")
@CrossOrigin(origins = "*")
public class EncargoController {

    @Autowired
    private EncargoRepository encargoRepository;

    @GetMapping
    public List<Encargo> getAllEncargos() {
        return encargoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Encargo> getEncargoById(@PathVariable Long id) {
        Optional<Encargo> encargo = encargoRepository.findById(id);
        return encargo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/loja/{loja}")
    public List<Encargo> getEncargosByLoja(@PathVariable String loja) {
        return encargoRepository.findByLoja(loja);
    }

    @GetMapping("/mes/{mesAno}")
    public List<Encargo> getEncargosByMes(@PathVariable String mesAno) {
        return encargoRepository.findByMesAno(mesAno);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Encargo> getEncargosByTipo(@PathVariable String tipo) {
        return encargoRepository.findByTipo(tipo);
    }

    @PostMapping
    public Encargo createEncargo(@RequestBody Encargo encargo) {
        return encargoRepository.save(encargo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Encargo> updateEncargo(@PathVariable Long id, @RequestBody Encargo encargoDetails) {
        Optional<Encargo> optionalEncargo = encargoRepository.findById(id);
        
        if (optionalEncargo.isPresent()) {
            Encargo encargo = optionalEncargo.get();
            encargo.setTipo(encargoDetails.getTipo());
            encargo.setValor(encargoDetails.getValor());
            encargo.setLoja(encargoDetails.getLoja());
            encargo.setMesAno(encargoDetails.getMesAno());
            encargo.setObservacoes(encargoDetails.getObservacoes());
            
            return ResponseEntity.ok(encargoRepository.save(encargo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/pago")
    public ResponseEntity<Encargo> marcarEncargoPago(@PathVariable Long id, @RequestParam Boolean pago) {
        Optional<Encargo> optionalEncargo = encargoRepository.findById(id);
        
        if (optionalEncargo.isPresent()) {
            Encargo encargo = optionalEncargo.get();
            encargo.setPago(pago);
            return ResponseEntity.ok(encargoRepository.save(encargo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEncargo(@PathVariable Long id) {
        if (encargoRepository.existsById(id)) {
            encargoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 