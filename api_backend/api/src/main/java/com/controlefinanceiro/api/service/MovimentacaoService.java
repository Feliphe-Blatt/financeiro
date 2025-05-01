package com.controlefinanceiro.api.service;

import com.controlefinanceiro.api.dto.MovimentacaoDTO;
import com.controlefinanceiro.api.enums.NomeCategoriaDespesaEnum;
import com.controlefinanceiro.api.enums.NomeCategoriaReceitaEnum;
import com.controlefinanceiro.api.enums.TipoCategoriaEnum;
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
        categoria.setTipo(movimentacaoDTO.getCategoria().getTipo());
        
        if (movimentacaoDTO.getCategoria().getNomeDespesa() != null) {
            categoria.setNomeDespesa(movimentacaoDTO.getCategoria().getNomeDespesa());
        }
        
        if (movimentacaoDTO.getCategoria().getNomeReceita() != null) {
            categoria.setNomeReceita(movimentacaoDTO.getCategoria().getNomeReceita());
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
            if (categoria.getTipo() == TipoCategoriaEnum.FIXA || categoria.getTipo() == TipoCategoriaEnum.VARIAVEL) {
                if (categoria.getNomeDespesa() == null || categoria.getNomeReceita() != null) {
                    throw new IllegalArgumentException("Categoria de despesa deve ter nomeDespesa preenchido e nomeReceita nulo.");
                }
            } else {
                throw new IllegalArgumentException("Tipo de categoria inválido para despesa. Deve ser FIXA ou VARIAVEL.");
            }
        } 
        // LÓGICA PARA RECEITAS
        else {
            if (categoria.getTipo() == TipoCategoriaEnum.EXTRA) {
                if (categoria.getNomeReceita() == null || categoria.getNomeDespesa() != null) {
                    throw new IllegalArgumentException("Categoria de receita EXTRA deve ter nomeReceita preenchido e nomeDespesa nulo.");
                }
            } else if (categoria.getTipo() == TipoCategoriaEnum.FIXA || categoria.getTipo() == TipoCategoriaEnum.VARIAVEL) {
                if (categoria.getNomeReceita() == null || categoria.getNomeDespesa() != null) {
                    throw new IllegalArgumentException("Categoria de receita deve ter nomeReceita preenchido e nomeDespesa nulo.");
                }
            } else {
                throw new IllegalArgumentException("Tipo de categoria inválido.");
            }
        }
    }
}
