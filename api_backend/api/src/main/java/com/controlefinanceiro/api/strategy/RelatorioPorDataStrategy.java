package com.controlefinanceiro.api.strategy;

import com.controlefinanceiro.api.model.Movimentacao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RelatorioPorDataStrategy implements RelatorioStrategy {
    private final LocalDate dataInicio;
    private final LocalDate dataFim;

    public RelatorioPorDataStrategy(LocalDate dataInicio, LocalDate dataFim) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    @Override
    public List<Movimentacao> filtrar(List<Movimentacao> movimentacoes) {
        return movimentacoes.stream()
                .filter(mov -> !mov.getData().isBefore(dataInicio) && !mov.getData().isAfter(dataFim))
                .collect(Collectors.toList());
    }
}
