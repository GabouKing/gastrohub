# GastroHub

GastroHub é um sistema de gestão compartilhado para restaurantes, desenvolvido para o **Tech Challenge da Fase 2** da pós-graduação. O sistema permite cadastro de tipos de usuário, usuários, restaurantes e itens de cardápio, seguindo uma arquitetura em camadas e boas práticas de desenvolvimento com Spring Boot.

> Projeto acadêmico — a implementação dos CRUDs, controllers e testes será feita pelos colegas em etapas futuras.

---

## Stack

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.1.0 |
| Spring Data JPA / Hibernate | 7.4 |
| Spring Web MVC | — |
| Lombok | — |
| Maven | 3.9+ |
| H2 Database | dev (embedded) |
| MySQL 8.0 | produção / Docker |
| Docker | 24+ |

---

## Estrutura do projeto

```
src/main/java/com/example/gastrohub/
├── GastrohubApplication.java         # Entry point Spring Boot
├── domain/
│   └── entity/
│       ├── UserType.java             # Tipo de usuário
│       ├── User.java                 # Usuário
│       ├── Restaurant.java           # Restaurante
│       └── MenuItem.java             # Item do cardápio
├── application/                      # (a implementar — casos de uso)
├── infrastructure/                   # (a implementar — persistência)
└── presentation/                     # (a implementar — controllers REST)

src/main/resources/
├── application.properties            # Configuração da aplicação
└── db/
    └── migration/
        └── V1__init.sql              # Script DDL MySQL (referência)

src/test/java/com/example/gastrohub/
└── GastrohubApplicationTests.java    # Smoke test
```

---

## Banco de dados

### Modelo relacional

```
user_types (1) ──── (N) users (1) ──── (N) restaurants (1) ──── (N) menu_items
```

As tabelas são geradas automaticamente pelo Hibernate via `spring.jpa.hibernate.ddl-auto=update`
— **nenhum script manual é necessário** para criar o schema.

### user_types

Armazena os perfis de usuário do sistema (ex.: "Dono de Restaurante", "Cliente").

| Coluna | Tipo | Restrições | Descrição |
|---|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT | Identificador único |
| name | VARCHAR(100) | NOT NULL, UNIQUE | Nome do tipo |
| created_at | DATETIME(6) | NOT NULL | Data de criação |
| updated_at | DATETIME(6) | NOT NULL | Data da última atualização |

### users

Armazena os usuários do sistema, cada um associado a um tipo.

| Coluna | Tipo | Restrições | Descrição |
|---|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT | Identificador único |
| name | VARCHAR(150) | NOT NULL | Nome completo |
| email | VARCHAR(200) | NOT NULL, UNIQUE | E-mail (login) |
| password | VARCHAR(255) | NOT NULL | Hash da senha |
| phone | VARCHAR(20) | — | Telefone de contato |
| user_type_id | BIGINT | FK → user_types(id), NOT NULL | Tipo de usuário |
| created_at | DATETIME(6) | NOT NULL | Data de criação |
| updated_at | DATETIME(6) | NOT NULL | Data da última atualização |

### restaurants

Armazena os restaurantes cadastrados, cada um vinculado a um usuário dono.

| Coluna | Tipo | Restrições | Descrição |
|---|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT | Identificador único |
| name | VARCHAR(200) | NOT NULL | Nome do restaurante |
| address | VARCHAR(500) | NOT NULL | Endereço completo |
| cuisine_type | VARCHAR(100) | NOT NULL | Tipo de cozinha (ex.: "Italiana", "Japonesa") |
| opening_hours | VARCHAR(200) | NOT NULL | Horário de funcionamento |
| owner_id | BIGINT | FK → users(id), NOT NULL | Dono do restaurante |
| created_at | DATETIME(6) | NOT NULL | Data de criação |
| updated_at | DATETIME(6) | NOT NULL | Data da última atualização |

### menu_items

Armazena os itens do cardápio de cada restaurante.

