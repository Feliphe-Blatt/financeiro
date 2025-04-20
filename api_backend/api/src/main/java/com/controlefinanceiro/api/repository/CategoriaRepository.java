package com.controlefinanceiro.api.repository;

import com.controlefinanceiro.api.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
  
  /**
   * Busca uma categoria pelo tipo e nome da despesa
   *
   * @param tipo o tipo da categoria
   * @param nomeDespesa o nome da despesa
   * @return a categoria encontrada ou null
   */
  Categoria findByTipoAndNomeDespesa(Categoria.TipoCategoria tipo, Categoria.NomeCategoriaDespesa nomeDespesa);
  
  /**
   * Busca uma categoria pelo tipo e nome da receita
   *
   * @param tipo o tipo da categoria
   * @param nomeReceita o nome da receita
   * @return a categoria encontrada ou null
   */
  Categoria findByTipoAndNomeReceita(Categoria.TipoCategoria tipo, Categoria.NomeCategoriaReceita nomeReceita);
}