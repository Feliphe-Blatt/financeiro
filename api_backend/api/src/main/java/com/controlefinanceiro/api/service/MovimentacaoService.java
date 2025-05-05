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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovimentacaoService {
    
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    
    @Transactional
    public void criarMovimentacao(MovimentacaoDTO movimentacaoDTO) {
        Movimentacao mov = new Movimentacao();
        mov.setReceita(movimentacaoDTO.isReceita());

        // Validar categoria inserida
        Categoria categoria = categoriaRepository.findById(movimentacaoDTO.getCategoria()).orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + movimentacaoDTO.getCategoria()));

        // Validar se o tipo da categoria corresponde ao tipo da movimentação
        boolean isReceita = movimentacaoDTO.isReceita();
        if ((isReceita && !"Receita".equals(categoria.getNomeCategoria().getTipo())) ||
                (!isReceita && !"Despesa".equals(categoria.getNomeCategoria().getTipo()))) {
            throw new IllegalArgumentException("A categoria selecionada não corresponde ao tipo da movimentação. ID: " + movimentacaoDTO.getCategoria() + " / isReceita: " + movimentacaoDTO.isReceita());
        }

        // Validar e definir o tipo da movimentação
//        TipoCategoriaEnum tipo = movimentacaoDTO.getTipo();
//        try {
//            tipo = TipoCategoriaEnum.valueOf(movimentacaoDTO.getTipo().name());
//        } catch (IllegalArgumentException | NullPointerException exceptionTipo) {
//            throw new IllegalArgumentException("O tipo da movimentação é inválido. Valores permitidos: FIXA, VARIAVEL ou EXTRA.");
//        }
//        mov.setTipo(tipo);
        
        // Criar a movimentação
        mov.setValor(movimentacaoDTO.getValor());
        mov.setTipo(movimentacaoDTO.getTipo());
        mov.setCategoria(categoria);
        mov.setData(movimentacaoDTO.getData());
        mov.setDescricao(movimentacaoDTO.getDescricao());
        mov.setTipo(movimentacaoDTO.getTipo());
        
        // Associar ao usuário, se o ID do usuário estiver presente
        if (movimentacaoDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(movimentacaoDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + movimentacaoDTO.getUsuarioId()));
            mov.setUsuario(usuario);
        }
        
        movimentacaoRepository.save(mov);
        System.out.println("Movimentação criada com sucesso!");

    }
}
