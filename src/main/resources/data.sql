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

-- Endereços para as empresas
INSERT INTO endereco (bairro, cep, cidade, complemento, estado, logradouro, numero)
VALUES
    ('Cerqueira César', '01414-001', 'São Paulo', 'Apto 456', 'SP', 'Rua Haddock Lobo', '595'),
    ('Centro', '20010-000', 'Rio de Janeiro', 'Sala 101', 'RJ', 'Av. Presidente Vargas', '1000'),
    ('Savassi', '30112-010', 'Belo Horizonte', 'Loja 5', 'MG', 'Rua Pernambuco', '123'),
    ('Boa Viagem', '51020-010', 'Recife', 'Casa', 'PE', 'Rua dos Navegantes', '456'),
    ('Batel', '80420-090', 'Curitiba', 'Conjunto 1203', 'PR', 'Av. Batel', '789'),
    ('Jardim Goiás', '74810-010', 'Goiânia', 'Qd. 45 Lt. 10', 'GO', 'Av. Jamel Cecílio', '250'),
    ('Praia do Canto', '29055-250', 'Vitória', 'Bloco B', 'ES', 'Rua Aleixo Neto', '100'),
    ('Asa Sul', '70390-010', 'Brasília', 'Edifício Brasília Center', 'DF', 'SQS 202', '3'),
    ('Trindade', '88036-000', 'Florianópolis', 'Cobertura', 'SC', 'Rua Lauro Linhares', '890'),
    ('Meireles', '60160-150', 'Fortaleza', 'Apartamento 802', 'CE', 'Av. Beira Mar', '555'),
    ('Vila Mariana', '04018-001', 'São Paulo', 'Sala 204', 'SP', 'Rua Vergueiro', '1001');

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (1, 2, 'https://storage.googleapis.com/udois-261822.appspot.com/imagens-templates/thumbnail_1735372022031662324a1938845.webp', '96541975000104',
        'contato1@minhanovaempresa.com', 'Barber Mania', '+5511999999999');

