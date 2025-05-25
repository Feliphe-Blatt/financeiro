package com.controlefinanceiro.api.strategy;

import com.controlefinanceiro.api.model.Movimentacao;

import java.util.List;

public interface RelatorioStrategy {
    List<Movimentacao> filtrar(List<Movimentacao> movimentacoes);
}
