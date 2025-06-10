package com.controlefinanceiro.api.strategy;

import com.controlefinanceiro.api.model.Movimentacao;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class RelatorioPorValorStrategy implements RelatorioStrategy {
    private final BigDecimal valorMin;
    private final BigDecimal valorMax;

    public RelatorioPorValorStrategy(BigDecimal valorMin, BigDecimal valorMax) {
        this.valorMin = valorMin;
        this.valorMax = valorMax;
    }

    @Override
    public List<Movimentacao> filtrar(List<Movimentacao> movimentacoes) {
        return movimentacoes.stream()
                .filter(mov -> {
                    BigDecimal valor = mov.isReceita()
                            ? mov.getValor()
                            : mov.getValor().negate();
                    return (valorMin == null || valor.compareTo(valorMin) >= 0) &&
                            (valorMax == null || valor.compareTo(valorMax) <= 0);
                })
                .collect(Collectors.toList());
    }
}
