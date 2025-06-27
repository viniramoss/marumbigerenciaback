package com.example.railway_postgres_app.controller;

import com.example.railway_postgres_app.model.Outros;
import com.example.railway_postgres_app.repository.OutrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/outros")
@CrossOrigin(origins = "*")
public class OutrosController {

    @Autowired
    private OutrosRepository outrosRepository;

    @GetMapping
    public List<Outros> getAllOutros() {
        return outrosRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Outros> getOutroById(@PathVariable Long id) {
        Optional<Outros> outro = outrosRepository.findById(id);
        return outro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/loja/{loja}")
    public List<Outros> getOutrosByLoja(@PathVariable String loja) {
        return outrosRepository.findByLoja(loja);
    }

    @GetMapping("/mes/{mesAno}")
    public List<Outros> getOutrosByMes(@PathVariable String mesAno) {
        return outrosRepository.findByMesAno(mesAno);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Outros> getOutrosByTipo(@PathVariable String tipo) {
        return outrosRepository.findByTipo(tipo);
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public List<Outros> getOutrosByFuncionario(@PathVariable Long funcionarioId) {
        return outrosRepository.findByFuncionarioId(funcionarioId);
    }

    @PostMapping
    public Outros createOutro(@RequestBody Outros outro) {
        return outrosRepository.save(outro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Outros> updateOutro(@PathVariable Long id, @RequestBody Outros outroDetails) {
        Optional<Outros> optionalOutro = outrosRepository.findById(id);
        
        if (optionalOutro.isPresent()) {
            Outros outro = optionalOutro.get();
            outro.setTipo(outroDetails.getTipo());
            outro.setDescricao(outroDetails.getDescricao());
            outro.setValor(outroDetails.getValor());
            outro.setFuncionarioId(outroDetails.getFuncionarioId());
            outro.setLoja(outroDetails.getLoja());
            outro.setMesAno(outroDetails.getMesAno());
            outro.setObservacoes(outroDetails.getObservacoes());
            
            return ResponseEntity.ok(outrosRepository.save(outro));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/pago")
    public ResponseEntity<Outros> marcarOutroPago(@PathVariable Long id, @RequestParam Boolean pago) {
        Optional<Outros> optionalOutro = outrosRepository.findById(id);
        
        if (optionalOutro.isPresent()) {
            Outros outro = optionalOutro.get();
            outro.setPago(pago);
            return ResponseEntity.ok(outrosRepository.save(outro));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOutro(@PathVariable Long id) {
        if (outrosRepository.existsById(id)) {
            outrosRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 