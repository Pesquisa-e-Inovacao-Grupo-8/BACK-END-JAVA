CREATE DATABASE IF NOT EXISTS tukotomi
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE tukotomi;

SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS;
SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS;

SET NAMES utf8mb4;
SET TIME_ZONE = '+00:00';
SET FOREIGN_KEY_CHECKS = 0;
SET UNIQUE_CHECKS = 0;

-- =========================================================
-- LIMPA O SCHEMA ATUAL
-- =========================================================

DROP TABLE IF EXISTS agendamento_servico;
DROP TABLE IF EXISTS servico_produto;
DROP TABLE IF EXISTS cliente_pacote_servico;
DROP TABLE IF EXISTS pagamento;
DROP TABLE IF EXISTS comprovante;
DROP TABLE IF EXISTS agendamento;
DROP TABLE IF EXISTS profissional_horario;
DROP TABLE IF EXISTS servico_profissional;
DROP TABLE IF EXISTS pacote_servico;
DROP TABLE IF EXISTS cliente_pacote;
DROP TABLE IF EXISTS profissional;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS produto;
DROP TABLE IF EXISTS pacote;
DROP TABLE IF EXISTS servico;
DROP TABLE IF EXISTS usuario;

-- =========================================================
-- TABELA: usuario
-- =========================================================
CREATE TABLE usuario (
    id_usuario BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(255) NOT NULL,
    cpf VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    status ENUM('ATIVO', 'INATIVO') NOT NULL DEFAULT 'ATIVO',
    criacao TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_usuario),
    UNIQUE KEY uk_usuario_cpf (cpf),
    UNIQUE KEY uk_usuario_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: cliente
