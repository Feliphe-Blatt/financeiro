package com.controlefinanceiro.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Movimentacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToOne
    @JoinColumn(name = "valor_id")
    private Valor valor;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    private LocalDate data = LocalDate.now();
    
    private String descricao;
}
