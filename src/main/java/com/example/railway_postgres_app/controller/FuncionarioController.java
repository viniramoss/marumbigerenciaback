package com.example.railway_postgres_app.controller;

import com.example.railway_postgres_app.model.Funcionario;
import com.example.railway_postgres_app.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping
    public List<Funcionario> listarTodos(@RequestParam(required = false) String loja,
                                        @RequestParam(required = false) String nome) {
        if (loja != null && !loja.isEmpty() && nome != null && !nome.isEmpty()) {
            return funcionarioRepository.findByLojaAndNomeContainingIgnoreCase(loja, nome);
        } else if (loja != null && !loja.isEmpty()) {
            return funcionarioRepository.findByLoja(loja);
        } else if (nome != null && !nome.isEmpty()) {
            return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
        } else {
            return funcionarioRepository.findAll();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return funcionario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Funcionario criar(@RequestBody Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable Long id, @RequestBody Funcionario funcionarioAtualizado) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> {
                    funcionario.setNome(funcionarioAtualizado.getNome());
                    funcionario.setSalario(funcionarioAtualizado.getSalario());
                    funcionario.setLoja(funcionarioAtualizado.getLoja());
                    return ResponseEntity.ok(funcionarioRepository.save(funcionario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> {
                    funcionarioRepository.delete(funcionario);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 