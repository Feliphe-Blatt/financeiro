package com.controlefinanceiro.api.strategy;

import com.controlefinanceiro.api.enums.TipoCategoriaEnum;
import com.controlefinanceiro.api.model.Movimentacao;

import java.util.List;
import java.util.stream.Collectors;

public class RelatorioPorTipoStrategy implements RelatorioStrategy {
    private final TipoCategoriaEnum tipo;

    public RelatorioPorTipoStrategy(String tipo) {
        this.tipo = TipoCategoriaEnum.valueOf(tipo.toUpperCase());
    }

    @Override
    public List<Movimentacao> filtrar(List<Movimentacao> movimentacoes) {
        return movimentacoes.stream()
                .filter(mov -> mov.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}