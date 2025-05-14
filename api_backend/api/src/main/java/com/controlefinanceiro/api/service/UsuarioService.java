package com.controlefinanceiro.api.service;

import com.controlefinanceiro.api.dto.UsuarioDTO;
import com.controlefinanceiro.api.model.Usuario;
import com.controlefinanceiro.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;
    
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    /**
     * Busca todos os usuários cadastrados
     * @return Lista de usuários convertidos para DTO
     */
    public List<UsuarioDTO> buscarTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getId(), 
                        usuario.getNome(), 
                        usuario.getEmail(),
                        null)) // Não retornamos a senha
                .collect(Collectors.toList());
    }
    
    /**
     * Busca um usuário pelo ID
     * @param id ID do usuário
     * @return DTO do usuário encontrado ou null
     * @throws IllegalArgumentException se o usuário não for encontrado
     */
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
        
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                null); // Não retornamos a senha
    }
    
    /**
     * Cria um novo usuário
     * @param usuarioDTO DTO com os dados do usuário
     * @return DTO do usuário criado
     */
    @Transactional
    public void criar(UsuarioDTO.UsuarioRequestDTO usuarioDTO) {
        validarSeUsuarioJaExiste(usuarioDTO.email());
        Usuario usuario = new Usuario();

        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.senha()));

        usuarioRepository.save(usuario);
    }

    public void validarSeUsuarioJaExiste(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este email.");
        }
    }
    
    /**
     * Atualiza um usuário existente
     * @param id ID do usuário
     * @param usuarioDTO DTO com os dados atualizados
     * @return DTO do usuário atualizado
     * @throws IllegalArgumentException se o usuário não for encontrado
     */
    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
        
        // Atualizar campos
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        
        // Atualizar senha apenas se fornecida
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
            usuario.setSenha(usuarioDTO.getSenha());
        }
        
        // Salvar no banco de dados
        usuario = usuarioRepository.save(usuario);
        
        // Converter entidade para DTO de resposta
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                null); // Não retornamos a senha
    }
    
    /**
     * Remove um usuário pelo ID
     * @param id ID do usuário
     * @throws IllegalArgumentException se o usuário não for encontrado
     */
    @Transactional
    public void remover(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
        }
        
        usuarioRepository.deleteById(id);
    }
}
