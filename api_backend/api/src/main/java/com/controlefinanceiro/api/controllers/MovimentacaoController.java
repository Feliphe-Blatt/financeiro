package com.controlefinanceiro.api.controller;

import com.controlefinanceiro.api.dto.MovimentacaoDTO;
import com.controlefinanceiro.api.model.Movimentacao;
import com.controlefinanceiro.api.model.Usuario;
import com.controlefinanceiro.api.service.MovimentacaoService;
import com.controlefinanceiro.api.strategy.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/usuario-movimentacoes")
    public ResponseEntity<?> getMovimentacoesUsuarioLogado() {
        try {
            return ResponseEntity.ok(movimentacaoService.getMovimentacoesUsuarioLogado());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter movimentações: " + e.getMessage());
        }
    }

    @GetMapping("/usuario-movimentacoes-paginado")
    public ResponseEntity<?> getMovimentacoesUsuarioLogadoPaginado(
            @RequestParam(required = false) Boolean isReceita,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            var resultado = movimentacaoService.getPaginadoComFiltrosStrategy(
                    isReceita, tipo, categoria,  dataInicio, dataFim, PageRequest.of(page, size, Sort.by("data").descending())
            );
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter movimentações: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/movimentacoes-tela-inicial")
    public ResponseEntity<?> getMovimentacoesUsuarioTelaInicial() {
        try {
            return ResponseEntity.ok(movimentacaoService.getMovimentacoesUsuarioTelaInicial());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter movimentações: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/saldo")
    public ResponseEntity<?> calcularSaldoUsuarioLogado() {
        try {
            return ResponseEntity.ok(movimentacaoService.calcularSaldoUsuarioLogado());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter saldo: " + e.getMessage());
        }
    }

    @GetMapping("/relatorio/categoria")
    public ResponseEntity<?> relatorioPorCategoria(@RequestParam String categoria) {
        RelatorioPorCategoriaStrategy strategy = new RelatorioPorCategoriaStrategy(categoria);
        List<MovimentacaoDTO.MovimentacaoResponseDTO> dtos = movimentacaoService.gerarRelatorioDTO(strategy);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/relatorio/data-periodo")
    public ResponseEntity<?> relatorioPorData(@RequestParam LocalDate dataInicio,
                                              @RequestParam LocalDate dataFim)
    {
        RelatorioPorDataStrategy strategy = new RelatorioPorDataStrategy(dataInicio, dataFim);
        List<MovimentacaoDTO.MovimentacaoResponseDTO> dtos = movimentacaoService.gerarRelatorioDTO(strategy);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/relatorio/valor-margem")
    public ResponseEntity<?> relatorioPorData(@RequestParam BigDecimal valorMinimo,
                                              @RequestParam BigDecimal valorMaximo)
    {
        RelatorioPorValorStrategy strategy = new RelatorioPorValorStrategy(valorMinimo, valorMaximo);
        List<MovimentacaoDTO.MovimentacaoResponseDTO> dtos = movimentacaoService.gerarRelatorioDTO(strategy);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/relatorio/tipo")
    public ResponseEntity<?> relatorioPorTipo(@RequestParam String tipo) {
        RelatorioPorTipoStrategy strategy = new RelatorioPorTipoStrategy(tipo);
        List<MovimentacaoDTO.MovimentacaoResponseDTO> dtos = movimentacaoService.gerarRelatorioDTO(strategy);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/relatorio/receita-despesa")
    public ResponseEntity<?> relatorioPorReceitaOuDespesa(@RequestParam boolean isReceita) {
        RelatorioPorReceitaDespesaStrategy strategy = new RelatorioPorReceitaDespesaStrategy(isReceita);
        List<MovimentacaoDTO.MovimentacaoResponseDTO> dtos = movimentacaoService.gerarRelatorioDTO(strategy);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/relatorios")
    public ResponseEntity<?> relatorioPersonalizado(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Boolean isReceita,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim,
            @RequestParam(required = false) BigDecimal valorMinimo,
            @RequestParam(required = false) BigDecimal valorMaximo
    ) {
        try {
            List<MovimentacaoDTO.MovimentacaoResponseDTO> dtos = movimentacaoService.relatorioPersonalizado(
                    categoria, tipo, isReceita, dataInicio, dataFim, valorMinimo, valorMaximo
            );
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    @GetMapping("/relatorios/csv")
    public void exportarRelatorioCsv(
            HttpServletResponse response,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Boolean isReceita,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            @RequestParam(required = false) BigDecimal valorMinimo,
            @RequestParam(required = false) BigDecimal valorMaximo
    ) throws IOException {
        LocalDate inicio = dataInicio != null ? LocalDate.parse(dataInicio) : null;
        LocalDate fim = dataFim != null ? LocalDate.parse(dataFim) : null;
        movimentacaoService.exportarRelatorioCsv(
                response, categoria, tipo, isReceita, inicio, fim, valorMinimo, valorMaximo
        );
    }
}
