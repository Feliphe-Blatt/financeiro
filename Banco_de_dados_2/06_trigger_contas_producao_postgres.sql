-- Conectado ao ProducaoDB (onde Contas_Producao reside)
-- Este exemplo é mais conceitual e depende de como você configura o acesso cross-database para escrita.
-- Uma abordagem comum é usar postgres_fdw para criar uma tabela externa em ProducaoDB
-- que aponta para HomologacaoDB.DimConta.

-- Exemplo: Criar uma tabela externa em ProducaoDB que aponta para DimConta em HomologacaoDB
-- CREATE EXTENSION IF NOT EXISTS postgres_fdw; (Se não existir em ProducaoDB)
-- CREATE SERVER IF NOT EXISTS server_homologacao FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host 'localhost', dbname 'HomologacaoDB', port '5432');
-- CREATE USER MAPPING IF NOT EXISTS FOR CURRENT_USER SERVER server_homologacao OPTIONS (user 'usuario_postgres', password 'senha_postgres');
-- CREATE FOREIGN TABLE IF NOT EXISTS DimConta_FDW (
--     ContaKey INT,
--     NomeConta VARCHAR(100),
--     TipoConta VARCHAR(50),
--     InstituicaoFinanceira VARCHAR(100),
--     CodigoConta VARCHAR(50)
-- ) SERVER server_homologacao OPTIONS (schema_name 'public', table_name 'dimconta');


CREATE OR REPLACE FUNCTION fn_sync_dim_conta()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        -- Tenta inserir na DimConta no banco de homologação
        -- Usando a tabela externa DimConta_FDW
        INSERT INTO DimConta_FDW (NomeConta, TipoConta, InstituicaoFinanceira, CodigoConta)
        VALUES (NEW.Nome_Conta, NEW.Tipo_Conta_Prod, NEW.Instituicao_Financeira_Prod, NEW.Codigo_Unico_Prod)
        ON CONFLICT (CodigoConta) DO NOTHING; -- Ignora se já existir com mesmo CodigoConta
    ELSIF (TG_OP = 'UPDATE') THEN
        -- Atualiza a DimConta correspondente
        UPDATE DimConta_FDW
        SET NomeConta = NEW.Nome_Conta,
            TipoConta = NEW.Tipo_Conta_Prod,
            InstituicaoFinanceira = NEW.Instituicao_Financeira_Prod
        WHERE CodigoConta = NEW.Codigo_Unico_Prod;
    -- ELSIF (TG_OP = 'DELETE') THEN
        -- Tratar DELETE pode ser mais complexo (soft delete na dimensão?)
        -- DELETE FROM DimConta_FDW WHERE CodigoConta = OLD.Codigo_Unico_Prod;
    END IF;
    RETURN NULL; -- Resultado é ignorado para triggers AFTER
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_contas_producao_after_insert_update
AFTER INSERT OR UPDATE ON Contas_Producao -- Tabela do ProducaoDB
FOR EACH ROW
EXECUTE FUNCTION fn_sync_dim_conta();