-- =========================================================
CREATE TABLE cliente (
    id_cliente BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    observacoes VARCHAR(255) NULL,
    fk_usuario BINARY(16) NULL,
    PRIMARY KEY (id_cliente),
    UNIQUE KEY uk_cliente_usuario (fk_usuario),
    CONSTRAINT fk_cliente_usuario
        FOREIGN KEY (fk_usuario) REFERENCES usuario (id_usuario)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: profissional
-- =========================================================
CREATE TABLE profissional (
    id_profissional BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    especialidade VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NULL,
    foto VARCHAR(255) NULL,
    fk_usuario BINARY(16) NULL,
    PRIMARY KEY (id_profissional),
    UNIQUE KEY uk_profissional_usuario (fk_usuario),
    CONSTRAINT fk_profissional_usuario
        FOREIGN KEY (fk_usuario) REFERENCES usuario (id_usuario)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: servico
-- =========================================================
CREATE TABLE servico (
    id_servico BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    nome VARCHAR(255) NOT NULL,
    duracao_minutos INT NOT NULL,
    descricao VARCHAR(255) NULL,
    preco DOUBLE NOT NULL,
    status ENUM('ATIVO', 'INATIVO') NOT NULL DEFAULT 'ATIVO',
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_servico),
    CONSTRAINT chk_servico_duracao CHECK (duracao_minutos > 0),
    CONSTRAINT chk_servico_preco CHECK (preco >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: produto
-- =========================================================
CREATE TABLE produto (
    id_produto BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    nome VARCHAR(100) NOT NULL,
    unidade_medida VARCHAR(20) NULL,
    custo_unitario DOUBLE NOT NULL,
    PRIMARY KEY (id_produto),
    CONSTRAINT chk_produto_custo CHECK (custo_unitario >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: pacote
-- =========================================================
CREATE TABLE pacote (
    id_pacote BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NULL,
    preco_total DOUBLE NOT NULL,
    PRIMARY KEY (id_pacote),
    CONSTRAINT chk_pacote_preco CHECK (preco_total >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: servico_profissional
-- =========================================================
-- Tabela canonica do relacionamento profissional-servico.
-- A tabela profissional_servico antiga nao faz parte deste schema.
CREATE TABLE servico_profissional (
    id_profissional_servico BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    fk_servico BINARY(16) NOT NULL,
    fk_profissional BINARY(16) NOT NULL,
    PRIMARY KEY (id_profissional_servico),
    UNIQUE KEY uk_servico_profissional (fk_servico, fk_profissional),
    KEY idx_sp_profissional (fk_profissional),
    KEY idx_sp_servico (fk_servico),
    CONSTRAINT fk_sp_profissional
        FOREIGN KEY (fk_profissional) REFERENCES profissional (id_profissional)
        ON DELETE CASCADE,
    CONSTRAINT fk_sp_servico
        FOREIGN KEY (fk_servico) REFERENCES servico (id_servico)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: profissional_horario
-- =========================================================
-- Um registro por dia da semana para cada profissional.
-- 1 = segunda-feira ... 7 = domingo.
CREATE TABLE profissional_horario (
    id_horario BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    fk_profissional BINARY(16) NOT NULL,
    dia_semana TINYINT NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    intervalo_minutos INT NOT NULL DEFAULT 0,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_horario),
    UNIQUE KEY uk_profissional_dia (fk_profissional, dia_semana),
    KEY idx_horario_profissional (fk_profissional),
    CONSTRAINT fk_horario_profissional
        FOREIGN KEY (fk_profissional) REFERENCES profissional (id_profissional)
        ON DELETE CASCADE,
    CONSTRAINT chk_horario_dia CHECK (dia_semana BETWEEN 1 AND 7),
    CONSTRAINT chk_horario_intervalo CHECK (intervalo_minutos BETWEEN 0 AND 240),
    CONSTRAINT chk_horario_ordem CHECK (NOT ativo OR hora_fim > hora_inicio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: pacote_servico
-- =========================================================
CREATE TABLE pacote_servico (
    id_pacote_servico BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    fk_pacote BINARY(16) NOT NULL,
    fk_servico BINARY(16) NOT NULL,
    PRIMARY KEY (id_pacote_servico),
    UNIQUE KEY uk_pacote_servico (fk_pacote, fk_servico),
    KEY idx_ps_servico (fk_servico),
    CONSTRAINT fk_ps_pacote
        FOREIGN KEY (fk_pacote) REFERENCES pacote (id_pacote)
        ON DELETE CASCADE,
    CONSTRAINT fk_ps_servico
        FOREIGN KEY (fk_servico) REFERENCES servico (id_servico)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: cliente_pacote
-- =========================================================
CREATE TABLE cliente_pacote (
    id_cliente_pacote BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    fk_cliente BINARY(16) NOT NULL,
    fk_pacote BINARY(16) NOT NULL,
    status ENUM('ATIVO', 'INATIVO') NOT NULL DEFAULT 'ATIVO',
    data_compra TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    expiracao TIMESTAMP NULL DEFAULT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    dt_expiracao DATETIME(6) NOT NULL,
    PRIMARY KEY (id_cliente_pacote),
    KEY idx_cp_cliente (fk_cliente),
    KEY idx_cp_pacote (fk_pacote),
    CONSTRAINT fk_cp_cliente
        FOREIGN KEY (fk_cliente) REFERENCES cliente (id_cliente)
        ON DELETE CASCADE,
    CONSTRAINT fk_cp_pacote
        FOREIGN KEY (fk_pacote) REFERENCES pacote (id_pacote)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: cliente_pacote_servico
-- =========================================================
CREATE TABLE cliente_pacote_servico (
    id_cliente_pacote_servico BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    fk_cliente_pacote BINARY(16) NOT NULL,
    fk_servico BINARY(16) NOT NULL,
    quantidade_disponivel INT NOT NULL,
    PRIMARY KEY (id_cliente_pacote_servico),
    UNIQUE KEY uk_cliente_pacote_servico (fk_cliente_pacote, fk_servico),
    KEY idx_cps_servico (fk_servico),
    CONSTRAINT fk_cps_cliente_pacote
        FOREIGN KEY (fk_cliente_pacote) REFERENCES cliente_pacote (id_cliente_pacote)
        ON DELETE CASCADE,
    CONSTRAINT fk_cps_servico
        FOREIGN KEY (fk_servico) REFERENCES servico (id_servico)
        ON DELETE CASCADE,
    CONSTRAINT chk_cps_quantidade CHECK (quantidade_disponivel >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: agendamento
-- =========================================================
CREATE TABLE agendamento (
    id_agendamento BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    data DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    ordem_pedido VARCHAR(255) NULL,
    fk_cliente BINARY(16) NOT NULL,
    fk_profissional BINARY(16) NOT NULL,
    fk_cliente_pacote BINARY(16) NULL,
    nome_cliente_avulso VARCHAR(255) NULL,
    telefone_cliente_avulso VARCHAR(255) NULL,
    valor_total DOUBLE NOT NULL,
    fk_servico BINARY(16) NULL,
    PRIMARY KEY (id_agendamento),
    KEY idx_ag_cliente (fk_cliente),
    KEY idx_ag_profissional_data (fk_profissional, data),
    KEY idx_ag_cliente_pacote (fk_cliente_pacote),
    KEY idx_ag_servico (fk_servico),
    CONSTRAINT fk_ag_cliente
        FOREIGN KEY (fk_cliente) REFERENCES cliente (id_cliente)
        ON DELETE CASCADE,
    CONSTRAINT fk_ag_profissional
        FOREIGN KEY (fk_profissional) REFERENCES profissional (id_profissional)
        ON DELETE CASCADE,
    CONSTRAINT fk_ag_cliente_pacote
        FOREIGN KEY (fk_cliente_pacote) REFERENCES cliente_pacote (id_cliente_pacote)
        ON DELETE SET NULL,
    CONSTRAINT fk_ag_servico
        FOREIGN KEY (fk_servico) REFERENCES servico (id_servico),
    CONSTRAINT chk_ag_horario CHECK (hora_fim > hora_inicio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: pagamento
-- =========================================================
CREATE TABLE pagamento (
    id_pagamento BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    valor DOUBLE NOT NULL,
    metodo VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    data TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    fk_agendamento BINARY(16) NULL,
    PRIMARY KEY (id_pagamento),
    KEY idx_pagamento_agendamento (fk_agendamento),
    CONSTRAINT fk_pagamento_agendamento
        FOREIGN KEY (fk_agendamento) REFERENCES agendamento (id_agendamento)
        ON DELETE SET NULL,
    CONSTRAINT chk_pagamento_valor CHECK (valor >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: comprovante
-- =========================================================
CREATE TABLE comprovante (
    id_comprovante BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    url VARCHAR(255) NOT NULL,
    fk_pagamento BINARY(16) NULL,
    PRIMARY KEY (id_comprovante),
    UNIQUE KEY uk_comprovante_pagamento (fk_pagamento),
    CONSTRAINT fk_comprovante_pagamento
        FOREIGN KEY (fk_pagamento) REFERENCES pagamento (id_pagamento)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: agendamento_servico
-- =========================================================
CREATE TABLE agendamento_servico (
    id_agendamento_servico BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    fk_agendamento BINARY(16) NOT NULL,
    fk_servico BINARY(16) NOT NULL,
    fk_cliente_pacote_servico BINARY(16) NULL,
    PRIMARY KEY (id_agendamento_servico),
    UNIQUE KEY uk_agendamento_servico (fk_agendamento, fk_servico),
    KEY idx_as_servico (fk_servico),
    KEY idx_as_cliente_pacote_servico (fk_cliente_pacote_servico),
    CONSTRAINT fk_as_agendamento
        FOREIGN KEY (fk_agendamento) REFERENCES agendamento (id_agendamento)
        ON DELETE CASCADE,
    CONSTRAINT fk_as_servico
        FOREIGN KEY (fk_servico) REFERENCES servico (id_servico)
        ON DELETE CASCADE,
    CONSTRAINT fk_as_cliente_pacote_servico
        FOREIGN KEY (fk_cliente_pacote_servico) REFERENCES cliente_pacote_servico (id_cliente_pacote_servico)
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABELA: servico_produto
-- =========================================================
CREATE TABLE servico_produto (
    id_servico_produto BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    fk_servico BINARY(16) NOT NULL,
    fk_produto BINARY(16) NOT NULL,
    quantidade_usada DOUBLE NOT NULL,
    id BINARY(16) NOT NULL DEFAULT (UUID_TO_BIN(UUID())),
    PRIMARY KEY (id_servico_produto),
    UNIQUE KEY uk_servico_produto (fk_servico, fk_produto),
    KEY idx_sprod_produto (fk_produto),
    CONSTRAINT fk_sprod_servico
        FOREIGN KEY (fk_servico) REFERENCES servico (id_servico)
        ON DELETE CASCADE,
    CONSTRAINT fk_sprod_produto
        FOREIGN KEY (fk_produto) REFERENCES produto (id_produto)
        ON DELETE CASCADE,
    CONSTRAINT chk_sprod_quantidade CHECK (quantidade_usada > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- DADOS INICIAIS: SERVICOS
-- =========================================================

INSERT INTO servico (
    id_servico,
    nome,
    duracao_minutos,
    descricao,
    preco,
    status,
    ativo
) VALUES
(
    UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174001'),
    'Corte Feminino',
    60,
    'Corte personalizado com acabamento impecavel',
    120.00,
    'ATIVO',
    TRUE
),
(
    UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174002'),
    'Coloracao Completa',
    150,
    'Coloracao profissional premium',
    280.00,
    'ATIVO',
    TRUE
),
(
    UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174003'),
    'Limpeza de Pele',
    90,
    'Tratamento completo com extracao',
    180.00,
    'ATIVO',
    TRUE
);

-- =========================================================
-- DADOS INICIAIS: USUARIOS PADRAO
-- =========================================================
-- Senha de todos os usuarios abaixo: senha123
-- O valor armazenado e um hash BCrypt.

INSERT INTO usuario (
    id_usuario,
    nome,
    telefone,
    cpf,
    senha,
    email,
    tipo,
    status,
    ativo
) VALUES
(
    UUID_TO_BIN('423e4567-e89b-12d3-a456-426614174001'),
    'Cliente Padrao',
    '(11) 98888-0001',
    '333.333.333-01',
    '$2a$10$6Rz3G.41B0/wQ4mQ.7xR2.m04c7P4vT/35U6rB3pT9o2V1/4.G/1q',
    'cliente@tukotomi.com',
    'CLIENTE',
    'ATIVO',
    TRUE
),
(
    UUID_TO_BIN('223e4567-e89b-12d3-a456-426614174001'),
    'Profissional Padrao',
    '(11) 99999-0001',
    '111.111.111-01',
    '$2a$10$6Rz3G.41B0/wQ4mQ.7xR2.m04c7P4vT/35U6rB3pT9o2V1/4.G/1q',
    'profissional@tukotomi.com',
    'PROFISSIONAL',
    'ATIVO',
    TRUE
),
(
    UUID_TO_BIN('223e4567-e89b-12d3-a456-426614174003'),
    'Administrador',
    '(11) 99999-0000',
    '000.000.000-01',
    '$2a$10$6Rz3G.41B0/wQ4mQ.7xR2.m04c7P4vT/35U6rB3pT9o2V1/4.G/1q',
    'ADMIN',
    'ATIVO',
    TRUE
);

-- =========================================================
-- DADOS INICIAIS: PERFIS RELACIONADOS
-- =========================================================

INSERT INTO cliente (
    id_cliente,
    observacoes,
    fk_usuario
) VALUES (
    UUID_TO_BIN('523e4567-e89b-12d3-a456-426614174001'),
    'Cliente padrao do ambiente',
    UUID_TO_BIN('423e4567-e89b-12d3-a456-426614174001')
);

INSERT INTO profissional (
    id_profissional,
    especialidade,
    descricao,
    foto,
    fk_usuario
) VALUES (
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001'),
    'Cabelo e estetica',
    'Profissional padrao do ambiente',
    NULL,
    UUID_TO_BIN('223e4567-e89b-12d3-a456-426614174001')
);

INSERT INTO servico_profissional (
    id_profissional_servico,
    fk_servico,
    fk_profissional
) VALUES
(
    UUID_TO_BIN('843e4567-e89b-12d3-a456-426614174001'),
    UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174001'),
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001')
),
(
    UUID_TO_BIN('843e4567-e89b-12d3-a456-426614174002'),
    UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174002'),
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001')
),
(
    UUID_TO_BIN('843e4567-e89b-12d3-a456-426614174003'),
    UUID_TO_BIN('123e4567-e89b-12d3-a456-426614174003'),
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001')
);

-- Segunda a sexta: 09:00 - 18:00, intervalo de 15 minutos.
-- Sabado: 09:00 - 13:00, intervalo de 15 minutos.
-- Domingo: inativo.
INSERT INTO profissional_horario (
    id_horario,
    fk_profissional,
    dia_semana,
    hora_inicio,
    hora_fim,
    intervalo_minutos,
    ativo
) VALUES
(
    UUID_TO_BIN('733e4567-e89b-12d3-a456-426614174001'),
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001'),
    1, '09:00:00', '18:00:00', 15, TRUE
),
(
    UUID_TO_BIN('733e4567-e89b-12d3-a456-426614174002'),
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001'),
    2, '09:00:00', '18:00:00', 15, TRUE
),
(
    UUID_TO_BIN('733e4567-e89b-12d3-a456-426614174003'),
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001'),
    3, '09:00:00', '18:00:00', 15, TRUE
),
(
    UUID_TO_BIN('733e4567-e89b-12d3-a456-426614174004'),
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001'),
    4, '09:00:00', '18:00:00', 15, TRUE
),
(
    UUID_TO_BIN('733e4567-e89b-12d3-a456-426614174005'),
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001'),
    5, '09:00:00', '18:00:00', 15, TRUE
),
(
    UUID_TO_BIN('733e4567-e89b-12d3-a456-426614174006'),
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001'),
    6, '09:00:00', '13:00:00', 15, TRUE
),
(
    UUID_TO_BIN('733e4567-e89b-12d3-a456-426614174007'),
    UUID_TO_BIN('323e4567-e89b-12d3-a456-426614174001'),
    7, '00:00:00', '00:00:00', 0, FALSE
);

-- =========================================================
-- RESTAURA CONFIGURACOES
-- =========================================================
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
