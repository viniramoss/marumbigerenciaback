package com.example.railway_postgres_app_maven.controller;

import com.example.railway_postgres_app_maven.model.Despesa;
import com.example.railway_postgres_app_maven.service.DespesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/despesas") // Base path for despesa-related endpoints
@CrossOrigin(origins = "*") // Allow requests from any origin (adjust in production if needed)
@RequiredArgsConstructor // Lombok creates constructor with final fields (DespesaService)
public class DespesaController {

  private final DespesaService svc;

  @PostMapping
  public ResponseEntity<Despesa> criar(@RequestBody Despesa d){
    // Consider adding validation (@Valid) here if needed
    return ResponseEntity.ok(svc.salvar(d));
  }

  @GetMapping
  public List<Despesa> listar(
          @RequestParam(required = false) String unidade,
          @RequestParam(required = false) String tipo,
          @RequestParam(required = false) String status  // p (pago) | np (não pago) | all (todos)
  ) {
      return svc.listar().stream()
          .filter(d -> unidade == null || norm(unidade).equals(norm(d.getUnidade())))
          .filter(d -> tipo    == null || norm(tipo)   .equals(norm(d.getTipo())))
          .filter(d -> {
              if (status == null || "all".equalsIgnoreCase(status)) return true;
              return "p".equalsIgnoreCase(status)  ? d.getPago()
                  : "np".equalsIgnoreCase(status) ? !d.getPago()
                  : true; // Default to true if status is unrecognized
          })
          .toList();
  }

  /* remove accents and convert to lowercase */
  private static String norm(String s) {
      return s == null ? null
          : Normalizer.normalize(s, Normalizer.Form.NFD)
                      .replaceAll("\\p{M}","")   // remove diacritics
                      .toLowerCase();
  }
  
  @PutMapping("/{id}/pago")
  public ResponseEntity<Despesa> marcarPago(
      @PathVariable Long id, 
      @RequestParam boolean valor, // true to mark as paid, false to mark as unpaid
      @RequestBody(required = false) Map<String, String> dadosPagamento // Optional payment details (banco, metodo)
  ){
    // Find the Despesa or return 404 Not Found
    Despesa d = svc.listar().stream() // Inefficient for large datasets, consider repo.findById(id)
                   .filter(x -> x.getId().equals(id))
                   .findFirst()
                   .orElse(null); // Find first or return null
                   
    if (d == null) {
        return ResponseEntity.notFound().build(); // Return 404 if not found
    }

    d.setPago(valor);
    
    // If marking as paid and payment details are provided
    if (valor && dadosPagamento != null) {
      d.setBanco(dadosPagamento.getOrDefault("banco", d.getBanco())); // Update if key exists
      d.setMetodo(dadosPagamento.getOrDefault("metodo", d.getMetodo())); // Update if key exists
    }
    
    // If marking as unpaid, clear payment details
    if (!valor) {
      d.setBanco(null);
      d.setMetodo(null);
    }
    
    return ResponseEntity.ok(svc.salvar(d)); // Save changes and return updated entity
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id){
    // Consider checking if the entity exists before deleting to return 404 if needed
    svc.excluir(id);
    return ResponseEntity.ok().build(); // Return 200 OK on successful deletion
  }
}

