# Order Hub

## Sistema de Agendamento e Gerenciamento para Empresas de Estética

## Descrição

Este projeto é uma aplicação backend desenvolvida em Java com o objetivo de gerenciar o agendamento e o gerenciamento de empresas de estética, como salões de beleza e barbearias. A aplicação visa fornecer uma plataforma eficiente para os proprietários dessas empresas, facilitando a administração de agendamentos, clientes e serviços.

## Funcionalidades

- **Gestão de Agendamentos**: Permite agendar, modificar e cancelar compromissos entre clientes e profissionais.
- **Cadastro de Clientes**: Facilita o gerenciamento das informações dos clientes, incluindo histórico de agendamentos.
- **Gestão de Profissionais**: Permite o cadastro e gerenciamento dos profissionais que trabalham na empresa, suas especialidades e horários disponíveis.
- **Cadastro de Serviços**: Gerencia os serviços oferecidos, incluindo preços e descrições.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal.
- **Spring Boot**: Framework para criar aplicativos Java de forma rápida e com mínima configuração.
- **MySQL**: Banco de dados relacional.
- **Maven**: Gerenciador de dependências e construção do projeto.
- **Docker Compose**: Para configurar e executar o ambiente do banco de dados MySQL.

## Configuração do Ambiente de Desenvolvimento

### Pré-requisitos

- Java 17 instalado
- Maven instalado
- Docker e Docker Compose instalados

### Instalação

1. **Clone o repositório**

    ``
    git clone https://github.com/Grupo-2-PI-Terceiro-semestre/back-end.git
    ``

2. **Configure o banco de dados**

   O projeto já inclui um arquivo `docker-compose-mysql.yml` na raiz, que configura um banco de dados MySQL. Para iniciar o banco de dados, execute:

       docker-compose-mysql up -d

   Este comando iniciará um contêiner MySQL e criará um banco de dados chamado `orderhub` com as credenciais especificadas.



   **Nota:** Após a primeira execução da aplicação, que irá criar o banco de dados e as tabelas necessárias, é importante alterar a configuração `spring.jpa.hibernate.ddl-auto` para `none`. Isso evita que o Hibernate tente criar ou atualizar o esquema do banco de dados em execuções subsequentes, prevenindo a perda acidental de dados e garantindo que o esquema do banco de dados seja gerenciado de maneira adequada. A configuração para produção deve ser ajustada para:

       application.properties
       spring.jpa.hibernate.ddl-auto=none

3. **Compile e inicie o projeto**

       mvn clean install
       mvn spring-boot:run

## Modelagem
![Logo](/DER%20-%20V1.svg)