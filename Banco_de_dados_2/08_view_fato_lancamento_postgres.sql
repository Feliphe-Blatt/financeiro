-- Conectado ao HomologacaoDB
-- Utiliza as FDWs já definidas e as tabelas de dimensão locais

CREATE OR REPLACE VIEW V_Fato_Lancamento_Financeiro_Origem AS
SELECT
    -- Chaves das Dimensões (PRECISAM ser buscadas nas tabelas de dimensão)
    (SELECT TempoKey FROM DimTempo dt WHERE dt.Data = tp.Data_Transacao LIMIT 1) AS TempoKey,
    (SELECT TipoTransacaoKey FROM DimTipoTransacao dtt WHERE dtt.NomeTipoTransacao = tp.Categoria_Transacao_Prod LIMIT 1) AS TipoTransacaoKey,
    (SELECT ContaKey FROM DimConta dc WHERE dc.CodigoConta = cp.Codigo_Unico_Prod LIMIT 1) AS ContaKey,
    -- Métricas
    tp.Valor_Transacao AS Valor,
    1 AS Quantidade -- Exemplo, se cada linha for uma transação
    -- tp.ID_Transacao_Prod -- Para referência/controle de carga
FROM
    Transacoes_Producao_FDW tp
JOIN
    Contas_Producao_FDW cp ON tp.ID_Conta_Prod = cp.ID_Conta_Prod -- Join para pegar Codigo_Unico_Prod da conta
WHERE
    (SELECT TempoKey FROM DimTempo dt WHERE dt.Data = tp.Data_Transacao LIMIT 1) IS NOT NULL AND
    (SELECT TipoTransacaoKey FROM DimTipoTransacao dtt WHERE dtt.NomeTipoTransacao = tp.Categoria_Transacao_Prod LIMIT 1) IS NOT NULL AND
    (SELECT ContaKey FROM DimConta dc WHERE dc.CodigoConta = cp.Codigo_Unico_Prod LIMIT 1) IS NOT NULL;
