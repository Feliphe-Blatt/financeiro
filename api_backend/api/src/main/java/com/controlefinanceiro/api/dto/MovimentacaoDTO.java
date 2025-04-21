package com.controlefinanceiro.api.dto;

import com.controlefinanceiro.api.deserializer.ValorDTODeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovimentacaoDTO {
    
    private ValorDTO valor;
    private CategoriaDTO categoria;
    private Long usuarioId;
    private int quantidade = 1;
    
    @Data
    @JsonDeserialize(using = ValorDTODeserializer.class)
    public static class ValorDTO {
        
        private BigDecimal valor;
        private String tipo; // RECEITA ou DESPESA
        
        // Construtor padrão para o Lombok
        public ValorDTO() {}
        
        // Construtor para valor numérico direto (considerado DESPESA por padrão)
        public ValorDTO(BigDecimal valor) {
            this.valor = valor;
            this.tipo = "DESPESA";
        }
        
        // Construtor com todos os campos
        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public ValorDTO(@JsonProperty("valor") BigDecimal valor, @JsonProperty(value = "tipo", required = false) String tipo) {
            
            this.valor = valor;
            this.tipo = tipo != null ? tipo : "DESPESA";
        }
    }
    
    @Data
    public static class CategoriaDTO {
        private String tipo; // FIXA, VARIAVEL, EXTRA
        private String nomeDespesa; // LAZER, EDUCACAO, MORADIA, TRANSPORTE, ALIMENTACAO, SAUDE, PRESENTES, PET, INVESTIMENTOS, ASSINATURAS, OUTROS
        private String nomeReceita; // SALARIO, BONUS, FREELANCER, VENDA, RENDIMENTO, OUTROS
    }
}
