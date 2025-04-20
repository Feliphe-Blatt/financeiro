package com.controlefinanceiro.api.model;

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
    private TipoCategoria tipo;

    public enum TipoCategoria {
        FIXA,
        VARIAVEL,
        EXTRA
    }

    @Enumerated(EnumType.STRING)
    private NomeCategoriaDespesa nomeDespesa;

    public enum NomeCategoriaDespesa {
        LAZER,
        EDUCACAO,
        MORADIA,
        TRANSPORTE,
        ALIMENTACAO,
        SAUDE,
        PRESENTES,
        PET,
        INVESTIMENTOS,
        ASSINATURAS,
        OUTROS
    }
    @Enumerated(EnumType.STRING)
    private NomeCategoriaReceita nomeReceita;

    public enum NomeCategoriaReceita {
        SALARIO,
        BONUS,
        FREELANCER,
        VENDA,
        RENDIMENTO,
        OUTROS
    }

}
