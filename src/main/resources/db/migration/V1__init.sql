-- =============================================================
-- Migration V1: Schema inicial do GastroHub
--
-- Este script pode ser executado manualmente no MySQL ou usado
-- como referência. Em desenvolvimento (H2), o Hibernate gera
-- as tabelas automaticamente via spring.jpa.hibernate.ddl-auto=update.
-- =============================================================

-- 1. Tipos de Usuário (Dono de Restaurante, Cliente, etc.)
CREATE TABLE IF NOT EXISTS user_types (
    id          BIGINT          AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)    NOT NULL UNIQUE,
    created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Usuários (associados a um tipo de usuário)
CREATE TABLE IF NOT EXISTS users (
    id           BIGINT          AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(150)    NOT NULL,
    email        VARCHAR(200)    NOT NULL UNIQUE,
    password     VARCHAR(255)    NOT NULL,
    phone        VARCHAR(20),
    user_type_id BIGINT          NOT NULL,
    created_at   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_user_type FOREIGN KEY (user_type_id) REFERENCES user_types(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_users_user_type_id ON users(user_type_id);
CREATE INDEX idx_users_email ON users(email);

-- 3. Restaurantes (cada um com um dono — User do tipo "Dono de Restaurante")
CREATE TABLE IF NOT EXISTS restaurants (
    id             BIGINT          AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(200)    NOT NULL,
    address        VARCHAR(500)    NOT NULL,
    cuisine_type   VARCHAR(100)    NOT NULL,
    opening_hours  VARCHAR(200)    NOT NULL,
    owner_id       BIGINT          NOT NULL,
    created_at     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_restaurants_owner FOREIGN KEY (owner_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_restaurants_owner_id ON restaurants(owner_id);

-- 4. Itens do Cardápio (associados a um restaurante)
CREATE TABLE IF NOT EXISTS menu_items (
    id                   BIGINT          AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(200)    NOT NULL,
    description          TEXT,
    price                DECIMAL(10,2)   NOT NULL,
    available_for_dine_in BOOLEAN        NOT NULL DEFAULT FALSE,
    photo_path           VARCHAR(500),
    restaurant_id        BIGINT          NOT NULL,
    created_at           TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_menu_items_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_menu_items_restaurant_id ON menu_items(restaurant_id);