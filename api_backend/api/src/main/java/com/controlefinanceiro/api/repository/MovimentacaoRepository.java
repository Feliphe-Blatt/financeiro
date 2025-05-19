package com.controlefinanceiro.api.repository;

import com.controlefinanceiro.api.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByUsuarioId(Long usuarioId);
}
