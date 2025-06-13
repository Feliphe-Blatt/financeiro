-- Tabela Fato de Lançamentos Financeiros
-- Armazena os valores e métricas dos lançamentos, conectando-se às dimensões
CREATE TABLE FatoLancamentoFinanceiro (
    -- Chaves estrangeiras para as dimensões
    TempoKey INT NOT NULL,
    TipoTransacaoKey INT NOT NULL,
    ContaKey INT NOT NULL,
    
    -- Métricas (os fatos)
    Valor NUMERIC(18, 2) NOT NULL,    -- Valor do lançamento
    Quantidade INT NULL,              -- Quantidade, se aplicável
    SaldoAnterior NUMERIC(18,2) NULL, -- Saldo antes da transação na conta (exemplo de métrica semi-aditiva)
    SaldoPosterior NUMERIC(18,2) NULL, -- Saldo após da transação na conta (exemplo de métrica semi-aditiva)

    -- Chave primária da tabela fato
    PRIMARY KEY (TempoKey, TipoTransacaoKey, ContaKey /*, outras FKs que compõem a granularidade */),

    -- Constraints de Chave Estrangeira
    CONSTRAINT fk_tempo FOREIGN KEY (TempoKey) REFERENCES DimTempo(TempoKey),
    CONSTRAINT fk_tipotransacao FOREIGN KEY (TipoTransacaoKey) REFERENCES DimTipoTransacao(TipoTransacaoKey),
    CONSTRAINT fk_conta FOREIGN KEY (ContaKey) REFERENCES DimConta(ContaKey)
);