package com.controlefinanceiro.api.enums;

public enum NomeCategoriaEnum {
    SALARIO("Receita"),
    BONUS("Receita"),
    FREELANCER("Receita"),
    VENDA("Receita"),
    RENDIMENTO("Receita"),
    INVESTIMENTO("Receita"),
    OUTROS_RECEITA("Receita"),
    LAZER("Despesa"),
    EDUCACAO("Despesa"),
    MORADIA("Despesa"),
    TRANSPORTE("Despesa"),
    ALIMENTACAO("Despesa"),
    SAUDE("Despesa"),
    PRESENTES("Despesa"),
    PET("Despesa"),
    INVESTIMENTOS("Despesa"),
    ASSINATURAS("Despesa"),
    OUTROS_DESPESA("Despesa");

    private final String tipo;

    NomeCategoriaEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