insert into imagem (fk_empresa, url_imagem)
values (1, 'https://www.goldhairmoveis.com.br/wp-content/uploads/2016/11/excelencia-no-salao-de-beleza.jpg'),
       (1, 'https://s2.glbimg.com/Ha2q-YYa3pCWtwM4E51zi_p-POI=/940x523/e.glbimg.com/og/ed/f/original/2019/02/20/blow-dry-bar-del-mar-chairs-counter-853427.jpg'),
       (1, 'https://frizzar.com.br/blog/wp-content/uploads/2022/07/moveis-em-sala-de-beleza-compressed-1024x571.jpg'),
       (1, 'https://contabilidaderm.com.br/wp-content/uploads/2023/07/Analise-Financeira-para-Salao-de-Beleza.jpg');

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
    VALUES (1, 1, 1, 0, 98260012014, 'joao.silva@example.com', '', 1, 'João da Silva', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO');

INSERT INTO usuarios(fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                     firebase_uid, fk_funcao, nome_pessoa, numero_telefone, senha, status_atividade)
VALUES (1, 1, 0, 1, '00637754093', 'maria.silva@example.com', '', 2, 'Maria da Silva', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO');

INSERT INTO usuarios(fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                     firebase_uid, fk_funcao, nome_pessoa, numero_telefone, senha, status_atividade)
VALUES (1, 1, 0, 1, '98753157028', 'jose.silva@example.com', '', 1, 'José da Silva', NULL,
        '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO');

INSERT INTO usuarios(fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                     firebase_uid, fk_funcao, nome_pessoa, numero_telefone, senha, status_atividade)
VALUES (1, 1, 0, 1, '67662104001', 'felipe.silva@example.com', '', 1, 'Felipe', NULL,
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



-- Serviços para a Empresa 1

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (2, 1, 'https://marketplace.canva.com/EAE_pF9jQO8/1/0/1600w/canva-logotipo-circular-vintage-floral-terracota-para-est%C3%A9tica-e-beleza-isR0zH2IodY.jpg', '66325467000110',
        'contato@belezaexpress.com', 'Beleza Express', '+5511988887777');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES
    (2, 80.00, 'Escova Progressiva', 'Alisamento profissional para cabelos de todos os tipos.', '#FF5733', '02:00:00', 'ATIVO'),
    (2, 60.00, 'Hidratação Capilar', 'Tratamento intenso para cabelos secos e danificados.', '#C70039', '01:00:00', 'ATIVO'),
    (2, 50.00, 'Corte Feminino', 'Corte moderno e personalizado para mulheres.', '#FFBD33', '01:00:00', 'ATIVO'),
    (2, 45.00, 'Corte Masculino', 'Corte estilizado para homens.', '#8B008B', '00:45:00', 'ATIVO'),
    (2, 90.00, 'Penteado Especial', 'Penteados para ocasiões especiais.', '#00CED1', '01:30:00', 'ATIVO');

-- Serviços para a Empresa 2

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (3, 2, 'https://s3-sa-east-1.amazonaws.com/projetos-artes/fullsize%2F2022%2F05%2F03%2F14%2FLogo-282869_335130_140920012_1113390457.jpg', '66325467000110',
        'contato@barberking.com', 'Barber King', '+5511977776666');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES
    (3, 45.00, 'Corte Degradê', 'Corte moderno e estiloso com acabamento perfeito.', '#581845', '01:00:00', 'ATIVO'),
    (3, 30.00, 'Barba Navalhada', 'Barba feita com navalha para um acabamento impecável.', '#DAA520', '00:30:00', 'ATIVO'),
    (3, 50.00, 'Combo Corte + Barba', 'Pacote completo com corte e barba.', '#B22222', '01:30:00', 'ATIVO'),
    (3, 20.00, 'Bigode Modelado', 'Modelagem precisa para destacar o bigode.', '#228B22', '00:30:00', 'ATIVO'),
    (3, 60.00, 'Relaxamento Capilar', 'Relaxamento para controle de volume e cachos.', '#4682B4', '01:30:00', 'ATIVO');

-- Serviços para a Empresa 3

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (4, 3, 'https://img.irroba.com.br/fit-in/600x600/filters:fill(fff):quality(80)/kreative/catalog/adesivos-parede-manciure/mod-1601/adesivo-parede-manicure-pedicure-ref-1601-foto-1.JPG', '66325467000110',
        'contato@topmanicure.com', 'Top Manicure', '+5511966665555');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES
    (4, 25.00, 'Unhas Decoradas', 'Decoração artística de unhas com alta qualidade.', '#DAF7A6', '00:45:00', 'ATIVO'),
    (4, 20.00, 'Cutilagem', 'Remoção de cutículas com cuidado e precisão.', '#FA8072', '00:30:00', 'ATIVO'),
    (4, 30.00, 'Esmaltação em Gel', 'Unhas duradouras com acabamento brilhante.', '#FFD700', '01:00:00', 'ATIVO'),
    (4, 40.00, 'Spa para Pés', 'Hidratação e cuidados especiais para os pés.', '#40E0D0', '01:00:00', 'ATIVO'),
    (4, 35.00, 'Manicure Completa', 'Pacote completo com cutilagem, esmaltação e hidratação.', '#8A2BE2', '01:15:00', 'ATIVO');

-- Serviços para a Empresa 4

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (5, 4, 'https://artpoin.com/wp-content/uploads/2019/11/132213-scaled.png', '66325467000110',
        'contato@esteticanova.com', 'Estética Nova', '+5511955554444');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES
    (5, 100.00, 'Limpeza de Pele', 'Limpeza profunda para remoção de impurezas da pele.', '#FFC300', '01:30:00', 'ATIVO'),
    (5, 120.00, 'Peeling Facial', 'Tratamento para renovação da pele.', '#CD5C5C', '01:00:00', 'ATIVO'),
    (5, 80.00, 'Hidratação Facial', 'Hidratação intensa para uma pele mais saudável.', '#4682B4', '00:45:00', 'ATIVO'),
    (5, 150.00, 'Tratamento Anti-Aging', 'Redução de linhas de expressão e rejuvenescimento.', '#8B4513', '02:00:00', 'ATIVO'),
    (5, 200.00, 'Massagem Corporal', 'Massagem relaxante para alívio de tensões.', '#FF69B4', '01:30:00', 'ATIVO');

-- Serviços para a Empresa 5

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (6, 5, 'https://i.pinimg.com/736x/ec/6d/f6/ec6df648a26bc7dcf282cf0d65786040.jpg', '66325467000110',
        'contato@sobrancelhaplus.com', 'Sobrancelha Plus', '+5511944443333');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES
    (6, 40.00, 'Design de Sobrancelhas', 'Design especializado para sobrancelhas.', '#581845', '00:30:00', 'ATIVO'),
    (6, 50.00, 'Henna', 'Aplicação de henna para realce das sobrancelhas.', '#2F4F4F', '00:45:00', 'ATIVO'),
    (6, 70.00, 'Micropigmentação', 'Técnica de preenchimento semipermanente.', '#9932CC', '02:00:00', 'ATIVO'),
    (6, 60.00, 'Laminação de Sobrancelhas', 'Realce natural com alinhamento perfeito.', '#20B2AA', '01:00:00', 'ATIVO'),
    (6, 45.00, 'Tingimento de Sobrancelhas', 'Coloração especial para destacar as sobrancelhas.', '#FF6347', '00:30:00', 'ATIVO');

-- Serviços para a Empresa 6

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (7, 1, 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/beauty-salon-logo-salao-de-beleza-logotipo-ha-design-template-faab7ba5452b192ba09127ce05617bda_screen.jpg?ts=1589058791', '66325467000110',
        'contato@hairstylistpro.com', 'Hair Stylist Pro', '+5511933332222');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES
    (7, 120.00, 'Coloração', 'Coloração profissional com tintas de alta qualidade.', '#C70039', '02:00:00', 'ATIVO'),
    (7, 50.00, 'Escova Modeladora', 'Escova com modelagem personalizada.', '#FF8C00', '01:00:00', 'ATIVO'),
    (7, 80.00, 'Luzes', 'Mechas luminosas para realçar o cabelo.', '#FFD700', '02:00:00', 'ATIVO'),
    (7, 40.00, 'Corte Infantil', 'Corte especial para crianças.', '#6495ED', '00:45:00', 'ATIVO'),
    (7, 100.00, 'Tratamento Capilar', 'Tratamento para recuperação de fios danificados.', '#8B008B', '01:30:00', 'ATIVO');

-- Serviços para a Empresa 7

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (8, 2, 'https://app.nbbarbearia.com.br/user-uploads/logo/308c29be02abe759eadb278ab1390e2f.png', '66325467000110',
        'contato@vipbarbers.com', 'VIP Barbers', '+5511922221111');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES
    (8, 70.00, 'Barba Completa', 'Barba com modelagem e cuidados especiais.', '#7ED321', '01:00:00', 'ATIVO'),
    (8, 55.00, 'Corte Social', 'Corte clássico e formal.', '#32CD32', '00:45:00', 'ATIVO'),
    (8, 65.00, 'Terapia Capilar', 'Tratamento especial para couro cabeludo.', '#FFD700', '01:30:00', 'ATIVO'),
    (8, 90.00, 'Pacote Premium', 'Corte, barba e tratamento especial.', '#FF4500', '02:30:00', 'ATIVO'),
    (8, 25.00, 'Hidratação Masculina', 'Hidratação para cabelos masculinos.', '#40E0D0', '01:00:00', 'ATIVO');

-- Serviços para a Empresa 8

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (9, 3, 'https://blog.agenciadosite.com.br/wp-content/uploads/2017/02/logo-mancini-e1486727339396.jpg', '66325467000110',
        'contato@glamournails.com', 'Glamour Nails', '+5511911110000');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES
    (9, 35.00, 'Manicure SPA', 'Tratamento completo de manicure com hidratação.', '#DAF7A6', '01:00:00', 'ATIVO'),
    (9, 30.00, 'Francesinha', 'Unhas decoradas com estilo clássico.', '#FFB6C1', '00:45:00', 'ATIVO'),
    (9, 45.00, 'Alongamento de Unhas', 'Unhas longas com aplicação de gel.', '#FFD700', '01:30:00', 'ATIVO'),
    (9, 50.00, 'Pedicure Completa', 'Cuidados especiais para os pés.', '#8A2BE2', '01:15:00', 'ATIVO'),
    (9, 25.00, 'Fortalecimento de Unhas', 'Tratamento para unhas frágeis.', '#4682B4', '00:45:00', 'ATIVO');

-- Serviços para a Empresa 9

INSERT INTO empresa (fk_endereco, fk_categoria, url_logo, cnpj, email_empresa, nome_empresa, telefone)
VALUES (10, 4, 'https://png.pngtree.com/png-vector/20220725/ourmid/pngtree-spa-logo-png-image_6060681.png', '66325467000110',
        'contato@luxbeauty.com', 'Lux Beauty', '+5511988881111');

INSERT INTO servico (fk_empresa, valor_servico, nome_servico, descricao, cor_referencia_hex, duracao, status_atividade)
VALUES
    (10, 150.00, 'Massagem Relaxante', 'Massagem para alívio de estresse e relaxamento.', '#FF4500', '02:00:00', 'ATIVO'),
    (10, 100.00, 'Reflexologia', 'Tratamento terapêutico para os pés.', '#228B22', '01:30:00', 'ATIVO'),
    (10, 80.00, 'Massagem com Pedras Quentes', 'Relaxamento profundo com pedras aquecidas.', '#FFA07A', '01:45:00', 'ATIVO'),
    (10, 120.00, 'Drenagem Linfática', 'Massagem para eliminação de líquidos e toxinas.', '#32CD32', '02:00:00', 'ATIVO'),
    (10, 70.00, 'Shiatsu', 'Massagem tradicional japonesa.', '#7B68EE', '01:30:00', 'ATIVO');


INSERT INTO usuarios(fk_empresa, fk_endereco, representante, tipos_de_usuario, cpf, email_pessoa,
                     firebase_uid, fk_funcao, nome_pessoa, numero_telefone, senha, status_atividade)
VALUES
    (2, 2, 1, 0, '12345678901', 'admin@belezaexpress.com', '', 1, 'Administrador Belezza Express', NULL,
     '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO'),
    (3, 3, 1, 0, '12345678902', 'admin@barberking.com', '', 1, 'Administrador Barber King', NULL,
     '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO'),
    (4, 4, 1, 0, '12345678903', 'admin@topmanicure.com', '', 1, 'Administrador Top Manicure', NULL,
     '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO'),
    (5, 5, 1, 0, '12345678904', 'admin@esteticanova.com', '', 1, 'Administrador Estética Nova', NULL,
     '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO'),
    (6, 6, 1, 0, '12345678905', 'admin@sobrancelhaplus.com', '', 1, 'Administrador Sobrancelha Plus', NULL,
     '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO'),
    (7, 7, 1, 0, '12345678906', 'admin@hairstylistpro.com', '', 1, 'Administrador Hair Stylist Pro', NULL,
     '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO'),
    (8, 8, 1, 0, '12345678907', 'admin@vipbarbers.com', '', 1, 'Administrador VIP Barbers', NULL,
     '$2a$10$jTFvRNxv.JBvUvLtCFSeR.UY2O/ugXv.kKYOIuKnwz4GgHKxzRIN.', 'ATIVO');


-- DIA 05/12/2024
-- Agenda 3
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 06:30:00', 'REALIZADO', 2, 3, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 09:30:00', 'REALIZADO', 1, 3, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 12:30:00', 'REALIZADO', 2, 3, 4);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 15:00:00', 'REALIZADO', 1, 3, 5);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 18:30:00', 'AGENDADO', 2, 3, 6);

-- Agenda 2
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 08:00:00', 'REALIZADO', 1, 2, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 11:30:00', 'REALIZADO', 2, 2, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 14:00:00', 'REALIZADO', 1, 2, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 16:30:00', 'REALIZADO', 2, 2, 5);

-- Agenda 1
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 09:00:00', 'REALIZADO', 1, 1, 6);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 12:00:00', 'REALIZADO', 2, 1, 4);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 15:30:00', 'AGENDADO', 1, 1, 3);

