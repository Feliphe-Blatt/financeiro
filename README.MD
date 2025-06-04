# Sistema de Controle Financeiro

Um sistema completo de gerenciamento de finanças pessoais desenvolvido com Spring Boot (backend) e front-end web responsivo embarcado.

## 🚀 Funcionalidades

- **Gestão de Transações**: Registro, filtro e exportação de receitas e despesas
- **Categorização Inteligente**: Classificação por tipos (fixas, variáveis, extras)
- **Categorias Personalizadas**: Organização detalhada das transações
- **Dashboard Interativo**: Visualização de dados financeiros em tempo real (gráficos)
- **Autenticação Segura**: Login/cadastro, controle de sessão JWT
- **Relatórios e Exportação**: Geração de extratos e download CSV
- **Interface Responsiva**: Compatível com desktop e mobile

## 🛠️ Tecnologias Utilizadas

### Backend
- Java 11+
- Spring Boot, Spring Data JPA, Spring Security
- PostgreSQL, Hibernate
- Maven, Docker
- BCrypt

### Frontend (integrado em `/api_backend/api/src/main/resources/static`)
- HTML5, CSS3, JavaScript ES6+
- Bootstrap 5, Material Icons
- Chart.js (via CDN) para gráficos
- Estrutura modular: cada tela tem seu próprio HTML + JS dedicado

#### Principais arquivos front-end:
- `index.html`: Dashboard principal, gráficos e saldo
- `transacoes.html`: Listagem, filtro e exportação de transações
- `login.html`: Tela de login
- `cadastro.html`: Tela de cadastro de usuário
- `config.html`: Configurações do usuário/sistema
- `scripts/`: 
  - `auth.js`, `login.js`, `cadastro.js`: autenticação e cadastro
  - `dashboard.js`: dashboard e gráficos
  - `transacoes.js`: lógica das transações
  - `config.js`: preferências/configurações
  - `tema.js`: tema escuro/claro

#### Exemplo de estrutura:
```
api_backend/api/src/main/resources/static/
├── index.html
├── transacoes.html
├── login.html
├── cadastro.html
├── config.html
├── scripts/
│   ├── auth.js
│   ├── login.js
│   ├── cadastro.js
│   ├── dashboard.js
│   ├── transacoes.js
│   ├── config.js
│   └── tema.js
├── styles/
│   └── style.css
```

## 📊 Usando a Aplicação

1. **Acesse `/login.html`** para entrar ou registrar um usuário novo (`/cadastro.html`)
2. **Após login**, navegue pelo dashboard (`/index.html`), consulte e filtre transações (`/transacoes.html`), exporte dados e acesse suas configurações (`/config.html`)
3. Todos os recursos são acessíveis através do menu lateral das páginas

## 🔒 Segurança
- Autenticação via JWT (token salvo em LocalStorage)
- Senhas criptografadas (BCrypt)
- Validação de entrada e proteção anti-CSRF

## 🧪 Testes
- Teste via interface web
- Testes automatizados com Insomnia/Postman disponíveis em `docs/`
- Testes de banco de dados com DBeaver

## 📈 Roadmap
- [ ] Gráficos avançados (mais tipos)
- [ ] Metas financeiras e notificações
- [ ] Exportação para PDF
- [ ] Dashboard personalizável

## 📄 Licença e Contribuição

Este projeto está sob licença MIT.  
Contribuições são bem-vindas!

## 👨‍💻 Autores

**Feliphe Blatt**
- GitHub: [@Feliphe-Blatt](https://github.com/Feliphe-Blatt)
- GitHub: [@Iarlon](https://github.com/Iarlon)
- GitHub: [@rafaellucas30290](https://github.com/rafaellucas30290)

---

⭐ Se este projeto te ajudou, deixe sua estrela!
