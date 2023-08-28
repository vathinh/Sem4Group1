-- liquibase formatted sql

-- changeset vanthinh:add-users-table.sql

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       phone VARCHAR(20),
                       keycloak_id VARCHAR(255) UNIQUE,
                       created_by VARCHAR(255),
                       created_date TIMESTAMP,
                       modified_date TIMESTAMP,
                       delete_flag BOOLEAN NOT NULL DEFAULT false,
                       version BIGINT
);

CREATE INDEX idx_email ON users (email);

ALTER TABLE users ADD CONSTRAINT uc_keycloak_id UNIQUE (keycloak_id);
