# <div align="center"> Webflux Support  </div>

<br/>

# <div align="center"> 🚧 Concluído 🚧  </div>

<br/>

## Conteúdos

* [Pré-requisitos](#pré-requisitos)
* [Sobre](#sobre)
* [Como usar](#como-usar)
* [Tecnologias](#tecnologias)

<br/>

## Pré-requisitos

- JDK 17
- Postman
- Intellij
- PostgreSql

<br/>

## Sobre

Este projeto é um sistema de suporte online desenvolvido utilizando Java 17 e Spring WebFlux, seguindo uma arquitetura reativa para garantir alto desempenho e escalabilidade. O sistema permite que os usuários relatem problemas, enviem suas solicitações e aguardem na fila até serem atendidos. A posição do usuário na fila é dinâmica, mudando à medida
que atendentes começam a atender os clientes.

Este é sómente um pequeno exemplo para estudos e não contempla um sistema completo.

### Funcionalidades

- **Relatar Problema e Aguardar na Fila:** O usuário registra um novo atendimento, fornecendo informações detalhadas sobre o problema. A solicitação é adicionada a uma fila, e o sistema retorna dados como a posição do cliente na fila, permitindo o acompanhamento em tempo real.
- **Posição na Fila:** Sempre que um atendente começa a atender um cliente, a posição do usuário na fila é atualizada automaticamente, refletindo a mudança de estado de "Aguardando Atendimento" para "Em Atendimento". Esta lógica de movimentação é gerenciada de forma eficiente e assíncrona com o uso de WebFlux.
- **Listagem de Solicitações pelo Atendente:** Os atendentes têm acesso a uma lista de todos os registros em espera, podendo visualizar as informações detalhadas de cada atendimento. Quando um novo cliente envia uma solicitação, ela aparece automaticamente na lista do atendente, sem a necessidade de recarregar a página, utilizando o modelo de
  programação reativa.

## Arquitetura Reativa com WebFlux

O sistema é projetado com o Spring WebFlux, que adota a programação reativa, permitindo que ele lide com grandes volumes de solicitações simultâneas de maneira não bloqueante. Isso significa que o servidor pode processar várias requisições de usuários e atendentes em paralelo sem bloquear threads, melhorando a escalabilidade e a capacidade de
resposta, especialmente em sistemas que requerem alta disponibilidade e baixo tempo de latência.

A utilização de Flux e Mono, os dois tipos principais de objetos reativos do Spring WebFlux, facilita a modelagem de fluxo de dados assíncronos. O Flux é usado para representar uma sequência de itens (como múltiplas solicitações de atendimento), enquanto o Mono é usado para representar um único valor ou vazio (como a resposta ao envio de um novo
problema).

Além disso, o sistema se beneficia da integração com R2DBC para comunicação com o banco de dados de forma reativa, permitindo que as operações de leitura e escrita sejam não bloqueantes, proporcionando maior eficiência na manipulação de dados em tempo real.

Com essa abordagem, o sistema oferece uma experiência fluida para os usuários e atendentes, com atualizações instantâneas sobre o status das solicitações e atendimento.

<br/>

## Como Usar

Abra seu PostgreSql e crie a base chamada<code>***webflux-support***</code>

Edite o arquivo <code>***application.yml***</code>, e informe as seguintes variáveis de ambiente:

- <code>***${DATABASE_USERNAME}***</code>Usuário do seu banco de dados
- <code>***${DATABASE_PASSWORD}***</code>Senha do seu banco de dados

Agora entre na classe <code>***WebfluxSupportApplication***</code> e execute o método <code>***main***</code>. Quando o projeto iniciar, as tabelas serão criadas através de migrations.

O projeto roda na url ``http://localhost:9000/api/v1``.

### Listar Atendimentos

Abra o Postman e execute o ``curl`` abaixo.

 ```sh
curl --location GET 'http://localhost:9000/api/v1/queue?status=AGUARDANDO_ATENDIMENTO'
 ```

Este endpoint retornará uma lista de atendimento pendentes. Esta consulta tem um delay de 5 segundos entre os registros.
Este delay foi feito para permitir você visualizar um novo registro entrando na fila sem a
necessidade de executar o endpoint novamente.

### Criar Registro

Abra o Postman e execute o ``curl`` abaixo.

 ```sh
curl --location POST 'http://localhost:9000/api/v1/queue' \
--header 'Content-Type: application/json' \
--data '{   
    "title": "Lorem Ipsum",
    "problemDescription": "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
    "idCustomer": "060c053b-f746-4eee-a63d-24dcd19daa0b"
}'
 ```

Este endpoint criará um no registro de suporte e você poderá vê-lo na ``Listagem de Atendimentos``.

<br/>

## Tecnologias

![Java](https://img.shields.io/static/v1?label=Java&message=17&color=green)
![Spring Webflux](https://img.shields.io/static/v1?label=spring-webflux&message=3.4.3&color=green)
![PostgreSql](https://img.shields.io/static/v1?label=postgresql&message=42.7.4&color=green)
![R2DBC PostgreSql](https://img.shields.io/static/v1?label=r2dbc-postgresql&message=1.0.7&color=green)
![h2database](https://img.shields.io/static/v1?label=h2database&message=2.3.232&color=green)
![FlywayDB](https://img.shields.io/static/v1?label=flywaydb&message=10.21.1&color=green)
