# API de Controle Financeiro

Este projeto implementa uma API REST para gerenciamento financeiro pessoal, utilizando Spring Boot.

## Tecnologias Utilizadas

- Java 11+
- Spring Boot
- Spring Data JPA
- Maven 3.6+
- Lombok
- PostgreSQL 12+
- Insomnia (para testes da API)
- DBeaver (para visualização do banco de dados)

## Estrutura do Projeto

O projeto segue a arquitetura em camadas:
- **Model:** Entidades JPA que representam as tabelas do banco de dados.
- **Repository:** Interfaces que estendem JpaRepository para operações de persistência.
- **Service:** Classes contendo a lógica de negócio.
- **Controller:** Endpoints REST que recebem as requisições HTTP.
- **DTO:** Objetos para transferência de dados entre camadas.
- **Deserializer:** Classes para deserialização personalizada de dados JSON.

## Como Executar

1. Clone o repositório:
   ```sh
   git clone https://github.com/Feliphe-Blatt/financeiro.git
   ```
2. Configure o banco de dados no arquivo `application.properties`, localizado em `src/main/resources`.
3. Execute o comando:
   ```sh
   mvn spring-boot:run
   ```
4. Acesse a API em `http://localhost:8080`.

## Pré-requisitos

- Java 11 ou superior
- Maven 3.6 ou superior
- PostgreSQL 12 ou superior
- Insomnia (ou Postman) para testar a API
- DBeaver (opcional, para visualizar o banco de dados)

## Principais Entidades

- **Transacao:** Registra transações financeiras (receitas ou despesas).
- **Categoria:** Classifica as transações (FIXA, VARIAVEL ou EXTRA).
- **Usuario:** Proprietário das transações.

### Categorização de Transações

**Tipos de Categoria:**
- `FIXA`: Despesas ou receitas que ocorrem regularmente.
- `VARIAVEL`: Despesas ou receitas que variam em valor ou frequência.
- `EXTRA`: Receitas/Despesas extras ou não recorrentes.

**Categorias de Despesas:**
- LAZER, EDUCACAO, MORADIA, TRANSPORTE, ALIMENTACAO, SAUDE, PRESENTES, PET, INVESTIMENTOS, ASSINATURAS, OUTROS

**Categorias de Receitas:**
- SALARIO, BONUS, FREELANCER, VENDA, RENDIMENTO, OUTROS

## Funcionalidades

- Cadastro e autenticação de usuários (JWT)
- Registro de receitas e despesas
- Categorização de transações
- Dashboard e relatórios
- Exportação de dados

## Principais Endpoints

```http
POST   /api/auth/login         # Autenticação (JWT)
POST   /api/usuarios           # Cadastro de usuário
GET    /api/transacoes         # Listar transações
POST   /api/transacoes         # Criar transação
PUT    /api/transacoes/{id}    # Atualizar transação
DELETE /api/transacoes/{id}    # Excluir transação
```

> Para acessar endpoints protegidos, inclua o token JWT no header `Authorization`:
> ```
> Authorization: Bearer <seu_token_jwt>
> ```

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

- A tabela de transações armazena receitas e despesas categorizadas.
- A tabela de categorias possui diferentes tipos para despesas e receitas.

### Exemplos de Consulta SQL

```sql
-- Consultar todas as transações
SELECT * FROM transacao;

-- Consultar total de despesas
SELECT SUM(valor) FROM transacao WHERE tipo = 'DESPESA';

-- Consultar total de receitas
SELECT SUM(valor) FROM transacao WHERE tipo = 'RECEITA';

-- Consultar quantidade de transações por categoria
SELECT categoria_nome, COUNT(*) FROM transacao GROUP BY categoria_nome;
```

## Observações

- As operações financeiras são corretamente categorizadas como receitas ou despesas.
- As transações são associadas a um usuário específico do sistema.
- Para acessar endpoints protegidos, inclua o token JWT no header Authorization.

---
