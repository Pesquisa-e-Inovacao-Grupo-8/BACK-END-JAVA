-- ==========================================
-- SERVIÇOS
-- ==========================================
INSERT INTO servico (id, nome, duracao_minutos, descricao, preco, ativo) VALUES
                                                                                 ('123e4567-e89b-12d3-a456-426614174001', 'Corte Feminino', 60, 'Corte personalizado com acabamento impecável', 120.0, true),
                                                                                 ('123e4567-e89b-12d3-a456-426614174002', 'Coloração Completa', 150, 'Coloração profissional premium', 280.0, true),
                                                                                 ('123e4567-e89b-12d3-a456-426614174003', 'Limpeza de Pele', 90, 'Tratamento completo com extração', 180.0, true);

-- ==========================================
-- USUÁRIOS (PROFISSIONAIS) - Senha: senha123
-- ==========================================
INSERT INTO usuario (id, nome, telefone, cpf, senha, email, tipo, ativo, criacao) VALUES
                                                                                      ('223e4567-e89b-12d3-a456-426614174001', 'Ana Paula', '(11) 99999-1111', '111.111.111-11', '$2a$10$6Rz3G.41B0/wQ4mQ.7xR2.m04c7P4vT/35U6rB3pT9o2V1/4.G/1q', 'ana@tukotomi.com', 'PROFISSIONAL', true, CURRENT_TIMESTAMP),
                                                                                      ('223e4567-e89b-12d3-a456-426614174002', 'Juliana Costa', '(11) 99999-2222', '222.222.222-22', '$2a$10$6Rz3G.41B0/wQ4mQ.7xR2.m04c7P4vT/35U6rB3pT9o2V1/4.G/1q', 'juliana@tukotomi.com', 'PROFISSIONAL', true, CURRENT_TIMESTAMP),
                                                                                      ('223e4567-e89b-12d3-a456-426614174003', 'Administrador', '(11) 00000-0000', '000.000.000-00', '$2a$10$6Rz3G.41B0/wQ4mQ.7xR2.m04c7P4vT/35U6rB3pT9o2V1/4.G/1q', 'admin@tukotomi.com', 'ADMIN', true, CURRENT_TIMESTAMP);

INSERT INTO profissional (id, especialidade, descricao, foto, fk_usuario) VALUES
                                                                              ('323e4567-e89b-12d3-a456-426614174001', 'Cabelo e Coloração', 'Especialista em mechas', null, '223e4567-e89b-12d3-a456-426614174001'),
                                                                              ('323e4567-e89b-12d3-a456-426614174002', 'Estética e Massagem', 'Especialista em cuidados faciais', null, '223e4567-e89b-12d3-a456-426614174002');

-- ==========================================
-- USUÁRIOS E CLIENTES - Senha: senha123
-- ==========================================
INSERT INTO usuario (id, nome, telefone, cpf, senha, email, tipo, ativo, criacao) VALUES
                                                                                      ('423e4567-e89b-12d3-a456-426614174001', 'Maria Silva', '(11) 98888-8888', '333.333.333-33', '$2a$10$6Rz3G.41B0/wQ4mQ.7xR2.m04c7P4vT/35U6rB3pT9o2V1/4.G/1q', 'maria@email.com', 'CLIENTE', true, CURRENT_TIMESTAMP),
                                                                                      ('423e4567-e89b-12d3-a456-426614174002', 'Beatriz Lima', '(11) 97777-7777', '444.444.444-44', '$2a$10$6Rz3G.41B0/wQ4mQ.7xR2.m04c7P4vT/35U6rB3pT9o2V1/4.G/1q', 'beatriz@email.com', 'CLIENTE', true, CURRENT_TIMESTAMP),
                                                                                      ('423e4567-e89b-12d3-a456-426614174003', 'Carlos Mendes', '(11) 96666-6666', '555.555.555-55', '$2a$10$6Rz3G.41B0/wQ4mQ.7xR2.m04c7P4vT/35U6rB3pT9o2V1/4.G/1q', 'carlos@email.com', 'CLIENTE', true, CURRENT_TIMESTAMP),
                                                                                      ('423e4567-e89b-12d3-a456-426614174004', 'Fernanda Souza', '(11) 95555-5555', '666.666.666-66', '$2a$10$6Rz3G.41B0/wQ4mQ.7xR2.m04c7P4vT/35U6rB3pT9o2V1/4.G/1q', 'fernanda@email.com', 'CLIENTE', true, CURRENT_TIMESTAMP);

INSERT INTO cliente (id, observacoes, fk_usuario) VALUES
                                                      ('523e4567-e89b-12d3-a456-426614174001', 'Cliente VIP', '423e4567-e89b-12d3-a456-426614174001'),
                                                      ('523e4567-e89b-12d3-a456-426614174002', 'Prefere horários da manhã', '423e4567-e89b-12d3-a456-426614174002'),
                                                      ('523e4567-e89b-12d3-a456-426614174003', 'Alérgico a amônia', '423e4567-e89b-12d3-a456-426614174003'),
                                                      ('523e4567-e89b-12d3-a456-426614174004', 'Sempre atrasa 10 min', '423e4567-e89b-12d3-a456-426614174004');

