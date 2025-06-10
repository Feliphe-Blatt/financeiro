package com.controlefinanceiro.api.strategy;

import com.controlefinanceiro.api.model.Movimentacao;
import java.util.List;
import java.util.stream.Collectors;

public class RelatorioPorReceitaDespesaStrategy implements RelatorioStrategy {
    private final boolean isReceita;

    public RelatorioPorReceitaDespesaStrategy(boolean isReceita) {
        this.isReceita = isReceita;
    }

    @Override
    public List<Movimentacao> filtrar(List<Movimentacao> movimentacoes) {
        return movimentacoes.stream()
                .filter(mov -> mov.isReceita() == isReceita)
                .collect(Collectors.toList());
    }
}