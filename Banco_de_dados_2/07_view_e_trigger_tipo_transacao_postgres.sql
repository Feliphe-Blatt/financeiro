-- Conectado ao HomologacaoDB (para a VIEW)
-- Assumindo FDW para Transacoes_Producao como Transacoes_Producao_FDW

CREATE FOREIGN TABLE IF NOT EXISTS Transacoes_Producao_FDW (
    ID_Transacao_Prod INT,
    ID_Conta_Prod INT,
    Data_Transacao DATE,
    Descricao_Transacao VARCHAR(255),
    Valor_Transacao NUMERIC(18,2),
    Tipo_Movimento VARCHAR(50),
    Categoria_Transacao_Prod VARCHAR(100)
) SERVER server_producao OPTIONS (schema_name 'public', table_name 'transacoes_producao');

-- View para identificar tipos de transação distintos da produção
CREATE OR REPLACE VIEW V_Tipos_Transacao_Origem AS
SELECT DISTINCT Categoria_Transacao_Prod
FROM Transacoes_Producao_FDW
WHERE Categoria_Transacao_Prod IS NOT NULL;

-- Para popular/atualizar DimTipoTransacao (exemplo de INSERT):
-- INSERT INTO DimTipoTransacao (NomeTipoTransacao, Categoria)
-- SELECT v.Categoria_Transacao_Prod, 'Default' -- Você pode adicionar lógica para Categoria
-- FROM V_Tipos_Transacao_Origem v
-- WHERE NOT EXISTS (SELECT 1 FROM DimTipoTransacao dtt WHERE dtt.NomeTipoTransacao = v.Categoria_Transacao_Prod);

-- TRIGGER (em ProducaoDB na Transacoes_Producao)
-- Conectado ao ProducaoDB
-- Assumindo FDW para HomologacaoDB.DimTipoTransacao como DimTipoTransacao_FDW

-- CREATE FOREIGN TABLE IF NOT EXISTS DimTipoTransacao_FDW (
--    TipoTransacaoKey INT,
--    NomeTipoTransacao VARCHAR(100),
--    Categoria VARCHAR(50)
-- ) SERVER server_homologacao OPTIONS (schema_name 'public', table_name 'dimtipotransacao');

CREATE OR REPLACE FUNCTION fn_sync_dim_tipo_transacao()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.Categoria_Transacao_Prod IS NOT NULL THEN
        INSERT INTO DimTipoTransacao_FDW (NomeTipoTransacao, Categoria)
        VALUES (NEW.Categoria_Transacao_Prod, 'Producao') -- Categoria pode ser definida
        ON CONFLICT (NomeTipoTransacao) DO NOTHING;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_transacoes_producao_after_insert_update_tipo
AFTER INSERT OR UPDATE ON Transacoes_Producao -- Tabela do ProducaoDB
FOR EACH ROW
WHEN (NEW.Categoria_Transacao_Prod IS NOT NULL)
EXECUTE FUNCTION fn_sync_dim_tipo_transacao();