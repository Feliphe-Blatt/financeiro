package com.controlefinanceiro.api.dto;

import com.controlefinanceiro.api.deserializer.ValorDTODeserializer;
import com.controlefinanceiro.api.model.Categoria.NomeCategoriaReceita;
import com.controlefinanceiro.api.model.Categoria.NomeCategoriaDespesa;
import com.controlefinanceiro.api.model.Categoria.TipoCategoria;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDTO {
    
    private Long id;
    private int quantidade;
    private CategoriaDTO categoria;
    private ValorDTO valor;
    private Long usuarioId;
    private LocalDate data;
    private String descricao;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoriaDTO {
        private Long id;
        private TipoCategoria tipo;
        private NomeCategoriaDespesa nomeDespesa;
        private NomeCategoriaReceita nomeReceita;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonDeserialize(using = ValorDTODeserializer.class)
    public static class ValorDTO {
        private BigDecimal valor;
        private String tipo = "DESPESA"; // Padrão é despesa
        
        public ValorDTO(BigDecimal valor) {
            this.valor = valor;
        }
        
        public boolean isReceita() {
            return "RECEITA".equalsIgnoreCase(tipo);
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimentacaoRequestDTO {
        private int quantidade = 1; // Default é 1
        private CategoriaDTO categoria;
        private ValorDTO valor;
        private Long usuarioId;
        private LocalDate data = LocalDate.now(); // Default é data atual
        private String descricao;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimentacaoResponseDTO {
        private Long id;
        private int quantidade;
        private CategoriaDTO categoria;
        private ValorDTO valor;
        private Long usuarioId;
        private LocalDate data;
        private String descricao;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimentacaoResumoDTO {
        private BigDecimal totalReceitas = BigDecimal.ZERO;
        private BigDecimal totalDespesas = BigDecimal.ZERO;
        private BigDecimal saldo = BigDecimal.ZERO;
    }
}