| Coluna | Tipo | Restrições | Descrição |
|---|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT | Identificador único |
| name | VARCHAR(200) | NOT NULL | Nome do item |
| description | TEXT | — | Descrição detalhada |
| price | DECIMAL(10,2) | NOT NULL | Preço em reais |
| available_for_dine_in | BIT(1) | NOT NULL, DEFAULT FALSE | Disponível apenas para consumo no local |
| photo_path | VARCHAR(500) | — | Caminho da foto do prato |
| restaurant_id | BIGINT | FK → restaurants(id), NOT NULL | Restaurante ao qual pertence |
| created_at | DATETIME(6) | NOT NULL | Data de criação |
| updated_at | DATETIME(6) | NOT NULL | Data da última atualização |

### Script de referência

O arquivo [`src/main/resources/db/migration/V1__init.sql`](src/main/resources/db/migration/V1__init.sql) contém o DDL completo para MySQL, incluindo `CREATE TABLE`, foreign keys e índices. Pode ser usado como referência ou executado manualmente se necessário.

---

## Como executar

### Pré-requisitos

- Java 21
- Maven 3.9+
- Docker 24+ e Docker Compose 2.20+ (para execução com MySQL)

### Local (H2 em memória — desenvolvimento)

```bash
./mvnw spring-boot:run
```

A aplicação inicia com H2 em memória na porta `8080`. As tabelas são criadas automaticamente.  
Para acessar o console H2: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:gastrohub`).

### Docker (MySQL 8.0 — produção)

```bash
# Build da imagem
docker compose build

# Subir serviços (MySQL + API)
docker compose up -d

# Acompanhar logs da API
docker compose logs -f gastrohub-api

# Verificar containers
docker compose ps

# Acessar o MySQL
docker exec -it gastrohub-mysql mysql -u gastrohub -pgastrohub123

# Parar e limpar volumes
docker compose down -v
```

### Acessos

| Serviço | URL / Credenciais |
|---|---|
| API | http://localhost:8080 |
| MySQL | localhost:3306, user `gastrohub`, pass `gastrohub123` |
| H2 Console | http://localhost:8080/h2-console (apenas em dev) |

---

## Endpoints previstos (Fase 2)

Os endpoints abaixo serão implementados pelos colegas. As entidades e o banco já estão prontos.

| Recurso | Métodos |
|---|---|
| Tipos de usuário | `GET/POST /roles`, `GET/PUT/DELETE /roles/{id}` |
| Usuários | `GET/POST /users`, `GET/PUT/DELETE /users/{id}` |
| Restaurantes | `GET/POST /restaurants`, `GET/PUT/DELETE /restaurants/{id}` |
| Itens do cardápio | `GET/POST /menu-items`, `GET/PUT/DELETE /menu-items/{id}` |

---

## Configuração

### application.properties

As propriedades de banco são configuradas via variáveis de ambiente com fallback para H2:

```properties
spring.datasource.url=${DB_URL:jdbc:h2:mem:gastrohub}
spring.datasource.username=${DB_USERNAME:sa}
spring.datasource.password=${DB_PASSWORD:}
spring.datasource.driver-class-name=${DB_DRIVER:org.h2.Driver}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=${DB_DIALECT:org.hibernate.dialect.H2Dialect}
```

No Docker, o `docker-compose.yml` injeta as variáveis para conectar ao MySQL.

### settings.xml

O arquivo `.mvn/settings.xml` contém a configuração Maven do projeto com mirrors para Maven Central e Spring Milestones. Ele está ignorado pelo `.gitignore`.

---

## Testes

A ser implementado. Requisitos da Fase 2:

- Testes unitários com cobertura mínima de **80%**
- Testes de integração para validar os componentes essenciais

---

## Próximos passos

- [ ] Implementar repositórios Spring Data JPA (`JpaRepository<>`)
- [ ] Criar DTOs e casos de uso na camada `application`
- [ ] Expor endpoints REST na camada `presentation`
- [ ] Adicionar validações e tratamento de exceções
- [ ] Incluir coleção Postman para testes manuais
- [ ] Implementar suíte de testes com cobertura ≥ 80%
- [ ] Gravar vídeo de apresentação (~5 min)