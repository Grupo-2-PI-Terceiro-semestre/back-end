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
VALUES (1, 50.00, 'Corte de Cabelo', 'Corte de cabelo masculino e feminino', '#0000ff', '02:30:00');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 30.00, 'Barba', 'Barba feita com navalha', '#000000', '02:20:00');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao)
VALUES (1, 20.00, 'Bigode', 'Bigode feito com navalha', '#000000', '02:00:00');

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
VALUES (NULL, 1, 1, 0, 1, '12345678902', 'felipe.silva@example.com', '', 1, 'M', 'José da Silva', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.');

INSERT INTO cliente (fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, '1990-02-02', 'felipe@gmail.com', 'M', 'Felipe', '11999999999');

INSERT INTO cliente (fk_empresa, data_nascimento, email_pessoa, genero, nome_pessoa, numero_telefone)
VALUES (1, '1990-02-02', 'jose@gmail.com', 'M', 'José', '11999999999');

INSERT INTO agenda(fk_usuario)
VALUES (1);

INSERT INTO agenda(fk_usuario)
VALUES (2);

INSERT INTO agenda(fk_usuario)
VALUES (3);

INSERT INTO agenda(fk_usuario)
VALUES (4);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-04 10:00:00', 'AGENDADO', 1, 1, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-04 10:30:00', 'AGENDADO', 2, 1, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-04 11:00:00', 'AGENDADO', 1, 1, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-04 13:00:00', 'AGENDADO', 1, 2, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-04 14:30:00', 'AGENDADO', 2, 2, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-04 15:00:00', 'AGENDADO', 1, 2, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-04 16:00:00', 'AGENDADO', 1, 3, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-04 17:30:00', 'AGENDADO', 2, 3, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-04 18:00:00', 'AGENDADO', 1, 3, 3);


INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-03 18:00:00', 'AGENDADO', 1, 3, 3);


INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-03 10:00:00', 'AGENDADO', 1, 3, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-10-03 11:00:00', 'AGENDADO', 1, 3, 3);

