# Documentação Inicial do Projeto Controle Financeiro API

## Visão Geral

A Controle Financeiro API é uma aplicação Spring Boot que permite gerenciar movimentações financeiras, categorizando receitas e despesas de acordo com tipos específicos. O sistema possibilita o controle financeiro pessoal através de uma API REST.

## Estrutura do Projeto

### Principais Entidades

- **Movimentacao**: Registra transações financeiras
- **Categoria**: Classifica as movimentações (FIXA, VARIAVEL ou EXTRA)
- **Valor**: Armazena o valor monetário e identifica se é receita ou despesa
- **Usuario**: Proprietário das movimentações

### Categorização de Transações

**Tipos de Categoria:**
- `FIXA`: Despesas ou receitas que ocorrem regularmente
- `VARIAVEL`: Despesas ou receitas que variam em valor ou frequência
- `EXTRA`: Receitas extras ou não-recorrentes

**Categorias de Despesas:**
- LAZER, EDUCACAO, MORADIA, TRANSPORTE, ALIMENTACAO, SAUDE, PRESENTES, PET, INVESTIMENTOS, ASSINATURAS, OUTROS

**Categorias de Receitas:**
- SALARIO, BONUS, FREELANCER, VENDA, RENDIMENTO, OUTROS

## Configuração do Ambiente

### Requisitos

- Java 11+
- PostgreSQL
- Maven

### Configurações de Banco de Dados

A aplicação utiliza PostgreSQL configurado no arquivo `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/controle_financeiro
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

## API Endpoints

### Criação de Movimentação

**Endpoint**: `POST /api/movimentacoes`

**Corpo da requisição**:
```json
{
  "valor": {
    "valor": 150.00,
    "tipo": "DESPESA"  // ou "RECEITA"
  },
  "categoria": {
    "tipo": "VARIAVEL",  // FIXA, VARIAVEL ou EXTRA
    "nomeDespesa": "ALIMENTACAO",  // Nome predefinido ou null, mais detalhes na documentação completa
    "nomeReceita": null  // nome predefinido ou null
  },
  "quantidade": 1,
  "usuarioId": 1
}
```

**Regras de negócio:**
1. Despesas devem ser do tipo FIXA ou VARIAVEL e ter nomeDespesa preenchido
2. Receitas podem ser FIXA, VARIAVEL ou EXTRA e devem ter nomeReceita preenchido

## Como Testar a API

### Usando o Insomnia

1. Importe a coleção de endpoints para o Insomnia
2. Configure o ambiente para apontar para `http://localhost:8080`
3. Execute as requisições disponíveis

### Usando o DBeaver

1. Conecte-se ao PostgreSQL usando as credenciais do `application.properties`
2. Visualize as tabelas:
   - movimentacao
   - categoria
   - valor
   - usuario

## Configuração do Banco de Dados

A aplicação utiliza Hibernate com estratégia de atualização automática (`ddl-auto=update`), que cria/atualiza as tabelas automaticamente no startup.

## Desenvolvimento e Contribuição

1. Clone o repositório
2. Importe como projeto Maven na sua IDE
3. Execute a aplicação localmente
4. Faça requisições para testar a API