-- Agenda 4
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 07:30:00', 'REALIZADO', 2, 4, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 10:00:00', 'REALIZADO', 1, 4, 6);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 13:00:00', 'REALIZADO', 2, 4, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 15:30:00', 'REALIZADO', 1, 4, 5);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-03 18:00:00', 'AGENDADO', 2, 4, 4);


-- DIA 07/12/2024
-- Agenda 3
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 06:30:00', 'AGENDADO', 2, 3, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 09:30:00', 'AGENDADO', 1, 3, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 12:00:00', 'AGENDADO', 2, 3, 5);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 14:30:00', 'AGENDADO', 1, 3, 6);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 17:30:00', 'AGENDADO', 2, 3, 2);

-- Agenda 2
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 08:00:00', 'AGENDADO', 1, 2, 4);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 09:30:00', 'AGENDADO', 2, 2, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 12:00:00', 'AGENDADO', 1, 2, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 14:00:00', 'AGENDADO', 2, 2, 5);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 15:30:00', 'AGENDADO', 1, 2, 6);

-- Agenda 1
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 09:00:00', 'AGENDADO', 2, 1, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 12:00:00', 'AGENDADO', 1, 1, 4);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 13:30:00', 'AGENDADO', 2, 1, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 16:00:00', 'AGENDADO', 1, 1, 5);

