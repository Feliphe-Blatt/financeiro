package com.controlefinanceiro.api.dto;

import com.controlefinanceiro.api.enums.NomeCategoriaEnum;
import com.controlefinanceiro.api.enums.TipoCategoriaEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull
    private boolean isReceita;

    @NotNull(message = "O valor é obrigatório.")
    @Positive(message = "O valor deve ser positivo.")
    private BigDecimal valor;

    @NotNull
    private Long categoria;

    @NotNull
    private Long usuarioId;

    private LocalDate data;

    @NotNull(message = "O campo 'tipo' é obrigatório.")
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

        @JsonProperty("isReceita")
        @NotNull
        private boolean isReceita;

        @NotNull(message = "O valor é obrigatório.")
        @Positive(message = "O valor deve ser positivo.")
        private BigDecimal valor;

        private Long categoria;
        private Long usuarioId;
        private LocalDate data = LocalDate.now();

        @NotNull(message = "O campo 'tipo' é obrigatório.")
        private TipoCategoriaEnum tipo = TipoCategoriaEnum.VARIAVEL; // Valor padrão
        private String descricao;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimentacaoResponseDTO {
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
