-- liquibase formatted sql

-- changeset vanthinh:add-users-title-table.sql

CREATE TABLE titles (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255),
                        code VARCHAR(255),
                        created_by VARCHAR(255),
                        created_date TIMESTAMPTZ,
                        modified_date TIMESTAMPTZ,
                        delete_flag BOOLEAN,
                        version BIGINT
);

CREATE INDEX idx_titles_code ON titles (code);
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       title_id BIGINT REFERENCES titles(id),
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       email VARCHAR(255),
                       phone VARCHAR(255),
                       keycloak_id VARCHAR(255) UNIQUE,
                       created_by VARCHAR(255),
                       created_date TIMESTAMPTZ,
                       modified_date TIMESTAMPTZ,
                       delete_flag BOOLEAN,
                       version BIGINT
);

CREATE INDEX idx_users_email ON users (email);