-- ==========================================
-- AGENDAMENTOS (HISTÓRICO DE 6 MESES PARA O GRÁFICO)
-- ==========================================
-- Mês Atual (Hoje)
INSERT INTO agendamento (id, data, hora_inicio, hora_fim, status, ordem_pedido, fk_cliente, fk_profissional, valor_total) VALUES
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174001', CURRENT_DATE, '09:00:00', '10:00:00', 'CONFIRMADO', 'ORD-001', '523e4567-e89b-12d3-a456-426614174001', '323e4567-e89b-12d3-a456-426614174001', 120.00),
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174002', CURRENT_DATE, '14:00:00', '15:30:00', 'PENDENTE', 'ORD-002', '523e4567-e89b-12d3-a456-426614174002', '323e4567-e89b-12d3-a456-426614174002', 180.00),
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174003', CURRENT_DATE, '16:00:00', '18:30:00', 'PAGO', 'ORD-003', '523e4567-e89b-12d3-a456-426614174003', '323e4567-e89b-12d3-a456-426614174001', 280.00);

-- Mês Atual (Ontem)
INSERT INTO agendamento (id, data, hora_inicio, hora_fim, status, ordem_pedido, fk_cliente, fk_profissional, valor_total) VALUES
    ('623e4567-e89b-12d3-a456-426614174004', DATEADD(DAY, -1, CURRENT_DATE), '10:00:00', '11:00:00', 'PAGO', 'ORD-004', '523e4567-e89b-12d3-a456-426614174004', '323e4567-e89b-12d3-a456-426614174001', 120.00);

-- Mês -1 (Abril)
INSERT INTO agendamento (id, data, hora_inicio, hora_fim, status, ordem_pedido, fk_cliente, fk_profissional, valor_total) VALUES
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174005', '2026-04-10', '11:00:00', '12:00:00', 'PAGO', 'ORD-005', '523e4567-e89b-12d3-a456-426614174001', '323e4567-e89b-12d3-a456-426614174001', 120.00),
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174006', '2026-04-15', '14:00:00', '16:30:00', 'PAGO', 'ORD-006', '523e4567-e89b-12d3-a456-426614174002', '323e4567-e89b-12d3-a456-426614174001', 280.00),
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174007', '2026-04-20', '09:00:00', '10:30:00', 'PAGO', 'ORD-007', '523e4567-e89b-12d3-a456-426614174003', '323e4567-e89b-12d3-a456-426614174002', 180.00);

-- Mês -2 (Março)
INSERT INTO agendamento (id, data, hora_inicio, hora_fim, status, ordem_pedido, fk_cliente, fk_profissional, valor_total) VALUES
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174008', '2026-03-05', '15:00:00', '17:30:00', 'PAGO', 'ORD-008', '523e4567-e89b-12d3-a456-426614174004', '323e4567-e89b-12d3-a456-426614174001', 280.00),
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174009', '2026-03-12', '10:00:00', '11:00:00', 'PAGO', 'ORD-009', '523e4567-e89b-12d3-a456-426614174001', '323e4567-e89b-12d3-a456-426614174001', 120.00);

-- Mês -3 (Fevereiro)
INSERT INTO agendamento (id, data, hora_inicio, hora_fim, status, ordem_pedido, fk_cliente, fk_profissional, valor_total) VALUES
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174010', '2026-02-14', '09:00:00', '10:30:00', 'PAGO', 'ORD-010', '523e4567-e89b-12d3-a456-426614174002', '323e4567-e89b-12d3-a456-426614174002', 180.00),
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174011', '2026-02-25', '13:00:00', '14:00:00', 'PAGO', 'ORD-011', '523e4567-e89b-12d3-a456-426614174003', '323e4567-e89b-12d3-a456-426614174001', 120.00);

-- Mês -4 (Janeiro)
INSERT INTO agendamento (id, data, hora_inicio, hora_fim, status, ordem_pedido, fk_cliente, fk_profissional, valor_total) VALUES
    ('623e4567-e89b-12d3-a456-426614174012', '2026-01-10', '16:00:00', '18:30:00', 'PAGO', 'ORD-012', '523e4567-e89b-12d3-a456-426614174004', '323e4567-e89b-12d3-a456-426614174001', 280.00);

-- Mês -5 (Dezembro)
INSERT INTO agendamento (id, data, hora_inicio, hora_fim, status, ordem_pedido, fk_cliente, fk_profissional, valor_total) VALUES
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174013', '2025-12-20', '10:00:00', '11:00:00', 'CANCELADO', 'ORD-013', '523e4567-e89b-12d3-a456-426614174001', '323e4567-e89b-12d3-a456-426614174001', 120.00),
                                                                                                                              ('623e4567-e89b-12d3-a456-426614174014', '2025-12-22', '14:00:00', '15:30:00', 'PAGO', 'ORD-014', '523e4567-e89b-12d3-a456-426614174002', '323e4567-e89b-12d3-a456-426614174002', 180.00);

-- ==========================================
-- PRODUTOS E VÍNCULOS
-- ==========================================
INSERT INTO produto (id, nome, unidade_medida, custo_unitario) VALUES
                                                                   ('11111111-1111-1111-1111-111111111111', 'Shampoo Premium Tukotomi', 'ml', 0.25),
                                                                   ('22222222-2222-2222-2222-222222222222', 'Máscara de Hidratação Ouro', 'g', 0.83),
                                                                   ('33333333-3333-3333-3333-333333333333', 'Protetor Térmico', 'ml', 0.42),
                                                                   ('44444444-4444-4444-4444-444444444444', 'Tinta Premium Importada', 'tubo', 120.00),
                                                                   ('55555555-5555-5555-5555-555555555555', 'Água Oxigenada', 'ml', 0.16);

-- Se quiser vincular os produtos, use a rota da API via Postman como combinamos!