package com.controlefinanceiro.api.model;

import com.controlefinanceiro.api.enums.NomeCategoriaDespesaEnum;
import com.controlefinanceiro.api.enums.NomeCategoriaReceitaEnum;
import com.controlefinanceiro.api.enums.TipoCategoriaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoCategoriaEnum tipo;

    @Enumerated(EnumType.STRING)
    private NomeCategoriaDespesaEnum nomeDespesa;

    @Enumerated(EnumType.STRING)
    private NomeCategoriaReceitaEnum nomeReceita;

}
