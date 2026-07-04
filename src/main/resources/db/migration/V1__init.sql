-- =============================================================
-- Migration V1: Schema inicial do GastroHub
-- =============================================================

-- 1. Usuários
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT          AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150)    NOT NULL,
    email       VARCHAR(200)    NOT NULL UNIQUE,
    login       VARCHAR(50)     NOT NULL UNIQUE,
    password    VARCHAR(255)    NOT NULL,
    role        VARCHAR(50)     NOT NULL DEFAULT 'USER_CLIENT'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Restaurantes
CREATE TABLE IF NOT EXISTS restaurants (
    id             BIGINT          AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(200)    NOT NULL,
    address        VARCHAR(500)    NOT NULL,
    cuisine_type   VARCHAR(100)    NOT NULL,
    opening_hours  VARCHAR(200)    NOT NULL,
    owner_id       BIGINT          NOT NULL,
    CONSTRAINT fk_restaurants_owner FOREIGN KEY (owner_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_restaurants_owner_id ON restaurants(owner_id);

-- 3. Itens do Cardápio
CREATE TABLE IF NOT EXISTS menu_items (
    id                          BIGINT          AUTO_INCREMENT PRIMARY KEY,
    name                        VARCHAR(200)    NOT NULL,
    description                 TEXT,
    price                       DECIMAL(10,2)   NOT NULL,
    available_only_on_restaurant BOOLEAN        NOT NULL DEFAULT FALSE,
    photo_path                  VARCHAR(500),
    restaurant_id               BIGINT          NOT NULL,
    CONSTRAINT fk_menu_items_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_menu_items_restaurant_id ON menu_items(restaurant_id);

-- 4. Roles
CREATE TABLE IF NOT EXISTS roles (
    id          BIGINT          AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50)     NOT NULL UNIQUE,
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Seed: roles padrão
INSERT IGNORE INTO roles (name, description) VALUES
    ('USER_ADMIN', 'Administrador do sistema'),
    ('USER_CLIENT', 'Cliente'),
    ('USER_OWNER', 'Dono de restaurante');