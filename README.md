# GastroHub

GastroHub é a base de um sistema de gestão compartilhado para restaurantes, desenvolvido para o Tech Challenge da fase 2. O objetivo do projeto é permitir o cadastro e a administração de tipos de usuário, restaurantes e itens de cardápio, seguindo uma organização em camadas e boas práticas de desenvolvimento com Spring Boot.

## Visão geral

Nesta fase, o sistema atende aos requisitos de:

- cadastro e associação de tipos de usuário;
- cadastro completo de restaurantes;
- cadastro de itens do cardápio;
- documentação da API;
- uso de Docker Compose para execução integrada;
- testes automatizados;
- organização em camadas inspirada em Clean Architecture.

O projeto está estruturado para evoluir de forma incremental, permitindo a implementação das funcionalidades exigidas pela fase sem comprometer a manutenibilidade.

## Requisitos da fase 2

Conforme o enunciado do Tech Challenge, o sistema deve contemplar:

- `Tipo de usuário`: cadastro de perfis como `Dono de Restaurante` e `Cliente`, com associação aos usuários existentes;
- `Restaurante`: cadastro com nome, endereço, tipo de cozinha, horário de funcionamento e dono associado;
- `Item de cardápio`: cadastro com nome, descrição, preço, disponibilidade para consumo apenas no local e caminho da foto;
- documentação técnica do projeto;
- coleção de testes para Postman ou ferramenta similar;
- `docker-compose.yml` para subir aplicação e banco;
- testes unitários e de integração.

## Stack utilizada

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Estrutura do projeto

O projeto segue uma divisão por camadas:

- `domain`: entidades, exceções e contratos de repositório;
- `application`: casos de uso, DTOs e mapeadores;
- `infra`: configurações, persistência e integrações técnicas;
- `presentation`: controladores da API.

Essa separação facilita a evolução do código, reduz acoplamento e mantém as regras de negócio isoladas da infraestrutura.

## Estado atual

O repositório já possui a base do projeto Spring Boot e a estrutura de pacotes preparada para receber a implementação das regras de negócio. Neste momento, a aplicação está no ponto inicial do domínio, então os endpoints, repositórios concretos, persistência e coleções de teste devem ser adicionados conforme a evolução da fase.

## Como executar

### Pré-requisitos

- Java 21
- Maven 3.9+

### Execução local

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
mvnw.cmd spring-boot:run
```

### Build

```bash
./mvnw clean package
```

## Configuração

A configuração principal da aplicação fica em `src/main/resources/application.properties`.

Como a base ainda está inicial, a recomendação é evoluir esse arquivo com:

- configuração do banco de dados;
- perfis de ambiente;
- parâmetros de porta;
- logs e propriedades específicas da API.

## Endpoints previstos

Os endpoints abaixo representam a cobertura esperada para a fase 2:

- `Tipo de usuário`
  - `GET /roles`
  - `GET /roles/{id}`
  - `POST /roles`
  - `PUT /roles/{id}`
  - `DELETE /roles/{id}`

- `Usuários`
  - `GET /users`
  - `GET /users/{id}`
  - `POST /users`
  - `PUT /users/{id}`
  - `DELETE /users/{id}`

- `Restaurantes`
  - `GET /restaurants`
  - `GET /restaurants/{id}`
  - `POST /restaurants`
  - `PUT /restaurants/{id}`
  - `DELETE /restaurants/{id}`

- `Itens do cardápio`
  - `GET /menu-items`
  - `GET /menu-items/{id}`
  - `POST /menu-items`
  - `PUT /menu-items/{id}`
  - `DELETE /menu-items/{id}`

## Docker

O enunciado exige um `docker-compose.yml` para subir aplicação e banco de dados. O projeto inclui:

- `Dockerfile` — build multi-estágio (Maven → JRE Alpine)
- `docker-compose.yml` — orquestração da API + MySQL Alpine
- `.dockerignore` — otimização do contexto de build

### Pré-requisitos

- Docker Engine 24+
- Docker Compose 2.20+

### Execução com Docker

```bash
# Build da imagem da aplicação
docker compose build

# Subir todos os serviços
docker compose up -d

# Acompanhar logs
docker compose logs -f gastrohub-api

# Verificar containers em execução
docker compose ps

# Parar e remover tudo (inclusive volumes)
docker compose down -v
```

### Acessos

| Serviço | URL |
|---|---|
| API | http://localhost:8080 |
| MySQL | localhost:3306, user `gastrohub`, pass `gastrohub123` |

### Estrutura do docker-compose

- `mysql-db`: MySQL 8.0 com volume persistente (`mysql_data`) e healthcheck
- `gastrohub-app`: Aplicação Spring Boot, depende do MySQL estar saudável
- Rede interna `gastrohub-net` isolando os serviços

## Testes

A fase pede:

- testes unitários com cobertura mínima de 80%;
- testes de integração para validar os componentes essenciais.

Recomenda-se organizar os testes por camada, mantendo foco em casos de uso e regras de negócio.

## Coleção de testes

Para facilitar a validação manual da API, o projeto deve incluir uma coleção no formato Postman Collection ou similar, contendo os principais cenários de criação, leitura, atualização e remoção.

## Próximos passos sugeridos

- implementar as entidades e contratos do domínio;
- criar os casos de uso da aplicação;
- expor os endpoints REST na camada de apresentação;
- configurar persistência e banco;
- adicionar `docker-compose.yml`;
- incluir a coleção Postman;
- completar a suíte de testes e medir cobertura.

## Licença

Projeto acadêmico desenvolvido para a fase 2 do Tech Challenge.
