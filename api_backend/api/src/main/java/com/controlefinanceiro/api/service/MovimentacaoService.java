package com.controlefinanceiro.api.service;

import com.controlefinanceiro.api.config.TokenBlacklist;
import com.controlefinanceiro.api.dto.MovimentacaoDTO;
import com.controlefinanceiro.api.enums.TipoCategoriaEnum;
import com.controlefinanceiro.api.model.Categoria;
import com.controlefinanceiro.api.model.Movimentacao;
import com.controlefinanceiro.api.model.Usuario;
import com.controlefinanceiro.api.repository.CategoriaRepository;
import com.controlefinanceiro.api.repository.MovimentacaoRepository;
import com.controlefinanceiro.api.repository.UsuarioRepository;
import com.controlefinanceiro.api.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    public List<MovimentacaoDTO.MovimentacaoResponseDTO> getMovimentacoesUsuarioLogado() {
        Usuario usuario = obterUsuarioLogado();

        return movimentacaoRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(movimentacao -> {
                    MovimentacaoDTO.MovimentacaoResponseDTO dto = mapearParaDTO(movimentacao);
                    return dto;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoDTO.MovimentacaoResponseDTOTelaInicial> getMovimentacoesUsuarioTelaInicial() {
        Usuario usuario = obterUsuarioLogado();

        return movimentacaoRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(movimentacao -> {
                    MovimentacaoDTO.MovimentacaoResponseDTOTelaInicial dto = mapearParaDTOTelaInicial(movimentacao);
                    return dto;
                })
                .toList();
    }

    public BigDecimal calcularSaldoUsuarioLogado() {
        Usuario usuario = obterUsuarioLogado();
        BigDecimal saldo = movimentacaoRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(mov -> mov.isReceita() ? mov.getValor() : mov.getValor().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return saldo;
    }

    public List<Movimentacao> gerarRelatorio(RelatorioStrategy strategy) {
        Usuario usuario = obterUsuarioLogado();
        List<Movimentacao> todas = movimentacaoRepository.findByUsuarioId(usuario.getId());
        return strategy.filtrar(todas);
    }

    public List<MovimentacaoDTO.MovimentacaoResponseDTO> gerarRelatorioDTO(RelatorioStrategy strategy) {
        List<Movimentacao> filtradas = gerarRelatorio(strategy);
        return filtradas.stream()
                .map(this::mapearParaDTO)
                .toList();
    }

    public List<MovimentacaoDTO.MovimentacaoResponseDTO> relatorioPersonalizado(
            String categoria,
            String tipo,
            Boolean isReceita,
            LocalDate dataInicio,
            LocalDate dataFim,
            BigDecimal valorMinimo,
            BigDecimal valorMaximo
    ) {
        List<RelatorioStrategy> strategies = new ArrayList<>();
        if (categoria != null) strategies.add(new RelatorioPorCategoriaStrategy(categoria));
        if (tipo != null) strategies.add(new RelatorioPorTipoStrategy(tipo));
        if (isReceita != null) strategies.add(new RelatorioPorReceitaDespesaStrategy(isReceita));
        if (dataInicio != null || dataFim != null) strategies.add(new RelatorioPorDataStrategy(dataInicio, dataFim));
        if (valorMinimo != null || valorMaximo != null) strategies.add(new RelatorioPorValorStrategy(valorMinimo, valorMaximo));

        RelatorioCombinadoStrategy combinado = new RelatorioCombinadoStrategy(strategies);
        return gerarRelatorioDTO(combinado);
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
        String nomeCategoria = movimentacaoDTO.getCategoria();
        return categoriaRepository.findAll().stream()
                .filter(categoria -> categoria.getNomeCategoria().name().equalsIgnoreCase(nomeCategoria))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o nome: " + nomeCategoria));
    }

    private Usuario obterUsuarioLogado() {
        // Recupera o ID do usuário logado a partir do SecurityContextHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = (Usuario) principal;

        // Busca o usuário no banco de dados pelo ID
        return usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + usuario.getId()));
    }

    private MovimentacaoDTO.MovimentacaoResponseDTO mapearParaDTO(Movimentacao movimentacao) {
        MovimentacaoDTO.MovimentacaoResponseDTO dto = new MovimentacaoDTO.MovimentacaoResponseDTO();
        dto.setTipoDaMovimentacao(movimentacao.getTipo());
        dto.setCategoria(movimentacao.getCategoria().getNomeCategoria().toString());
        BigDecimal valor = movimentacao.isReceita()
                ? movimentacao.getValor()
                : movimentacao.getValor().negate();
        dto.setValor(valor);
        dto.setData(movimentacao.getData());
        dto.setDescricao(movimentacao.getDescricao());
        dto.setHoraMovimentacao(
                movimentacao.getHorarioMovimentacao() != null
                        ? movimentacao.getHorarioMovimentacao().truncatedTo(ChronoUnit.MINUTES)
                        : null
        );
        return dto;
    }

    private MovimentacaoDTO.MovimentacaoResponseDTOTelaInicial mapearParaDTOTelaInicial(Movimentacao movimentacao) {
        MovimentacaoDTO.MovimentacaoResponseDTOTelaInicial dto = new MovimentacaoDTO.MovimentacaoResponseDTOTelaInicial();
        dto.setCategoria(movimentacao.getCategoria().getNomeCategoria().toString());
        BigDecimal valor = movimentacao.isReceita()
                ? movimentacao.getValor()
                : movimentacao.getValor().negate();
        dto.setValor(valor);
        dto.setData(movimentacao.getData());
        dto.setHoraMovimentacao(
                movimentacao.getHorarioMovimentacao() != null
                        ? movimentacao.getHorarioMovimentacao().truncatedTo(ChronoUnit.MINUTES)
                        : null
        );
        return dto;
    }
}
