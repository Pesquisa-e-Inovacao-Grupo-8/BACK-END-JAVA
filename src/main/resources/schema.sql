-- =========================
-- USUARIO
-- =========================
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario BINARY(16) PRIMARY KEY,
    nome VARCHAR(115) NOT NULL,
    telefone VARCHAR(20),
    cpf VARCHAR(12) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    tipo VARCHAR(45),
    status ENUM('ATIVO', 'INATIVO') DEFAULT 'ATIVO',
    criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- CLIENTE
-- =========================
CREATE TABLE IF NOT EXISTS cliente (
    id_cliente BINARY(16) PRIMARY KEY,
    observacoes TEXT,
    fk_usuario BINARY(16) UNIQUE,
    CONSTRAINT fk_cliente_usuario
        FOREIGN KEY (fk_usuario)
        REFERENCES usuario(id_usuario)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- PROFISSIONAL
-- =========================
CREATE TABLE IF NOT EXISTS profissional (
    id_profissional BINARY(16) PRIMARY KEY,
    especialidade VARCHAR(45),
    descricao VARCHAR(255),
    foto VARCHAR(225),
    fk_usuario BINARY(16) UNIQUE,
    CONSTRAINT fk_profissional_usuario
        FOREIGN KEY (fk_usuario)
        REFERENCES usuario(id_usuario)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- SERVICO
-- =========================
CREATE TABLE IF NOT EXISTS servico (
    id_servico BINARY(16) PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    duracao_minutos INT NOT NULL CHECK (duracao_minutos > 0),
    descricao VARCHAR(255),
    preco DECIMAL(10,2) NOT NULL CHECK (preco >= 0),
    status ENUM('ATIVO', 'INATIVO') DEFAULT 'ATIVO'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- PACOTE
-- =========================
CREATE TABLE IF NOT EXISTS pacote (
    id_pacote BINARY(16) PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    descricao VARCHAR(255),
    preco_total DECIMAL(10,2) CHECK (preco_total >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- PACOTE_SERVICO
-- =========================
CREATE TABLE IF NOT EXISTS pacote_servico (
    id_pacote_servico BINARY(16) PRIMARY KEY,
    fk_pacote BINARY(16) NOT NULL,
    fk_servico BINARY(16) NOT NULL,
    quantidade INT NOT NULL CHECK (quantidade > 0),
    CONSTRAINT fk_ps_pacote
        FOREIGN KEY (fk_pacote)
        REFERENCES pacote(id_pacote)
        ON DELETE CASCADE,
    CONSTRAINT fk_ps_servico
        FOREIGN KEY (fk_servico)
        REFERENCES servico(id_servico)
        ON DELETE CASCADE,
    CONSTRAINT unique_pacote_servico
        UNIQUE (fk_pacote, fk_servico)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- CLIENTE_PACOTE
-- =========================
CREATE TABLE IF NOT EXISTS cliente_pacote (
    id_cliente_pacote BINARY(16) PRIMARY KEY,
    fk_cliente BINARY(16) NOT NULL,
    fk_pacote BINARY(16) NOT NULL,
    status ENUM('ATIVO', 'INATIVO') DEFAULT 'ATIVO',
    data_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiracao TIMESTAMP NULL,
    CONSTRAINT fk_cp_cliente
        FOREIGN KEY (fk_cliente)
        REFERENCES cliente(id_cliente)
        ON DELETE CASCADE,
    CONSTRAINT fk_cp_pacote
        FOREIGN KEY (fk_pacote)
        REFERENCES pacote(id_pacote)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- CLIENTE_PACOTE_SERVICO
-- =========================
CREATE TABLE IF NOT EXISTS cliente_pacote_servico (
    id_cliente_pacote_servico BINARY(16) PRIMARY KEY,
    fk_cliente_pacote BINARY(16) NOT NULL,
    fk_servico BINARY(16) NOT NULL,
    quantidade_disponivel INT NOT NULL
        CHECK (quantidade_disponivel >= 0),
    CONSTRAINT fk_cps_cliente_pacote
        FOREIGN KEY (fk_cliente_pacote)
        REFERENCES cliente_pacote(id_cliente_pacote)
        ON DELETE CASCADE,
    CONSTRAINT fk_cps_servico
        FOREIGN KEY (fk_servico)
        REFERENCES servico(id_servico)
        ON DELETE CASCADE,
    CONSTRAINT unique_cliente_pacote_servico
        UNIQUE (fk_cliente_pacote, fk_servico)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- SERVICO_PROFISSIONAL
-- =========================
CREATE TABLE IF NOT EXISTS servico_profissional (
    id_profissional_servico BINARY(16) PRIMARY KEY,
    fk_servico BINARY(16) NOT NULL,
    fk_profissional BINARY(16) NOT NULL,
    CONSTRAINT fk_sp_servico
        FOREIGN KEY (fk_servico)
        REFERENCES servico(id_servico)
        ON DELETE CASCADE,
    CONSTRAINT fk_sp_profissional
        FOREIGN KEY (fk_profissional)
        REFERENCES profissional(id_profissional)
        ON DELETE CASCADE,
    CONSTRAINT unique_servico_profissional 
        UNIQUE (fk_servico, fk_profissional)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- AGENDAMENTO
-- =========================
CREATE TABLE IF NOT EXISTS agendamento (
    id_agendamento BINARY(16) PRIMARY KEY,
    data DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    status ENUM('PENDENTE', 'CONFIRMADO', 'CANCELADO', 'CONCLUIDO') NOT NULL,
    ordem_pedido VARCHAR(255),
    fk_cliente BINARY(16) NOT NULL,
    fk_profissional BINARY(16) NOT NULL,
    fk_cliente_pacote BINARY(16),
    CONSTRAINT fk_ag_cliente
        FOREIGN KEY (fk_cliente)
        REFERENCES cliente(id_cliente)
        ON DELETE CASCADE,
    CONSTRAINT fk_ag_profissional
        FOREIGN KEY (fk_profissional)
        REFERENCES profissional(id_profissional)
        ON DELETE CASCADE,
    CONSTRAINT chk_horario_valido 
        CHECK (hora_fim > hora_inicio),
    CONSTRAINT fk_ag_cliente_pacote
        FOREIGN KEY (fk_cliente_pacote)
        REFERENCES cliente_pacote(id_cliente_pacote)
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- AGENDAMENTO_SERVICO
-- =========================
CREATE TABLE IF NOT EXISTS agendamento_servico (
    id_agendamento_servico BINARY(16) PRIMARY KEY,
    fk_agendamento BINARY(16) NOT NULL,
    fk_servico BINARY(16) NOT NULL,
    fk_cliente_pacote_servico BINARY(16),
    CONSTRAINT fk_as_agendamento
        FOREIGN KEY (fk_agendamento)
        REFERENCES agendamento(id_agendamento)
        ON DELETE CASCADE,
    CONSTRAINT fk_as_servico
        FOREIGN KEY (fk_servico)
        REFERENCES servico(id_servico)
        ON DELETE CASCADE,
    CONSTRAINT unique_agendamento_servico 
        UNIQUE (fk_agendamento, fk_servico),
    CONSTRAINT fk_as_cliente_pacote_servico
        FOREIGN KEY (fk_cliente_pacote_servico)
        REFERENCES cliente_pacote_servico(id_cliente_pacote_servico)
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- PAGAMENTO
-- =========================
CREATE TABLE IF NOT EXISTS pagamento (
    id_pagamento BINARY(16) PRIMARY KEY,
    valor DECIMAL(10,2) NOT NULL CHECK (valor >= 0),
    metodo ENUM('PIX', 'CARTAO', 'DINHEIRO', 'BOLETO'),
    status ENUM('PENDENTE', 'PAGO', 'ESTORNADO', 'CANCELADO'),
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fk_agendamento BINARY(16),
    CONSTRAINT fk_pagamento_agendamento
        FOREIGN KEY (fk_agendamento)
        REFERENCES agendamento(id_agendamento)
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- COMPROVANTE
-- =========================
CREATE TABLE IF NOT EXISTS comprovante (
    id_comprovante BINARY(16) PRIMARY KEY,
    url TEXT NOT NULL,
    fk_pagamento BINARY(16) UNIQUE,
    CONSTRAINT fk_comprovante_pagamento
        FOREIGN KEY (fk_pagamento)
        REFERENCES pagamento(id_pagamento)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- PRODUTO
-- =========================
CREATE TABLE IF NOT EXISTS produto (
    id_produto BINARY(16) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    unidade_medida VARCHAR(20),
    custo_unitario DECIMAL(10,2) NOT NULL CHECK (custo_unitario >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================
-- SERVICO_PRODUTO
-- =========================
CREATE TABLE IF NOT EXISTS servico_produto (
    id_servico_produto BINARY(16) PRIMARY KEY,
    fk_servico BINARY(16) NOT NULL,
    fk_produto BINARY(16) NOT NULL,
    quantidade_usada DECIMAL(10,2) NOT NULL CHECK (quantidade_usada > 0),
    CONSTRAINT fk_sprod_servico 
        FOREIGN KEY (fk_servico) 
        REFERENCES servico(id_servico) 
        ON DELETE CASCADE,
    CONSTRAINT fk_sprod_produto 
        FOREIGN KEY (fk_produto) 
        REFERENCES produto(id_produto) 
        ON DELETE CASCADE,
    CONSTRAINT unique_servico_produto 
        UNIQUE (fk_servico, fk_produto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
