package com.controlefinanceiro.api.repository;

import com.controlefinanceiro.api.model.Valor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ValorRepository extends JpaRepository<Valor, Long> {

}