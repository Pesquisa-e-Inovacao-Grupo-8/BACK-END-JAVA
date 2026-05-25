INSERT INTO servico (id, nome, duracao_minutos, descricao, preco, ativo) VALUES
('123e4567-e89b-12d3-a456-426614174001', 'Corte Feminino', 60, 'Corte personalizado com acabamento impecável', 120.0, true),
('123e4567-e89b-12d3-a456-426614174002', 'Coloração Completa', 150, 'Coloração profissional premium', 280.0, true),
('123e4567-e89b-12d3-a456-426614174003', 'Limpeza de Pele', 90, 'Tratamento completo com extração', 180.0, true);

INSERT INTO usuario (id, nome, telefone, cpf, senha, email, tipo, ativo, criacao) VALUES
('223e4567-e89b-12d3-a456-426614174001', 'Ana Paula', '5511982722589', '111.111.111-11', 'senha123', 'ana@tukotomi.com', 'PROFISSIONAL', true, CURRENT_TIMESTAMP),
('223e4567-e89b-12d3-a456-426614174002', 'Juliana Costa', '5511982722589', '222.222.222-22', 'senha123', 'juliana@tukotomi.com', 'PROFISSIONAL', true, CURRENT_TIMESTAMP);

INSERT INTO profissional (id, especialidade, descricao, foto, fk_usuario) VALUES
('323e4567-e89b-12d3-a456-426614174001', 'Cabelo e Coloração', 'Especialista em mechas', null, '223e4567-e89b-12d3-a456-426614174001'),
('323e4567-e89b-12d3-a456-426614174002', 'Estética e Massagem', 'Especialista em cuidados faciais', null, '223e4567-e89b-12d3-a456-426614174002');

INSERT INTO usuario (id, nome, telefone, cpf, senha, email, tipo, ativo, criacao) VALUES
('423e4567-e89b-12d3-a456-426614174001', 'Maria Silva', '5511982722589', '333.333.333-33', 'senha123', 'maria@email.com', 'CLIENTE', true, CURRENT_TIMESTAMP),
('423e4567-e89b-12d3-a456-426614174002', 'Maria Silva', '5511982722589', '333.333.333-33', 'senha123', 'maria@email.com', 'CLIENTE', true, CURRENT_TIMESTAMP),
('423e4567-e89b-12d3-a456-426614174003', 'Maria Silva', '5511982722589', '333.333.333-33', 'senha123', 'maria@email.com', 'CLIENTE', true, CURRENT_TIMESTAMP);


INSERT INTO cliente (id, observacoes, fk_usuario) VALUES
('523e4567-e89b-12d3-a456-426614174001', 'Cliente VIP', '423e4567-e89b-12d3-a456-426614174001');

INSERT INTO agendamento (id, data, hora_inicio, hora_fim, status, ordem_pedido, fk_cliente, fk_profissional, valor_total) VALUES
 ('623e4567-e89b-12d3-a456-426614174001', DATEADD('DAY', 1, CURRENT_DATE), '14:00:00', '15:00:00', 'CONFIRMADO', 'ORD-001', '523e4567-e89b-12d3-a456-426614174001', '323e4567-e89b-12d3-a456-426614174001', 150.00);

INSERT INTO agendamento_servico (id, fk_agendamento, fk_servico) VALUES
('723e4567-e89b-12d3-a456-426614174001', '623e4567-e89b-12d3-a456-426614174001', '123e4567-e89b-12d3-a456-426614174001');