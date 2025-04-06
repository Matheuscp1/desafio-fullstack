CREATE TABLE IF NOT EXISTS users
(
    id    BIGSERIAL PRIMARY KEY NOT NULL,
    name  VARCHAR(80)                             NOT NULL,
    email VARCHAR(80)                             NOT NULL,
    password VARCHAR(80)                             NOT NULL,
    role  VARCHAR(255)                            NOT NULL
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- Inserir usuario
INSERT INTO users (name,email, password,role) VALUES
('admin','contato@simplesdental.com',  '$2a$10$WEzj2Oex3WtO1uKQP9eAsOF4o.Lyi7HoSaUuxwLn5yIVaii44ZTsS','admin')
