package com.controlefinanceiro.api.strategy;

import com.controlefinanceiro.api.model.Movimentacao;
import java.util.List;

public class RelatorioCombinadoStrategy implements RelatorioStrategy {
    private final List<RelatorioStrategy> strategies;

    public RelatorioCombinadoStrategy(List<RelatorioStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public List<Movimentacao> filtrar(List<Movimentacao> movimentacoes) {
        List<Movimentacao> resultado = movimentacoes;
        for (RelatorioStrategy strategy : strategies) {
            resultado = strategy.filtrar(resultado);
        }
        return resultado;
    }
}