package com.controlefinanceiro.api.strategy;

import com.controlefinanceiro.api.model.Movimentacao;

import java.util.List;
import java.util.stream.Collectors;

public class RelatorioPorCategoriaStrategy implements RelatorioStrategy {
    private final String categoriaFiltro;

    public RelatorioPorCategoriaStrategy(String categoriaFiltro) {
        this.categoriaFiltro = categoriaFiltro;
    }

    @Override
    public List<Movimentacao> filtrar(List<Movimentacao> movimentacoes) {
        // Implementar a lógica de filtragem por categoria
        return movimentacoes.stream()
                .filter(mov -> mov.getCategoria().getNomeCategoria().name().equalsIgnoreCase(categoriaFiltro))
                .collect(Collectors.toList());
    }
}
