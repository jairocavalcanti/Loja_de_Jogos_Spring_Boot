# 🎮 Lambda Games — E-commerce API

> Backend robusto em Java e Spring Boot para e-commerce de jogos digitais, com autenticação JWT, integração com pagamentos via Mercado Pago (Pix) e OAuth2 Google.

---

## 💻 Sobre o Projeto

O **Lambda Games API** é uma solução backend para gerenciamento e venda de jogos eletrônicos. A aplicação contempla:

- **Autenticação & Segurança:** Cadastro e login com senhas criptografadas via BCrypt, geração de tokens JWT stateless e suporte a login social via Google OAuth2.
- **Catálogo de Jogos:** Operações completas de CRUD para títulos com filtragem por ID, nome e categorização por gênero.
- **Carrinho de Compras:** Fluxo dinâmico de inclusão, remoção, cálculo em tempo real do valor total e persistência vinculada ao CPF do usuário.
- **Checkout & Pagamentos:** Geração instantânea de cobrança Pix via API do Mercado Pago, consulta de status do pedido e recepção de notificações via Webhooks com validação de assinatura (`x-signature`).

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem & Runtime:** Java 17
- **Framework Principal:** Spring Boot 3.4.1
- **Módulos Spring:**
  - Spring Data JPA (Hibernate)
  - Spring Web (RESTful API)
  - Spring Security
  - Spring OAuth2 Client
  - Spring Validation
- **Segurança & Tokens:** Auth0 Java JWT (v4.4.0), BCrypt
- **Banco de Dados:** MySQL com driver `mysql-connector-j`
- **Pagamentos & Integrações:** API Mercado Pago (Pix + Webhooks)
- **Gerenciador de Dependências:** Maven

---

## ⚙️ Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou superior
- [Maven](https://maven.apache.org/) (opcional, pode-se usar o wrapper `./mvnw`)
- [MySQL](https://www.mysql.com/) 8.0+
- Credenciais da conta de desenvolvedor do [Mercado Pago](https://www.mercadopago.com.br/developers)
- Credenciais da API [Google Cloud Console](https://console.cloud.google.com/) (OAuth 2.0)

---

## 🔧 Configuração e Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto (carregado automaticamente via biblioteca `dotenv-java` na inicialização da aplicação) com os seguintes parâmetros:

\`\`\`env
# Banco de Dados
DB_URL=jdbc:mysql://localhost:3306/Pessoas
DB_USERNAME=root
DB_PASSWORD=sua_senha_mysql

# Segurança & JWT
API_SECRET_KEY=sua_chave_secreta_jwt_longa_e_segura

# Mercado Pago
MERCADOPAGO_ACCESS_TOKEN=seu_access_token_mercado_pago
MERCADOPAGO_WEBHOOK_SECRET=seu_webhook_secret_mercado_pago

# Google OAuth2
GOOGLE_CLIENT_ID=seu_google_client_id.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=seu_google_client_secret
\`\`\`

---

## 🚀 Como Executar

### 1. Clonar o repositório
\`\`\`bash
git clone https://github.com/seu-usuario/lambda-games.git
cd lambda-games
\`\`\`

### 2. Configurar o Banco de Dados
Certifique-se de que o serviço MySQL esteja em execução e crie o schema configurado:
\`\`\`sql
CREATE DATABASE IF NOT EXISTS Pessoas;
\`\`\`

### 3. Rodar a aplicação
No terminal, execute:
\`\`\`bash
# Linux/macOS
./mvnw spring-boot:run

# Windows (CMD / PowerShell)
./mvnw.cmd spring-boot:run
\`\`\`

A aplicação estará disponível em: `http://localhost:8080` (CORS configurado por padrão para frontend em `http://localhost:5173`).

---

## 🛣️ Endpoints da API

### 🔐 Autenticação (`/auth`)

| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| `POST` | `/auth/register` | Cadastra um novo cliente e retorna o token JWT | Público |
| `POST` | `/auth/login` | Realiza autenticação com email e senha | Público |

<details>
<summary>Exemplo de Payload: Registro (clique para expandir)</summary>

\`\`\`json
{
  "nome": "João Silva",
  "idade": 25,
  "cpf": "12345678900",
  "gmail": "joao@example.com",
  "senha": "senhaSegura123"
}
\`\`\`
</details>

---

### 🕹️ Jogos (`/jogos`)

| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| `GET` | `/jogos/getjogos` | Lista todos os jogos cadastrados | Público |
| `GET` | `/jogos/getjogobyid/{id}` | Busca os detalhes de um jogo por ID | Público |
| `GET` | `/jogos/getjogobynome/{nome}` | Busca um jogo específico pelo título | Público |
| `POST` | `/jogos/postjogo` | Cadastra um novo jogo no catálogo | Público / Admin |
| `PUT` | `/jogos/updateJogo/{id}` | Atualiza informações de um jogo | Público / Admin |
| `DELETE`| `/jogos/deleteJogo/{id}` | Remove um jogo do catálogo | Público / Admin |

---

### 🛒 Carrinho (`/carrinho`)

| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| `GET` | `/carrinho/{cpf}` | Visualiza itens e subtotal do carrinho do usuário | Público |
| `POST` | `/carrinho/postcarrinho/{cpf}` | Inicializa um carrinho para o usuário | Público |
| `POST` | `/carrinho/{cpf}/adicionar` | Adiciona um item (`idJogo`, `quantidade`) | Público |
| `PUT` | `/carrinho/{cpf}/excluirItem` | Decrementa ou exclui um item | Público |
| `DELETE`| `/carrinho/{cpf}/excluirCarrinho`| Limpa todo o carrinho | Público |

---

### 💳 Pagamentos & Checkout (`/pagamentos`)

| Método | Endpoint | Descrição | Acesso |
|---|---|---|---|
| `POST` | `/pagamentos/pix` | Gera cobrança Pix via Mercado Pago para o CPF | Autenticado |
| `GET` | `/pagamentos/status/{pedidoId}` | Consulta o status atual de um pedido (`PENDENTE`, `PAGO`, etc.) | Autenticado |
| `POST` | `/pagamentos/webhook/pix` | Endpoint receptor de notificações de pagamento do Mercado Pago | Público (Valida Headers) |
| `POST` | `/pagamentos/teste/confirmar/{txId}` | Rota auxiliar para testes de confirmação manual | Autenticado |

---

## 🗄️ Modelo de Dados

O ecossistema relacional do banco de dados é estruturado sobre:

- **`Pessoa`**: Usuários cadastrados com credenciais e CPF único.
- **`Jogo`**: Títulos à venda contendo nome, descrição, gênero, preço e URL de imagem.
- **`Carrinho` & `ItemCarrinho`**: Relação 1:1 com `Pessoa` gerenciando múltiplos itens temporários de compra e cálculo de total.
- **`Pedido` & `ItemPedido`**: Registro permanente da transação após o fechamento do checkout, contendo valor total, status do pagamento e `transactionId` do gateway.

---

## 📄 Licença

Este projeto foi desenvolvido para fins didáticos e comerciais sob a licença [MIT](LICENSE).
