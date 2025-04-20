package com.controlefinanceiro.api.service;

import com.controlefinanceiro.api.dto.MovimentacaoDTO;
import com.controlefinanceiro.api.model.Categoria;
import com.controlefinanceiro.api.model.Movimentacao;
import com.controlefinanceiro.api.model.Usuario;
import com.controlefinanceiro.api.model.Valor;
import com.controlefinanceiro.api.repository.CategoriaRepository;
import com.controlefinanceiro.api.repository.MovimentacaoRepository;
import com.controlefinanceiro.api.repository.UsuarioRepository;
import com.controlefinanceiro.api.repository.ValorRepository;
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
    @Autowired
    private ValorRepository valorRepository;
    
    
    @Transactional
    public void criarMovimentacao(MovimentacaoDTO movimentacaoDTO) {
        
        // Criar e configurar o objeto Valor
        Valor valor = new Valor();
        valor.setValor(movimentacaoDTO.getValor().getValor());
        valor.setReceita("RECEITA".equals(movimentacaoDTO.getValor().getTipo()));
        
        // Salvar o objeto Valor primeiro
        valor = valorRepository.save(valor);
        
        // Criar e configurar o objeto Categoria
        Categoria categoria = new Categoria();
        categoria.setTipo(converteTipoCategoria(movimentacaoDTO.getCategoria().getTipo()));
        
        if (movimentacaoDTO.getCategoria().getNomeDespesa() != null && !movimentacaoDTO.getCategoria().getNomeDespesa().isEmpty()) {
            categoria.setNomeDespesa(converteNomeDespesa(movimentacaoDTO.getCategoria().getNomeDespesa()));
        }
        
        if (movimentacaoDTO.getCategoria().getNomeReceita() != null && !movimentacaoDTO.getCategoria().getNomeReceita().isEmpty()) {
            categoria.setNomeReceita(converteNomeReceita(movimentacaoDTO.getCategoria().getNomeReceita()));
        }
        
        // Validar a categoria, passando também o valor para verificar se é receita ou despesa
        validarCategoria(categoria, valor.isReceita());
        
        // Salvar o objeto Categoria antes de associá-lo à Movimentação
        categoria = categoriaRepository.save(categoria);
        
        // Criar a movimentação
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setValor(valor);
        movimentacao.setCategoria(categoria);
        movimentacao.setQuantidade(movimentacaoDTO.getQuantidade());
        
        // Associar ao usuário, se o ID do usuário estiver presente
        if (movimentacaoDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(movimentacaoDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + movimentacaoDTO.getUsuarioId()));
            movimentacao.setUsuario(usuario);
        }
        
        movimentacaoRepository.save(movimentacao);
        System.out.println("Movimentação criada com sucesso!");
    }
    
    private Categoria.TipoCategoria converteTipoCategoria(String tipo) {
        try {
            return Categoria.TipoCategoria.valueOf(tipo);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Tipo de categoria inválido: " + tipo);
        }
    }
    
    private Categoria.NomeCategoriaDespesa converteNomeDespesa(String nome) {
        try {
            return Categoria.NomeCategoriaDespesa.valueOf(nome);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Nome de despesa inválido: " + nome);
        }
    }
    
    private Categoria.NomeCategoriaReceita converteNomeReceita(String nome) {
        try {
            return Categoria.NomeCategoriaReceita.valueOf(nome);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Nome de receita inválido: " + nome);
        }
    }
    
    @Deprecated
    public void criarMovimentacao(Valor valor, Categoria categoria) {
        validarCategoria(categoria, valor.isReceita());
        
        // Salvar entidades antes de associá-las
        valor = valorRepository.save(valor);
        categoria = categoriaRepository.save(categoria);
        
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setValor(valor);
        movimentacao.setCategoria(categoria);
        
        movimentacaoRepository.save(movimentacao);
        System.out.println("Movimentação criada com sucesso!");
    }
    
    private void validarCategoria(Categoria categoria, boolean isReceita) {
        if (categoria.getTipo() == null) {
            throw new IllegalArgumentException("O tipo da categoria não pode ser nulo.");
        }
        
        // LÓGICA PARA DESPESAS
        if (!isReceita) {
            if (categoria.getTipo() == Categoria.TipoCategoria.FIXA || categoria.getTipo() == Categoria.TipoCategoria.VARIAVEL) {
                if (categoria.getNomeDespesa() == null || categoria.getNomeReceita() != null) {
                    throw new IllegalArgumentException("Categoria de despesa deve ter nomeDespesa preenchido e nomeReceita nulo.");
                }
            } else {
                throw new IllegalArgumentException("Tipo de categoria inválido para despesa. Deve ser FIXA ou VARIAVEL.");
            }
        } 
        // LÓGICA PARA RECEITAS
        else {
            if (categoria.getTipo() == Categoria.TipoCategoria.EXTRA) {
                if (categoria.getNomeReceita() == null || categoria.getNomeDespesa() != null) {
                    throw new IllegalArgumentException("Categoria de receita EXTRA deve ter nomeReceita preenchido e nomeDespesa nulo.");
                }
            } else if (categoria.getTipo() == Categoria.TipoCategoria.FIXA || categoria.getTipo() == Categoria.TipoCategoria.VARIAVEL) {
                if (categoria.getNomeReceita() == null || categoria.getNomeDespesa() != null) {
                    throw new IllegalArgumentException("Categoria de receita deve ter nomeReceita preenchido e nomeDespesa nulo.");
                }
            } else {
                throw new IllegalArgumentException("Tipo de categoria inválido.");
            }
        }
    }
}
