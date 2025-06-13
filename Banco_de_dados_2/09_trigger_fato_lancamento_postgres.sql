-- Conectado ao ProducaoDB
-- Assumindo FDWs para DimTempo, DimTipoTransacao, DimConta e FatoLancamentoFinanceiro em HomologacaoDB

-- CREATE FOREIGN TABLE IF NOT EXISTS FatoLancamentoFinanceiro_FDW (
--    TempoKey INT,
--    TipoTransacaoKey INT,
--    ContaKey INT,
--    Valor NUMERIC(18,2),
--    Quantidade INT
-- ) SERVER server_homologacao OPTIONS (schema_name 'public', table_name 'fatolancamentofinanceiro');
-- (FDWs para DimTempo, DimTipoTransacao, DimConta também seriam necessárias aqui se não definidas globalmente)

CREATE OR REPLACE FUNCTION fn_insere_fato_lancamento()
RETURNS TRIGGER AS $$
DECLARE
    v_TempoKey INT;
    v_TipoTransacaoKey INT;
    v_ContaKey INT;
    v_CodigoContaProd VARCHAR;
BEGIN
    -- Garantir que a data exista em DimTempo (poderia ser um trigger separado em DimTempo)
    INSERT INTO DimTempo_FDW (TempoKey, Data, Ano, Mes, Dia, Trimestre, NomeMes, DiaDaSemana, SemanaDoAno)
    SELECT CAST(TO_CHAR(NEW.Data_Transacao, 'YYYYMMDD') AS INT),
           NEW.Data_Transacao,
           EXTRACT(YEAR FROM NEW.Data_Transacao),
           EXTRACT(MONTH FROM NEW.Data_Transacao),
           EXTRACT(DAY FROM NEW.Data_Transacao),
           EXTRACT(QUARTER FROM NEW.Data_Transacao),
           (ARRAY['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'])[EXTRACT(MONTH FROM NEW.Data_Transacao)],
           (ARRAY['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'])[EXTRACT(DOW FROM NEW.Data_Transacao) + 1],
           EXTRACT(WEEK FROM NEW.Data_Transacao)
    ON CONFLICT (TempoKey) DO NOTHING;

    -- Lookup das chaves das dimensões (acessando as tabelas FDW que apontam para HomologacaoDB)
    SELECT TempoKey INTO v_TempoKey FROM DimTempo_FDW WHERE Data = NEW.Data_Transacao LIMIT 1;

    IF NEW.Categoria_Transacao_Prod IS NOT NULL THEN
        INSERT INTO DimTipoTransacao_FDW (NomeTipoTransacao) VALUES (NEW.Categoria_Transacao_Prod) ON CONFLICT (NomeTipoTransacao) DO NOTHING;
        SELECT TipoTransacaoKey INTO v_TipoTransacaoKey FROM DimTipoTransacao_FDW WHERE NomeTipoTransacao = NEW.Categoria_Transacao_Prod LIMIT 1;
    END IF;

    SELECT Codigo_Unico_Prod INTO v_CodigoContaProd FROM Contas_Producao WHERE ID_Conta_Prod = NEW.ID_Conta_Prod;
    IF v_CodigoContaProd IS NOT NULL THEN
        -- Assumindo que a DimConta já foi populada pelo trigger em Contas_Producao
        SELECT ContaKey INTO v_ContaKey FROM DimConta_FDW WHERE CodigoConta = v_CodigoContaProd LIMIT 1;
    END IF;

    -- Insere na FatoLancamentoFinanceiro se todas as chaves foram encontradas
    IF v_TempoKey IS NOT NULL AND v_TipoTransacaoKey IS NOT NULL AND v_ContaKey IS NOT NULL THEN
        INSERT INTO FatoLancamentoFinanceiro_FDW (TempoKey, TipoTransacaoKey, ContaKey, Valor, Quantidade)
        VALUES (v_TempoKey, v_TipoTransacaoKey, v_ContaKey, NEW.Valor_Transacao, 1)
        ON CONFLICT (TempoKey, TipoTransacaoKey, ContaKey) DO UPDATE -- Ou DO NOTHING, dependendo da estratégia
        SET Valor = EXCLUDED.Valor, Quantidade = EXCLUDED.Quantidade; -- Exemplo de atualização se houver conflito
    ELSE
        -- Logar falha ou tratar de alguma forma se chaves de dimensão não forem encontradas
        RAISE WARNING 'Não foi possível inserir na Fato: chaves de dimensão não encontradas para Transacao_Prod ID %', NEW.ID_Transacao_Prod;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_transacoes_producao_after_insert_fato
AFTER INSERT ON Transacoes_Producao -- Tabela do ProducaoDB
FOR EACH ROW
EXECUTE FUNCTION fn_insere_fato_lancamento();
