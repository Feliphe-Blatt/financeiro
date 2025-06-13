-- Conectado ao HomologacaoDB

-- Função para ajudar a popular DimTempo (exemplo)
CREATE OR REPLACE FUNCTION popular_dim_tempo(data_inicio DATE, data_fim DATE)
RETURNS VOID AS $$
DECLARE
    data_atual DATE := data_inicio;
    nome_mes_pt TEXT[] := ARRAY['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
    dia_semana_pt TEXT[] := ARRAY['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'];
BEGIN
    WHILE data_atual <= data_fim LOOP
        INSERT INTO DimTempo (TempoKey, Data, Ano, Mes, Dia, Trimestre, NomeMes, DiaDaSemana, SemanaDoAno)
        VALUES (
            CAST(TO_CHAR(data_atual, 'YYYYMMDD') AS INT), -- TempoKey como YYYYMMDD
            data_atual,
            EXTRACT(YEAR FROM data_atual),
            EXTRACT(MONTH FROM data_atual),
            EXTRACT(DAY FROM data_atual),
            EXTRACT(QUARTER FROM data_atual),
            nome_mes_pt[EXTRACT(MONTH FROM data_atual)],
            dia_semana_pt[EXTRACT(DOW FROM data_atual) + 1], -- DOW é 0 para Domingo
            EXTRACT(WEEK FROM data_atual)
        )
        ON CONFLICT (TempoKey) DO NOTHING; -- Ou ON CONFLICT (Data) DO NOTHING se Data for a chave natural preferida
        data_atual := data_atual + INTERVAL '1 day';
    END LOOP;
END;
$$ LANGUAGE plpgsql;