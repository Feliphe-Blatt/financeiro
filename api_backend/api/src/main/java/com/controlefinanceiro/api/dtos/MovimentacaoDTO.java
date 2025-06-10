package com.controlefinanceiro.api.dto;

import com.controlefinanceiro.api.enums.NomeCategoriaEnum;
import com.controlefinanceiro.api.enums.TipoCategoriaEnum;
import com.controlefinanceiro.api.json.TipoCategoriaEnumDeserializer;
import com.controlefinanceiro.api.model.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDTO {
    @JsonProperty("isReceita")
    @NotNull
    private boolean isReceita;

    @NotNull(message = "O valor é obrigatório.")
    @Positive(message = "O valor deve ser positivo.")
    private BigDecimal valor;

    @NotNull
    private Categoria categoria;

    @NotNull
    private Long usuarioId;

    private LocalDate data;

    @NotNull(message = "O campo 'tipo' é obrigatório.")
    @JsonDeserialize(using = TipoCategoriaEnumDeserializer.class)
    private TipoCategoriaEnum tipo = TipoCategoriaEnum.VARIAVEL;

    private String descricao;

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

        private String categoria;
        private LocalDate data = LocalDate.now();
        private LocalTime horarioMovimentacao = LocalTime.now();

        @NotNull(message = "O campo 'tipo' é obrigatório.")
        @JsonDeserialize(using = TipoCategoriaEnumDeserializer.class)
        private TipoCategoriaEnum tipo = TipoCategoriaEnum.VARIAVEL;

        private String descricao;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimentacaoResponseDTO {
        private BigDecimal valor;
        private String categoria;
        private LocalDate data;
        private TipoCategoriaEnum tipoDaMovimentacao;
        private String descricao;
        private LocalTime horaMovimentacao;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovimentacaoResponseDTOTelaInicial {
        private BigDecimal valor;
        private String categoria;
        private LocalDate data;
        private LocalTime horaMovimentacao;
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
