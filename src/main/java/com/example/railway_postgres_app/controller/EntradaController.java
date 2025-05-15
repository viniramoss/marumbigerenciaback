package com.example.railway_postgres_app.controller;

import com.example.railway_postgres_app.model.Entrada;
import com.example.railway_postgres_app.service.EntradaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/entradas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EntradaController {

  private final EntradaService svc;

  @PostMapping
  public ResponseEntity<Entrada> criar(@RequestBody Entrada e){
    return ResponseEntity.ok(svc.salvar(e));
  }

  @GetMapping
  public List<Entrada> listar(
          @RequestParam(required = false) String unidade,
          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
  ) {
      List<Entrada> entradas;
      
      // Se tiver filtro de período, usa esse filtro
      if (dataInicio != null && dataFim != null) {
          entradas = svc.listarPorPeriodo(dataInicio, dataFim);
      } else {
          entradas = svc.listar();
      }
      
      // Filtro por unidade (aplicado depois do filtro de data)
      if (unidade != null && !unidade.isEmpty()) {
          return entradas.stream()
              .filter(e -> norm(unidade).equals(norm(e.getUnidade())))
              .toList();
      }
      
      return entradas;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Entrada> buscarPorId(@PathVariable Long id) {
      try {
          Entrada entrada = svc.buscarPorId(id);
          return ResponseEntity.ok(entrada);
      } catch (Exception e) {
          return ResponseEntity.notFound().build();
      }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Entrada> atualizar(@PathVariable Long id, @RequestBody Entrada entrada) {
      try {
          
          // Atualiza os campos
          entrada.setId(id);
          
          return ResponseEntity.ok(svc.salvar(entrada));
      } catch (Exception e) {
          return ResponseEntity.notFound().build();
      }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id){
    svc.excluir(id);
    return ResponseEntity.ok().build();
  }
  
  /* remove acentos e converte para minúsculas */
  private static String norm(String s) {
      return s == null ? null
          : Normalizer.normalize(s, Normalizer.Form.NFD)
                      .replaceAll("\\p{M}","")   // tira acentos
                      .toLowerCase();
  }
  
  // Endpoint para obter estatísticas do mês atual
  @GetMapping("/estatisticas/mes-atual")
  public ResponseEntity<EstatsticasDTO> estatisticasMesAtual() {
      // Obtém o primeiro e último dia do mês atual
      LocalDate hoje = LocalDate.now();
      LocalDate primeiroDia = hoje.withDayOfMonth(1);
      LocalDate ultimoDia = hoje.withDayOfMonth(hoje.lengthOfMonth());
      
      // Busca as entradas do período
      List<Entrada> entradas = svc.listarPorPeriodo(primeiroDia, ultimoDia);
      
      // Calcula os totais
      EstatsticasDTO estatisticas = new EstatsticasDTO();
      
      // Total geral
      estatisticas.setTotalGeral(
          entradas.stream()
              .map(Entrada::getTotal)
              .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
      );
      
      // Total por método de pagamento
      estatisticas.setTotalDinheiro(
          entradas.stream()
              .map(Entrada::getDinheiro)
              .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
      );
      
      estatisticas.setTotalDebito(
          entradas.stream()
              .map(Entrada::getDebito)
              .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
      );
      
      estatisticas.setTotalCredito(
          entradas.stream()
              .map(Entrada::getCredito)
              .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
      );
      
      estatisticas.setTotalPix(
          entradas.stream()
              .map(Entrada::getPix)
              .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
      );
      
      estatisticas.setTotalVoucher(
          entradas.stream()
              .map(Entrada::getVoucher)
              .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
      );
      
      return ResponseEntity.ok(estatisticas);
  }
  
  // DTO para estatísticas
  public static class EstatsticasDTO {
      private java.math.BigDecimal totalGeral;
      private java.math.BigDecimal totalDinheiro;
      private java.math.BigDecimal totalDebito;
      private java.math.BigDecimal totalCredito;
      private java.math.BigDecimal totalPix;
      private java.math.BigDecimal totalVoucher;
      
      // Getters e Setters
      public java.math.BigDecimal getTotalGeral() {
          return totalGeral;
      }
      
      public void setTotalGeral(java.math.BigDecimal totalGeral) {
          this.totalGeral = totalGeral;
      }
      
      public java.math.BigDecimal getTotalDinheiro() {
          return totalDinheiro;
      }
      
      public void setTotalDinheiro(java.math.BigDecimal totalDinheiro) {
          this.totalDinheiro = totalDinheiro;
      }
      
      public java.math.BigDecimal getTotalDebito() {
          return totalDebito;
      }
      
      public void setTotalDebito(java.math.BigDecimal totalDebito) {
          this.totalDebito = totalDebito;
      }
      
      public java.math.BigDecimal getTotalCredito() {
          return totalCredito;
      }
      
      public void setTotalCredito(java.math.BigDecimal totalCredito) {
          this.totalCredito = totalCredito;
      }
      
      public java.math.BigDecimal getTotalPix() {
          return totalPix;
      }
      
      public void setTotalPix(java.math.BigDecimal totalPix) {
          this.totalPix = totalPix;
      }
      
      public java.math.BigDecimal getTotalVoucher() {
          return totalVoucher;
      }
      
      public void setTotalVoucher(java.math.BigDecimal totalVoucher) {
          this.totalVoucher = totalVoucher;
      }
  }
}