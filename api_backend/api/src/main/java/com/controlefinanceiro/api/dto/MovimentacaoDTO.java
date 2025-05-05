package com.controlefinanceiro.api.dto;

import com.controlefinanceiro.api.enums.NomeCategoriaEnum;
import com.controlefinanceiro.api.enums.TipoCategoriaEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isReceita")
    private boolean isReceita;
    private BigDecimal valor;
    private Long categoria;
    private Long usuarioId;
    private LocalDate data;
    private TipoCategoriaEnum tipo;
    private String descricao;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoriaDTO {
        private Long id;
        private NomeCategoriaEnum nomeCategoria;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimentacaoRequestDTO {
        private Long categoria;
        private Long usuarioId;
        private LocalDate data = LocalDate.now(); // Default é data atual
        private String descricao;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimentacaoResponseDTO {
        private Long id;
        private boolean isReceita;
        private BigDecimal valor;
        private Long categoria;
        private Long usuarioId;
        private LocalDate data;
        private TipoCategoriaEnum tipo;
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
