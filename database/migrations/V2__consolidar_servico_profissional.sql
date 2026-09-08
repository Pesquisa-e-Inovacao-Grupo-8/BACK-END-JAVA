-- V2 - Consolida os vinculos profissional-servico e cria a agenda semanal.
-- Execute depois do script base do banco e faca backup antes de aplicar.

USE tukotomi;

-- =========================================================
-- VINCULO CANONICO: servico_profissional
-- =========================================================

CREATE TABLE IF NOT EXISTS servico_profissional (
    id_profissional_servico BINARY(16) NOT NULL,
    fk_servico BINARY(16) NOT NULL,
    fk_profissional BINARY(16) NOT NULL,
    PRIMARY KEY (id_profissional_servico),
    UNIQUE KEY unique_servico_profissional (fk_servico, fk_profissional),
    KEY fk_sp_profissional (fk_profissional),
    KEY fk_sp_servico (fk_servico),
    CONSTRAINT fk_sp_profissional
        FOREIGN KEY (fk_profissional)
        REFERENCES profissional (id_profissional)
        ON DELETE CASCADE,
    CONSTRAINT fk_sp_servico
        FOREIGN KEY (fk_servico)
        REFERENCES servico (id_servico)
        ON DELETE CASCADE
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

-- Copia os dados da tabela ManyToMany antiga sem duplicar vinculos.
-- Esta etapa pressupoe que profissional_servico existe no banco base.
INSERT IGNORE INTO servico_profissional (
    id_profissional_servico,
    fk_profissional,
    fk_servico
)
SELECT
    UUID_TO_BIN(UUID()),
    legado.profissional_id,
    legado.servico_id
FROM profissional_servico AS legado
INNER JOIN profissional AS profissional
    ON profissional.id_profissional = legado.profissional_id
INNER JOIN servico AS servico
    ON servico.id_servico = legado.servico_id;

-- A partir desta migracao, somente servico_profissional e usado pela aplicacao.
DROP TABLE IF EXISTS profissional_servico;

-- =========================================================
-- HORARIOS SEMANAIS DO PROFISSIONAL
-- =========================================================

CREATE TABLE IF NOT EXISTS profissional_horario (
    id_horario BINARY(16) NOT NULL,
    fk_profissional BINARY(16) NOT NULL,
    dia_semana TINYINT NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    intervalo_minutos INT NOT NULL DEFAULT 0,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_horario),
    UNIQUE KEY uk_profissional_dia (fk_profissional, dia_semana),
    KEY fk_horario_profissional (fk_profissional),
    CONSTRAINT fk_horario_profissional
        FOREIGN KEY (fk_profissional)
        REFERENCES profissional (id_profissional)
        ON DELETE CASCADE,
    CONSTRAINT chk_horario_dia
        CHECK (dia_semana BETWEEN 1 AND 7),
    CONSTRAINT chk_horario_intervalo
        CHECK (intervalo_minutos BETWEEN 0 AND 240),
    CONSTRAINT chk_horario_ordem
        CHECK (NOT ativo OR hora_fim > hora_inicio)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

-- Os horarios devem ser configurados pela aplicacao para cada profissional.