-- Agenda 4
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 07:30:00', 'AGENDADO', 1, 4, 6);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 10:00:00', 'AGENDADO', 2, 4, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 12:00:00', 'AGENDADO', 1, 4, 4);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 13:30:00', 'AGENDADO', 2, 4, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-04 15:30:00', 'AGENDADO', 1, 4, 5);


INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 06:30:00', 'REALIZADO', 2, 3, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 09:30:00', 'REALIZADO', 1, 3, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 12:00:00', 'REALIZADO', 2, 3, 5);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 14:30:00', 'REALIZADO', 1, 3, 6);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 17:30:00', 'REALIZADO', 2, 3, 2);

-- Agenda 2
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 08:00:00', 'REALIZADO', 1, 2, 4);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 09:30:00', 'REALIZADO', 2, 2, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 12:00:00', 'REALIZADO', 1, 2, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 14:00:00', 'REALIZADO', 2, 2, 5);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 15:30:00', 'REALIZADO', 1, 2, 6);

-- Agenda 1
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 09:00:00', 'REALIZADO', 2, 1, 2);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 12:00:00', 'REALIZADO', 1, 1, 4);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 13:30:00', 'REALIZADO', 2, 1, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 16:00:00', 'REALIZADO', 1, 1, 5);

-- Agenda 4
INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 07:30:00', 'REALIZADO', 1, 4, 6);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 10:00:00', 'REALIZADO', 2, 4, 1);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 12:00:00', 'REALIZADO', 1, 4, 4);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 13:30:00', 'REALIZADO', 2, 4, 3);

