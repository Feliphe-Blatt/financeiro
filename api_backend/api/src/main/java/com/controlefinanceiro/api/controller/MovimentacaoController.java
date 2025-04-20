package com.controlefinanceiro.api.controller;

import com.controlefinanceiro.api.dto.MovimentacaoDTO;
import com.controlefinanceiro.api.service.MovimentacaoService;
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
    public ResponseEntity<String> criarMovimentacao(@RequestBody MovimentacaoDTO movimentacaoDTO) {
        try {
            movimentacaoService.criarMovimentacao(movimentacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Movimentação criada com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar movimentação: " + e.getMessage());
        }
    }
}
