## 📋 Migração de PostgreSQL para MySQL

### ✅ Alterações Realizadas

#### 1. **pom.xml** - Dependências Atualizadas
- ✅ Removido driver PostgreSQL
- ✅ Adicionado driver MySQL Connector/J 8.x
- ✅ Mantidas todas as outras dependências (JPA, JWT, Swagger, Lombok, etc)

#### 2. **application.properties** - Configuração do Banco
- ✅ URL de conexão: `jdbc:mysql://localhost:3306/tukotomi`
- ✅ Driver: `com.mysql.cj.jdbc.Driver`
- ✅ Dialeto Hibernate: `MySQL8Dialect`
- ✅ Parâmetros MySQL: `useSSL=false&serverTimezone=UTC`

#### 3. **schema.sql** - Script de Criação das Tabelas
- ✅ Todas as 16 tabelas criadas com sintaxe MySQL
- ✅ Constraints e check constraints compatíveis
- ✅ Charset UTF-8 com suporte a emojis
- ✅ Engine InnoDB para transações
- ✅ Foreign keys com ON DELETE CASCADE/SET NULL

---

### 🔧 Próximos Passos - IMPORTANTE

#### 1. **Criar Banco de Dados MySQL**
```sql
CREATE DATABASE tukotomi CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 2. **Instalar Dependências Maven**
```bash
mvn clean install
```

#### 3. **Configurar Credenciais (se necessário)**
Edite `src/main/resources/application.properties`:
- `spring.datasource.username=` seu usuário MySQL
- `spring.datasource.password=` sua senha MySQL

#### 4. **Executar a Aplicação**
O Hibernate criará as tabelas automaticamente (ddl-auto=update)
```bash
mvn spring-boot:run
```

---

### 📊 Tabelas Criadas

| Tabela | Descrição |
|--------|-----------|
| usuario | Dados de usuários (Cliente, Profissional, Admin) |
| cliente | Informações adicionais de clientes |
| profissional | Especialidades de profissionais |
| servico | Serviços oferecidos |
| pacote | Pacotes de serviços |
| pacote_servico | Relação entre pacotes e serviços |
| cliente_pacote | Compras de pacotes por clientes |
| cliente_pacote_servico | Serviços disponíveis por pacote |
| servico_profissional | Relação entre serviços e profissionais |
| agendamento | Agendamentos de serviços |
| agendamento_servico | Serviços em cada agendamento |
| pagamento | Pagamentos realizados |
| comprovante | Comprovantes de pagamento |
| produto | Produtos utilizados nos serviços |
| servico_produto | Produtos necessários por serviço |

---

### 🔐 Configurações de Segurança

✅ **Credenciais Padrão** (ajuste no application.properties):
- Username: `root`
- Password: (vazio - configure conforme sua instalação)
- Host: `localhost:3306`

⚠️ **Para Produção**:
- Use variáveis de ambiente
- Nunca comita senhas no código
- Configure SSL/TLS

---

### 📝 Comandos Úteis

**Conectar ao MySQL:**
```bash
mysql -u root -p tukotomi
```

**Ver estrutura de uma tabela:**
```sql
DESC usuario;
```

**Visualizar criação de tabela:**
```sql
SHOW CREATE TABLE usuario;
```

**Limpar dados (cuidado!):**
```sql
DELETE FROM usuario;
```

---

### 🚀 Checklist de Implementação

- [ ] Banco de dados MySQL criado
- [ ] Credenciais do MySQL configuradas
- [ ] `mvn clean install` executado com sucesso
- [ ] Aplicação iniciada sem erros
- [ ] Tabelas criadas corretamente no MySQL
- [ ] Dados podem ser inseridos via API
- [ ] Relações de chave estrangeira funcionam