INSERT INTO agendamento (data_hora, status_agendamento, fk_cliente, fk_agenda, fk_servico)
VALUES ('2024-12-02 15:30:00', 'REALIZADO', 1, 4, 5);


INSERT INTO agendamento (fk_agenda, fk_cliente, fk_servico,data_hora, status_agendamento)
VALUES
    (1, 1, 1,  '2024-08-15 10:30:00.000000', 'REALIZADO'),
    (2, 3, 4,  '2024-08-20 15:00:00.000000', 'REALIZADO'),
    (3, 3, 5,  '2024-09-05 18:45:00.000000', 'CANCELADO'),
    (1, 4, 4,'2024-09-10 08:20:00.000000', 'REALIZADO'),
    (2, 8, 6, '2024-10-14 11:00:00.000000', 'REALIZADO'),
    (4, 4, 5, '2024-10-20 13:15:00.000000', 'REALIZADO'),
    (4, 1, 6, '2024-11-03 19:30:00.000000', 'CANCELADO'),
    (3, 1, 2, '2024-11-10 16:45:00.000000', 'REALIZADO'),
    (2, 3, 3, '2024-11-20 09:15:00.000000', 'REALIZADO'),
    (4, 6, 3, '2024-11-25 18:30:00.000000', 'REALIZADO'),
    (2, 1, 3, '2024-11-28 08:45:00.000000', 'CANCELADO');

INSERT INTO agendamento(fk_agenda, fk_cliente, fk_servico,  data_hora, status_agendamento)
VALUES
    (1, 3, 1, '2024-08-18 14:00:00.000000', 'REALIZADO'),
    (3, 4, 3, '2024-08-25 16:45:00.000000', 'REALIZADO'),
    (2, 3, 4, '2024-09-12 09:30:00.000000', 'REALIZADO'),
    (1, 4, 4, '2024-09-18 11:15:00.000000', 'CANCELADO'),
    (2, 8, 6, '2024-10-08 15:50:00.000000', 'REALIZADO'),
    (4, 4, 5, '2024-10-22 18:00:00.000000', 'REALIZADO'),
    (4, 3, 5, '2024-11-05 20:30:00.000000', 'CANCELADO'),
    (3, 1, 2, '2024-11-12 10:15:00.000000', 'REALIZADO'),
    (2, 3, 3, '2024-11-22 17:00:00.000000', 'REALIZADO'),
    (4, 6, 3, '2024-11-28 09:45:00.000000', 'REALIZADO'),
    (2, 1, 3, '2024-11-30 13:00:00.000000', 'CANCELADO');







