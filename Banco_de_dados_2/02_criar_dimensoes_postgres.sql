-- Dimensão Tempo
-- Armazena atributos de datas para análise temporal
CREATE TABLE DimTempo (
    TempoKey INT PRIMARY KEY,         -- Chave primária da dimensão (pode ser um ID ou a própria data formatada como YYYYMMDD)
    Data DATE NOT NULL UNIQUE,        -- Data completa
    Ano INT NOT NULL,
    Mes INT NOT NULL,                 -- Número do mês (1-12)
    Dia INT NOT NULL,                 -- Dia do mês
    Trimestre INT NOT NULL,           -- Trimestre do ano (1-4)
    NomeMes VARCHAR(20) NOT NULL,     -- Ex: Janeiro, Fevereiro
    DiaDaSemana VARCHAR(20) NOT NULL, -- Ex: Segunda-feira, Terça-feira
    SemanaDoAno INT NOT NULL
);

-- Dimensão Tipo de Transação
-- Classifica as transações (ex: Receita, Despesa, Investimento)
CREATE TABLE DimTipoTransacao (
    TipoTransacaoKey SERIAL PRIMARY KEY,               -- Chave primária autoincremental (SERIAL é um atalho para integer e sequence)
    NomeTipoTransacao VARCHAR(100) NOT NULL UNIQUE,    -- Ex: 'Receita de Vendas', 'Despesa Administrativa', 'Aplicação Financeira'
    Categoria VARCHAR(50)                              -- Ex: 'Operacional', 'Não Operacional', 'Financeira'
);

-- Dimensão Conta
-- Descreve as contas financeiras (ex: conta bancária, carteira, cartão de crédito)
CREATE TABLE DimConta (
    ContaKey SERIAL PRIMARY KEY,                      -- Chave primária autoincremental
    NomeConta VARCHAR(100) NOT NULL,                  -- Ex: 'Banco XYZ C/C 12345-6', 'Carteira Principal', 'Cartão XPTO Final 5678'
    TipoConta VARCHAR(50),                            -- Ex: 'Conta Corrente', 'Conta Poupança', 'Cartão de Crédito', 'Investimento'
    InstituicaoFinanceira VARCHAR(100),               -- Ex: 'Banco XYZ S.A.', 'Corretora XPTO'
    CodigoConta VARCHAR(50) UNIQUE                    -- Código único da conta, se houver
);