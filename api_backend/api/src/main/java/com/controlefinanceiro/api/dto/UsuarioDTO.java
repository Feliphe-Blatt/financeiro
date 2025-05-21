package com.controlefinanceiro.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    
    private Long id;
    private String nome;
    private String email;
    private String senha;

    public record UsuarioRequestDTO(
            @NotBlank(message = "O campo 'nome' é obrigatório.")
            String nome,
            @NotBlank(message = "O campo 'email' é obrigatório.")
            String email,
            @NotBlank(message = "O campo 'senha' é obrigatório.")
            String senha
    ) {
    }
    public record UsuarioResponseDTO(
            String nome,
            String email
    ) {
    }
}