# Sistema de Controle Financeiro

Um sistema completo de gerenciamento de finanças pessoais desenvolvido com Spring Boot (backend) e interface web responsiva (frontend).

## 🚀 Funcionalidades

- **Gestão de Transações**: Registro de receitas e despesas
- **Categorização Inteligente**: Classificação por tipos (fixas, variáveis, extras)
- **Categorias Personalizadas**: Organização detalhada das transações
- **Dashboard Interativo**: Visualização de dados financeiros em tempo real
- **Autenticação Segura**: Sistema de login e cadastro de usuários
- **Relatórios**: Geração de extratos e resumos financeiros
- **Exportação**: Download de dados em formato CSV
- **Interface Responsiva**: Compatível com desktop e mobile

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 11+**
- **Spring Boot** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **PostgreSQL** - Banco de dados
- **Hibernate** - ORM
- **Maven** - Gerenciamento de dependências
- **BCrypt** - Criptografia de senhas

### Frontend
- **HTML5** / **CSS3** / **JavaScript ES6+**
- **Bootstrap 5** - Framework CSS
- **Bootstrap Icons** - Iconografia
- **D3.js** - Visualização de dados
- **Material Icons** - Interface moderna

### DevOps
- **Docker** - Containerização
- **PostgreSQL** - Banco de dados

## 📁 Estrutura do Projeto

```
financeiro/
├── api_backend/
│   └── api/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/com/controlefinanceiro/api/
│       │   │   │   ├── config/         # Configurações (Security, CORS)
│       │   │   │   ├── controller/     # Controllers REST
│       │   │   │   ├── dto/           # Data Transfer Objects
│       │   │   │   ├── model/         # Entidades JPA
│       │   │   │   ├── repository/    # Repositórios
│       │   │   │   ├── service/       # Lógica de negócio
│       │   │   │   └── ApiApplication.java
│       │   │   └── resources/
│       │   │       ├── static/        # Frontend integrado
│       │   │       │   ├── styles/    # CSS
│       │   │       │   ├── scripts/   # JavaScript
│       │   │       │   ├── index.html
│       │   │       │   ├── login.html
│       │   │       │   ├── cadastro.html
│       │   │       │   └── transacoes.html
│       │   │       └── application.properties
│       │   └── test/
│       ├── pom.xml
│       └── Dockerfile
├── beta_version_front/                # Versão beta do frontend
│   ├── assets/
│   │   ├── css/
│   │   ├── js/
│   │   └── media/
│   └── index.html
├── docs/                             # Documentação
└── README.MD
```

## 🚀 Como Executar

### Pré-requisitos

- **Java 11+**
- **PostgreSQL 12+**
- **Maven 3.6+**
- **Docker** (opcional)

### Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL:
   ```sql
   CREATE DATABASE controle_financeiro;
   ```

2. Configure as credenciais em `api_backend/api/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/controle_financeiro
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   
   # Configurações JPA
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

### Execução Local

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Feliphe-Blatt/financeiro.git
   cd financeiro/api_backend/api
   ```

2. **Compile e execute o projeto**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Acesse a aplicação**
   - API: `http://localhost:8080`
   - Interface Web: `http://localhost:8080/login.html`

### Execução com Docker

```bash
# Em desenvolvimento - detalhes no Dockerfile
docker build -t controle-financeiro .
docker run -p 8080:8080 controle-financeiro
```

## 📊 Usando a Aplicação

### Primeiro Acesso

1. Acesse `http://localhost:8080/cadastro.html`
2. Crie sua conta de usuário
3. Faça login em `http://localhost:8080/login.html`
4. Comece a registrar suas transações no dashboard

### Funcionalidades Principais

- **Dashboard**: Visualize seu saldo atual e adicione transações rapidamente
- **Transações**: Gerencie todas as receitas e despesas com filtros avançados
- **Categorias**: Organize suas finanças por categorias personalizadas
- **Relatórios**: Exporte dados para análise externa

## 🧪 Testando a API

### Endpoints Principais

```
POST /api/auth/login          # Autenticação
POST /api/usuarios            # Cadastro de usuário
GET  /api/transacoes          # Listar transações
POST /api/transacoes          # Criar transação
PUT  /api/transacoes/{id}     # Atualizar transação
DELETE /api/transacoes/{id}   # Excluir transação
```

### Ferramentas de Teste

- **Insomnia/Postman**: Coleção utilizada em `docs/` e testes realizados ficam [AQUI](./api_backend/docs/testes-insomnia/).
- **Interface Web**: Use a interface integrada para testes funcionais
- **DBeaver**: Conectado ao PostgreSQL para verificar os dados, arquivo de exportação encontra-se também em `docs/` e testes do banco foram armazenados [AQUI](./api_backend/docs/testes-dbeaver/).

## 🔒 Segurança

- Autenticação baseada em tokens JWT
- Senhas criptografadas com BCrypt
- Configuração CORS para desenvolvimento
- Validação de dados de entrada
- Proteção contra CSRF

## 📈 Roadmap

- [ ] Implementação de gráficos avançados
- [ ] Categorias de despesas pré-definidas
- [ ] Metas financeiras
- [ ] Notificações por email
- [ ] Exportação para PDF

## 📖 Documentação

- [Documentação da API](./api_backend/docs/Documentação.MD)
- [Requisitos do Projeto](./projetoControleFinanceiro.pdf)

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autores

**Feliphe Blatt**
- GitHub: [@Feliphe-Blatt](https://github.com/Feliphe-Blatt)
- GitHub: [@Iarlon](https://github.com/Iarlon)
- GitHub: [@rafaellucas30290](https://github.com/rafaellucas30290)

---

⭐ Se este projeto te ajudou, considere dar uma estrela!
