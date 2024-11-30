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

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (1, 2, 'https://alexandrecarvalho.com/wp-content/uploads/2019/12/outstonelogo.jpg', '50438712000265',
        'contato1@minhanovaempresa.com', 'Minha Nova Empresa', '+5511999999999');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES (1, 50.00, 'Corte',
        'Corte de cabelo masculino e feminino, com técnicas modernas e estilo personalizado para cada cliente.',
        '#4A90E2', '01:00:00', 'ATIVO');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES (1, 30.00, 'Barba',
        'Barba feita com navalha, incluindo modelagem e acabamento, para um visual impecável e sofisticado.', '#7ED321',
        '00:30:00', 'ATIVO');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES (1, 20.00, 'Bigode',
        'Modelagem do bigode, com detalhes e acabamentos que destacam a sua personalidade e estilo.', '#F5A623',
        '00:30:00', 'ATIVO');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES (1, 25.00, 'Descoloração',
        'Descoloração de bigode, com produtos de alta qualidade que garantem a saúde dos pelos e um resultado duradouro.',
        '#D0021B', '02:30:00', 'ATIVO');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES (1, 55.00, 'Combo',
        'Corte e barba, combinando as melhores técnicas de barbearia para um resultado final excepcional.', '#9013FE',
        '02:00:00', 'ATIVO');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES (1, 70.00, 'Pacote Completo',
        'Pacote completo com corte de cabelo, barba e tratamento facial, para um dia de cuidados especiais e relaxamento.',
        '#F8E71C', '03:00:00', 'ATIVO');


INSERT INTO usuarios(fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                     firebase_uid, fk_funcao, nome_pessoa, numero_telefone, senha, status_atividade)
    VALUES (1, 1, 1, 0, 12345678905, 'joao.silva@example.com', '', 1, 'João da Silva', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO');

INSERT INTO usuarios(fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                     firebase_uid, fk_funcao, nome_pessoa, numero_telefone, senha, status_atividade)
VALUES (1, 1, 0, 1, '12345678902', 'maria.silva@example.com', '', 2, 'Maria da Silva', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO');

INSERT INTO usuarios(fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                     firebase_uid, fk_funcao, nome_pessoa, numero_telefone, senha, status_atividade)
VALUES (1, 1, 0, 1, '12345678902', 'jose.silva@example.com', '', 1, 'José da Silva', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO');

INSERT INTO usuarios(fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                     firebase_uid, fk_funcao, nome_pessoa, numero_telefone, senha, status_atividade)
VALUES (1, 1, 0, 1, '12345678902', 'felipe.silva@example.com', '', 1, 'Felipe', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO');

INSERT INTO cliente (fk_empresa, email_pessoa, nome_pessoa, numero_telefone, status_atividade)
VALUES (1, 'maria@gmail.com', 'Maria', '11988888888', 'ATIVO');

INSERT INTO cliente (fk_empresa, email_pessoa, nome_pessoa, numero_telefone, status_atividade)
VALUES (1, 'andre@gmail.com', 'André', '11977777777', 'ATIVO');

INSERT INTO cliente (fk_empresa, email_pessoa, nome_pessoa, numero_telefone, status_atividade)
VALUES (1, 'luana@gmail.com', 'Luana', '11966666666', 'ATIVO');

INSERT INTO cliente (fk_empresa, email_pessoa, nome_pessoa, numero_telefone, status_atividade)
VALUES (1, 'bruno@gmail.com', 'Bruno', '11955555555', 'ATIVO');

INSERT INTO cliente (fk_empresa, email_pessoa, nome_pessoa, numero_telefone, status_atividade)
VALUES (1, 'carla@gmail.com', 'Carla', '11944444444', 'ATIVO');

INSERT INTO cliente (fk_empresa, email_pessoa, nome_pessoa, numero_telefone, status_atividade)
VALUES (1, 'rafael@gmail.com', 'Rafael', '11933333333', 'ATIVO');

INSERT INTO cliente (fk_empresa, email_pessoa, nome_pessoa, numero_telefone, status_atividade)
VALUES (1, 'tania@gmail.com', 'Tânia', '11922222222', 'ATIVO');

INSERT INTO cliente (fk_empresa, email_pessoa, nome_pessoa, numero_telefone, status_atividade)
VALUES (1, 'carlos@gmail.com', 'Carlos', '11911111111', 'ATIVO');

INSERT INTO agenda(fk_usuario)
VALUES (1);

INSERT INTO agenda(fk_usuario)
VALUES (2);

INSERT INTO agenda(fk_usuario)
VALUES (3);

INSERT INTO agenda(fk_usuario)
VALUES (4);

insert into imagem (fk_empresa, url_imagem)
values (1, 'https://www.goldhairmoveis.com.br/wp-content/uploads/2016/11/excelencia-no-salao-de-beleza.jpg'),
       (1, 'https://s2.glbimg.com/Ha2q-YYa3pCWtwM4E51zi_p-POI=/940x523/e.glbimg.com/og/ed/f/original/2019/02/20/blow-dry-bar-del-mar-chairs-counter-853427.jpg'),
       (1, 'https://frizzar.com.br/blog/wp-content/uploads/2022/07/moveis-em-sala-de-beleza-compressed-1024x571.jpg'),
       (1, 'https://contabilidaderm.com.br/wp-content/uploads/2023/07/Analise-Financeira-para-Salao-de-Beleza.jpg');

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
VALUES ('2024-11-20 10:30:00', 'PENDENTE', 1, 1, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-28 12:00:00', 'REALIZADO', 1, 2, 1);






