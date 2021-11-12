<img src="https://storage.googleapis.com/golden-wind/experts-club/capa-github.svg" />

# Implementando autenticação com token JWT no Android com Clean Architecture

Implementar autenticação com Token JWT no Android não é uma tarefa fácil. É preciso ter um código bem organizado e com uma boa arquitetura para facilitar o armazenamento e gerenciamento de um token na aplicação, de forma segura e prática.

Nesta aula, iremos aprender a requisitar, armazenar e gerenciar tokens JWT de uma API Rest, utilizando a Clean Architecture e boas práticas de desenvolvimento Android. Além disso tudo, iremos aprender a assinar automaticamente todas as requisições feitas para os endpoints protegidos do serviço utilizando um interceptor do OkHttp.

### Requisitos para rodar a API Node localmente

- Node.js v14+ (`.nvmrc` incluso no projeto)
- Um banco PostgreSQL (existe um docker-compose no projeto, para subir o banco usando Docker)

### Como rodar a API Node localmente

0. Antes de tudo, acesse a pasta auth-jwt que está na raiz do projeto Android;
0. Rode o comando `docker-compose up -d --force-recreate --renew-anon-volumes`, para rodar o banco de dados localmente com Docker;
0. Rode o comando `npm install`;
0. Rode o comando `npm run sequelize:migrate` para gerar as tabelas;
0. Rode o comando `npm run dev` para rodar o projeto usando nodemon.

## Expert

| [<img src="https://avatars.githubusercontent.com/u/3431943?v=4?s=460&u=0ba16a79456c2f250e7579cb388fa18c5c2d7d65&v=4" width="75px;"/>](https://github.com/douglasramalho) |
| :-: |
|[Douglas Motta](https://github.com/douglasramalho)|
