# Controle Financeiro API

API para gerenciamento de finanças pessoais desenvolvida com Spring Boot e PostgreSQL.

## Funcionalidades

- Registro de receitas e despesas
- Categorização de transações (fixas, variáveis, extras)
- Classificação detalhada por categorias personalizadas
- Gerenciamento de usuários

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Hibernate
- Maven

## Como Executar

### Pré-requisitos

- Java 11+
- PostgreSQL
- Maven

### Passos para Execução

1. Clone o repositório
   ```bash
   git clone [URL_DO_REPOSITORIO]
   cd api
   ```

2. Configure o banco de dados em `src/main/resources/application.properties`
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/controle_financeiro
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

3. Compile e execute o projeto
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. A API estará disponível em: `http://localhost:8080`

## Testando a API

### Com Insomnia

1. Importe a coleção de endpoints do diretório `docs/insomnia`
2. Execute as requisições disponíveis para testar os endpoints

### Com DBeaver

1. Conecte-se ao banco de dados PostgreSQL
2. Explore as tabelas criadas automaticamente pelo Hibernate

## Estrutura do Projeto

```
api/
├── src/
│   ├── main/
│   │   ├── java/com/controlefinanceiro/api/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── ApiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## Documentação da API

Para mais detalhes sobre os endpoints disponíveis e exemplos de uso, consulte a documentação completa [AQUI](https://github.com/Feliphe-Blatt/financeiro/blob/main/api_backend/docs/Documentação.MD).
