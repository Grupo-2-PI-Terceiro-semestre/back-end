INSERT INTO categoria (nome)
VALUES ('Salões de Beleza');
INSERT INTO categoria (nome)
VALUES ('Barbearias');
INSERT INTO categoria (nome)
VALUES ('Manicure & Nail Designer');
INSERT INTO categoria (nome)
VALUES ('Esteticistas');
INSERT INTO categoria (nome)
VALUES ('Sobrancelhas & Cílios');

INSERT INTO funcao (nome_funcao)
VALUES ('Cabelereiro');
INSERT INTO funcao (nome_funcao)
VALUES ('Barbeiro');
INSERT INTO funcao (nome_funcao)
VALUES ('Manicure');
INSERT INTO funcao (nome_funcao)
VALUES ('Esteticista');
INSERT INTO funcao (nome_funcao)
VALUES ('Recepcionista');

INSERT INTO endereco (bairro, cep, cidade, complemento, estado, LOGRADOURO, numero)
VALUES ('Bairro Exemplo', '12345-678', 'Cidade Exemplo', 'Apto 456', 'Estado Exemplo', 'Rua Exemplo', '123');

INSERT INTO empresa (fk_endereco, fk_categoria, url_imagem, cnpj, email_empresa, nome_empresa, telefone)
VALUES (1, 2, 'https://alexandrecarvalho.com/wp-content/uploads/2019/12/outstonelogo.jpg', '50438712000265',
        'contato1@minhanovaempresa.com', 'Minha Nova Empresa', '+5511999999999');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 50.00, 'Corte', 'Corte de cabelo masculino e feminino, com técnicas modernas e estilo personalizado para cada cliente.', '#4A90E2', '01:00:00');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 30.00, 'Barba', 'Barba feita com navalha, incluindo modelagem e acabamento, para um visual impecável e sofisticado.', '#7ED321', '00:30:00');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 20.00, 'Bigode', 'Modelagem do bigode, com detalhes e acabamentos que destacam a sua personalidade e estilo.', '#F5A623', '00:30:00');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 25.00, 'Descoloração', 'Descoloração de bigode, com produtos de alta qualidade que garantem a saúde dos pelos e um resultado duradouro.', '#D0021B', '02:30:00');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 55.00, 'Combo', 'Corte e barba, combinando as melhores técnicas de barbearia para um resultado final excepcional.', '#9013FE', '02:00:00');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 70.00, 'Pacote Completo', 'Pacote completo com corte de cabelo, barba e tratamento facial, para um dia de cuidados especiais e relaxamento.', '#F8E71C', '03:00:00');


INSERT INTO usuarios (data_nascimento, fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                      firebase_uid, fk_funcao, genero, nome_pessoa, numero_telefone, senha)
VALUES (NULL, 1, 1, 1, 0, 12345678905, 'joao.silva@example.com', '', 1, NULL, 'João da Silva', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.');

INSERT INTO usuarios (data_nascimento, fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                      firebase_uid, fk_funcao, genero, nome_pessoa, numero_telefone, senha)
VALUES (NULL, 1, 1, 0, 1, '12345678902', 'maria.silva@example.com', '', 2, 'F', 'Maria da Silva', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.');

INSERT INTO usuarios (data_nascimento, fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                      firebase_uid, fk_funcao, genero, nome_pessoa, numero_telefone, senha)
VALUES (NULL, 1, 1, 0, 1, '12345678902', 'jose.silva@example.com', '', 1, 'M', 'José da Silva', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.');

INSERT INTO usuarios (data_nascimento, fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                      firebase_uid, fk_funcao, genero, nome_pessoa, numero_telefone, senha)
VALUES (NULL, 1, 1, 0, 1, '12345678902', 'felipe.silva@example.com', '', 1, 'M', 'Felipe', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.');

INSERT INTO cliente (fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, '1985-03-15', 'maria@gmail.com', 'F', 'Maria', '11988888888');

INSERT INTO cliente (fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, '1992-07-20', 'andre@gmail.com', 'M', 'André', '11977777777');

INSERT INTO cliente (fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, '1988-11-11', 'luana@gmail.com', 'F', 'Luana', '11966666666');

INSERT INTO cliente (fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, '1995-05-25', 'bruno@gmail.com', 'M', 'Bruno', '11955555555');

INSERT INTO cliente (fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, '1993-09-30', 'carla@gmail.com', 'F', 'Carla', '11944444444');

INSERT INTO cliente (fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, '1991-12-12', 'rafael@gmail.com', 'M', 'Rafael', '11933333333');

INSERT INTO cliente (fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, '1994-06-18', 'tania@gmail.com', 'F', 'Tânia', '11922222222');

INSERT INTO cliente (fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, '1980-01-01', 'carlos@gmail.com', 'M', 'Carlos', '11911111111');


INSERT INTO agenda(fk_usuario)
VALUES (1);

INSERT INTO agenda(fk_usuario)
VALUES (2);

INSERT INTO agenda(fk_usuario)
VALUES (3);

INSERT INTO agenda(fk_usuario)
VALUES (4);

-- Agendamentos para 29 de outubro de 2024
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-29 10:00:00', 'AGENDADO', 1, 1, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-29 10:30:00', 'AGENDADO', 2, 1, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-29 11:00:00', 'AGENDADO', 1, 1, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-29 13:00:00', 'AGENDADO', 1, 2, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-29 14:30:00', 'AGENDADO', 2, 2, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-29 15:00:00', 'AGENDADO', 1, 2, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-29 16:00:00', 'AGENDADO', 1, 3, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-29 17:30:00', 'AGENDADO', 2, 3, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-29 18:00:00', 'AGENDADO', 1, 3, 3);

-- Agendamentos com status REALIZADO (datas passadas)
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-25 09:00:00', 'REALIZADO', 1, 1, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-26 09:30:00', 'REALIZADO', 2, 1, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-27 10:30:00', 'REALIZADO', 1, 1, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-28 12:00:00', 'REALIZADO', 1, 2, 1);


