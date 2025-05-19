package com.controlefinanceiro.api.service;

import com.controlefinanceiro.api.dto.MovimentacaoDTO;
import com.controlefinanceiro.api.enums.TipoCategoriaEnum;
import com.controlefinanceiro.api.model.Categoria;
import com.controlefinanceiro.api.model.Movimentacao;
import com.controlefinanceiro.api.model.Usuario;
import com.controlefinanceiro.api.repository.CategoriaRepository;
import com.controlefinanceiro.api.repository.MovimentacaoRepository;
import com.controlefinanceiro.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovimentacaoService {
    
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    
    @Transactional
    public void criarMovimentacao(MovimentacaoDTO.MovimentacaoRequestDTO movimentacaoDTO) {
        Movimentacao mov = new Movimentacao();
        mov.setReceita(movimentacaoDTO.isReceita());


        // Validar categoria inserida
        Categoria categoria = validarCategoria(movimentacaoDTO);

        // Validar se o tipo da categoria corresponde ao tipo da movimentação
        validarMovimentacaoIsReceita(movimentacaoDTO, categoria);

        // Criar a movimentação
        mov.setValor(movimentacaoDTO.getValor());
        mov.setTipo(movimentacaoDTO.getTipo());
        mov.setCategoria(categoria);
        mov.setData(movimentacaoDTO.getData());
        mov.setDescricao(movimentacaoDTO.getDescricao());

        Usuario usuario = obterUsuarioLogado();
        mov.setUsuario(usuario);
        
        movimentacaoRepository.save(mov);
        System.out.println("Movimentação criada com sucesso!");

    }

    @Transactional(readOnly = true)
    public List<Movimentacao> getMovimentacoesUsuarioLogado() {
        Usuario usuario = obterUsuarioLogado();

        return movimentacaoRepository.findByUsuarioId(usuario.getId());
    }

    private void validarMovimentacaoIsReceita(MovimentacaoDTO.MovimentacaoRequestDTO movimentacaoDTO, Categoria categoria) {
        boolean isReceita = movimentacaoDTO.isReceita();
        if ((isReceita && !"Receita".equals(categoria.getNomeCategoria().getTipo())) ||
                (!isReceita && !"Despesa".equals(categoria.getNomeCategoria().getTipo()))) {
            throw new IllegalArgumentException("A categoria selecionada não corresponde ao tipo da movimentação. ID: "
                    + movimentacaoDTO.getCategoria() + " / isReceita: " + movimentacaoDTO.isReceita());
        }
    }
    private Categoria validarCategoria(MovimentacaoDTO.MovimentacaoRequestDTO movimentacaoDTO) {
        return categoriaRepository.findById(movimentacaoDTO.getCategoria())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + movimentacaoDTO.getCategoria()));
    }

    private Usuario obterUsuarioLogado() {
        // Recupera o ID do usuário logado a partir do SecurityContextHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = (Usuario) principal;

        // Busca o usuário no banco de dados pelo ID
        return usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + usuario.getId()));
    }
}
