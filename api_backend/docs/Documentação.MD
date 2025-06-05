# api-controle-financeiro
 
# API de Controle Financeiro

Este projeto implementa uma API REST para gerenciamento financeiro pessoal utilizando Spring Boot.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Maven
- Lombok
- PostgreSQL
- Insomnia
- DBeaver

## Estrutura do Projeto

O projeto segue uma arquitetura em camadas:

- **Model**: Entidades JPA que representam as tabelas do banco de dados
- **Repository**: Interfaces que estendem JpaRepository para operações de persistência
- **Service**: Classes que implementam a lógica de negócio
- **Controller**: Endpoints REST que recebem as requisições HTTP
- **DTO**: Objetos de transferência de dados entre camadas
- **Deserializer**: Classes para deserialização personalizada de dados JSON

## Como Executar

1. Clone o repositório
2. Configure o banco de dados no arquivo `application.properties` localizado em `resources`
3. Execute o comando: `mvn spring-boot:run`

## Pré-requisitos

- Java 11+
- Maven 3.6+
- PostgreSQL 12+
- Insomnia (para testes de API)
- DBeaver (para visualização do banco de dados)

## Principais Entidades

- **Transacao**: Registra transações financeiras (receitas ou despesas)
- **Categoria**: Classifica as transações (FIXA, VARIAVEL ou EXTRA)
- **Usuario**: Proprietário das transações

### Categorização de Transações

**Tipos de Categoria:**
- `FIXA`: Despesas ou receitas que ocorrem regularmente
- `VARIAVEL`: Despesas ou receitas que variam em valor ou frequência
- `EXTRA`: Receitas extras ou não-recorrentes

**Categorias de Despesas:**
- LAZER, EDUCACAO, MORADIA, TRANSPORTE, ALIMENTACAO, SAUDE, PRESENTES, PET, INVESTIMENTOS, ASSINATURAS, OUTROS

**Categorias de Receitas:**
- SALARIO, BONUS, FREELANCER, VENDA, RENDIMENTO, OUTROS

## Funcionalidades

- Cadastro e autenticação de usuários (JWT)
- Registro de receitas e despesas
- Categorização das transações
- Dashboard e relatórios
- Exportação de dados

## Endpoints Principais

```
POST   /api/auth/login         # Autenticação (JWT)
POST   /api/usuarios           # Cadastro de usuário
GET    /api/transacoes         # Listar transações
POST   /api/transacoes         # Criar transação
PUT    /api/transacoes/{id}    # Atualizar transação
DELETE /api/transacoes/{id}    # Excluir transação
```

### Exemplo de Payload para Criação de Transação

```json
{
  "valor": 150.00,
  "tipo": "DESPESA",
  "categoria": {
    "tipo": "VARIAVEL",
    "nome": "ALIMENTACAO"
  },
  "usuarioId": 1,
  "quantidade": 1
}
```

- `tipo`: "DESPESA" ou "RECEITA"
- `categoria.tipo`: "FIXA", "VARIAVEL" ou "EXTRA"
- `categoria.nome`: Nome da categoria (ex: "ALIMENTACAO", "SALARIO")

### Resposta

Status 201 (Created) e objeto da transação criada.

## Segurança

- Autenticação baseada em tokens JWT
- Senhas criptografadas com BCrypt
- Configuração CORS para desenvolvimento
- Validação de dados de entrada
- Proteção contra CSRF

## Exemplos de Uso

### Testando a API com Insomnia

#### Criação de Transação - Despesa (POST)

```json
{
  "valor": 150.00,
  "tipo": "DESPESA",
  "categoria": {
    "tipo": "VARIAVEL",
    "nome": "ALIMENTACAO"
  },
  "usuarioId": 1,
  "quantidade": 1
}
```

#### Criação de Transação - Receita (POST)

```json
{
  "valor": 2500.00,
  "tipo": "RECEITA",
  "categoria": {
    "tipo": "FIXA",
    "nome": "SALARIO"
  },
  "usuarioId": 1,
  "quantidade": 1
}
```

## Visualização no Banco de Dados (DBeaver)

- Tabela de transações armazena receitas e despesas categorizadas
- Tabela de categorias com diferentes tipos para despesas e receitas

### Consulta SQL

```sql
-- Consultar todas as transações
SELECT * FROM transacao;

-- Consultar total de despesas
SELECT SUM(valor) FROM transacao WHERE tipo = 'DESPESA';

-- Consultar total de receitas
SELECT SUM(valor) FROM transacao WHERE tipo = 'RECEITA';

-- Consultar transações por categoria
SELECT categoria, COUNT(*) FROM transacao GROUP BY categoria;
```

## Observações

- As operações financeiras são corretamente categorizadas como receitas ou despesas
- As transações são associadas a um usuário específico do sistema
- Para acessar endpoints protegidos, inclua o token JWT no header Authorization

---
