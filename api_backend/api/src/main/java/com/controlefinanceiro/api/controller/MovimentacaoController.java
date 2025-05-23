package com.controlefinanceiro.api.controller;

import com.controlefinanceiro.api.dto.MovimentacaoDTO;
import com.controlefinanceiro.api.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimentacoes")
@CrossOrigin(origins = "*")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService movimentacaoService;

    @PostMapping
    public ResponseEntity<String> criarMovimentacao(@Valid @RequestBody MovimentacaoDTO.MovimentacaoRequestDTO movimentacaoDTO) {
        try {
            movimentacaoService.criarMovimentacao(movimentacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Movimentação criada com sucesso!");
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar movimentação: " + e.getMessage());
        }
    }

    @GetMapping("/usuario-movimentacoes/{id}")
    public ResponseEntity<?> getMovimentacoesUsuarioLogado(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movimentacaoService.getMovimentacoesUsuarioLogado());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter movimentações: " + e.getMessage());
        }
    }

    @GetMapping("/homepage/{id}")
    public ResponseEntity<?> getMovimentacoesUsuarioTelaInicial(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movimentacaoService.getMovimentacoesUsuarioTelaInicial());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter movimentações: " + e.getMessage());
        }
    }

}
