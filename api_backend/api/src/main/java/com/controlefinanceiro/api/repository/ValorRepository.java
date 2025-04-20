package com.controlefinanceiro.api.repository;

import com.controlefinanceiro.api.model.Valor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ValorRepository extends JpaRepository<Valor, Long> {
  
  /**
   * Busca valores por montante
   *
   * @param valor o valor monetário a ser buscado
   * @return lista de valores correspondentes
   */
  List<Valor> findByValor(BigDecimal valor);
  
  /**
   * Busca valores por tipo (receita ou despesa)
   *
   * @param isReceita true para receitas, false para despesas
   * @return lista de valores correspondentes
   */
  List<Valor> findByReceita(boolean isReceita);
}