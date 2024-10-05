INSERT INTO categoria (id_categoria, nome) VALUES (1, 'Salões de Beleza');
INSERT INTO categoria (id_categoria, nome) VALUES (2, 'Barbearias');
INSERT INTO categoria (id_categoria, nome) VALUES (3, 'Manicure & Nail Designer');
INSERT INTO categoria (id_categoria, nome) VALUES (4, 'Esteticistas');
INSERT INTO categoria (id_categoria, nome) VALUES (5, 'Sobrancelhas & Cílios');

INSERT INTO funcao (id_funcao, nome_funcao) VALUES (1, 'Cabelereiro');
INSERT INTO funcao (id_funcao, nome_funcao) VALUES (2, 'Barbeiro');
INSERT INTO funcao (id_funcao, nome_funcao) VALUES (3, 'Manicure');
INSERT INTO funcao (id_funcao, nome_funcao) VALUES (4, 'Esteticista');
INSERT INTO funcao (id_funcao, nome_funcao) VALUES (5, 'Recepcionista');

INSERT INTO endereco (id_endereco, bairro, cep, cidade, complemento, estado, LOGRADOURO, numero)
VALUES (1, 'Bairro Exemplo', '12345-678', 'Cidade Exemplo', 'Apto 456', 'Estado Exemplo', 'Rua Exemplo', '123');

INSERT INTO empresa (fk_endereco, fk_categoria, id_empresa, id_imagem, cnpj, email_empresa, nome_empresa, telefone)
VALUES (1, 2, 1, NULL, '50438712000165', 'contato1@minhanovaempresa.com', 'Minha Nova Empresa', '+5511999999999');

INSERT INTO servico (fk_empresa, id_servico, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 1, 50.00, 'Corte de Cabelo', 'Corte de cabelo masculino e feminino', '#0000ff', '01:30:00');

INSERT INTO servico (fk_empresa, id_servico, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 2, 30.00, 'Barba', 'Barba feita com navalha', '#000000', '01:20:00');

INSERT INTO servico (fk_empresa, id_servico, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 3, 20.00, 'Bigode', 'Bigode feito com navalha', '#000000', '01:00:00');

INSERT INTO usuarios (data_nascimento, fk_empresa, fk_endereco, id_pessoa, representante, tipos_de_usuario, cpf, email_pessoa, firebase_uid, fk_funcao, genero, nome_pessoa, numero_telefone, senha)
VALUES (NULL, 1, 1, 1, TRUE, 0, 12345678905, 'joao.silva@example.com', '', 1, NULL, 'João da Silva', NULL, '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.');

INSERT INTO usuarios (data_nascimento, fk_empresa, fk_endereco, id_pessoa, representante, tipos_de_usuario, cpf, email_pessoa, firebase_uid, fk_funcao, genero, nome_pessoa, numero_telefone, senha)
VALUES (NULL, 1, 1, 2, FALSE, 1, '12345678901', 'maria.silva@example.com', '', 2, 'F', 'Maria da Silva', NULL, '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.');

INSERT INTO usuarios (data_nascimento, fk_empresa, fk_endereco, id_pessoa, representante, tipos_de_usuario, cpf, email_pessoa, firebase_uid, fk_funcao, genero, nome_pessoa, numero_telefone, senha)
VALUES (NULL, 1, 1, 3, FALSE, 1, '12345678902', 'jose.silva@example.com', '', 1, 'M', 'José da Silva', NULL, '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.');

INSERT INTO cliente (id_pessoa, fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, 1, '1990-01-01', 'felipe@gmail.com', 'M', 'Felipe', '11999999999');

INSERT INTO cliente (id_pessoa, fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (2, 1, '1990-01-01', 'jose@gmail.com', 'M', 'José', '11999999999');

INSERT INTO agenda(id_agenda, fk_usuario)
VALUES (10, 1);

INSERT INTO agenda(id_agenda, fk_usuario)
VALUES (11, 2);

INSERT INTO agenda(id_agenda, fk_usuario)
VALUES (12, 3);

INSERT INTO agendamento (id_agendamento, data_hora, fk_cliente, fk_agenda, fk_servico)
VALUES (1, '2024-10-01 10:00:00', 1, 10, 1);

INSERT INTO agendamento (id_agendamento, data_hora, fk_cliente, fk_agenda, fk_servico)
VALUES (2, '2024-10-01 10:30:00', 2, 10, 2);

INSERT INTO agendamento (id_agendamento, data_hora, fk_cliente, fk_agenda, fk_servico)
VALUES (3, '2024-10-01 11:00:00', 1, 10, 3);

INSERT INTO agendamento (id_agendamento, data_hora, fk_cliente, fk_agenda, fk_servico)
VALUES (4, '2024-10-01 13:00:00', 1, 11, 1);

INSERT INTO agendamento (id_agendamento, data_hora, fk_cliente, fk_agenda, fk_servico)
VALUES (5, '2024-10-01 14:30:00', 2, 11, 2);

INSERT INTO agendamento (id_agendamento, data_hora, fk_cliente, fk_agenda, fk_servico)
VALUES (6, '2024-10-01 15:00:00', 1, 11, 3);

INSERT INTO agendamento (id_agendamento, data_hora, fk_cliente, fk_agenda, fk_servico)
VALUES (7, '2024-10-01 16:00:00', 1, 12, 1);

INSERT INTO agendamento (id_agendamento, data_hora, fk_cliente, fk_agenda, fk_servico)
VALUES (8, '2024-10-01 17:30:00', 2, 12, 2);

INSERT INTO agendamento (id_agendamento, data_hora, fk_cliente, fk_agenda, fk_servico)
VALUES (9, '2024-10-01 18:00:00', 1, 12, 3);

