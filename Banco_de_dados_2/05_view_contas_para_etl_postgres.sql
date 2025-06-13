-- Conectado ao HomologacaoDB
-- Assumindo que 'server_producao' é um servidor externo FDW configurado apontando para ProducaoDB
-- e que o mapeamento de usuário permite ler 'Contas_Producao'

CREATE FOREIGN TABLE IF NOT EXISTS Contas_Producao_FDW (
    ID_Conta_Prod INT,
    Nome_Conta VARCHAR(255),
    Tipo_Conta_Prod VARCHAR(50),
    Instituicao_Financeira_Prod VARCHAR(100),
    Codigo_Unico_Prod VARCHAR(50)
) SERVER server_producao OPTIONS (schema_name 'public', table_name 'contas_producao'); -- Ajuste schema_name se necessário

-- View para facilitar a carga na DimConta
CREATE OR REPLACE VIEW V_Contas_Para_DimConta AS
SELECT
    cp.Nome_Conta AS NomeConta,
    cp.Tipo_Conta_Prod AS TipoConta,
    cp.Instituicao_Financeira_Prod AS InstituicaoFinanceira,
    cp.Codigo_Unico_Prod AS CodigoConta,
    cp.ID_Conta_Prod AS ChaveOrigemProd -- Para referência e evitar duplicatas
FROM
    Contas_Producao_FDW cp;

-- Para popular/atualizar DimConta (exemplo de INSERT, um processo ETL faria UPSERT):
-- INSERT INTO DimConta (NomeConta, TipoConta, InstituicaoFinanceira, CodigoConta)
-- SELECT v.NomeConta, v.TipoConta, v.InstituicaoFinanceira, v.CodigoConta FROM V_Contas_Para_DimConta v
-- WHERE NOT EXISTS (SELECT 1 FROM DimConta dc WHERE dc.CodigoConta = v.CodigoConta);