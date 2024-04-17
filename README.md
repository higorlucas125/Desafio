# Desafio Itau

Desafio criar uma API de transferência bancária

## Installation

Para instalar o projeto em sua maquina local, vamos precisar de um keyclock rodando em sua maquina local, para isso siga os passos abaixo:

```bash
docker pull keycloak/keycloak:17.0.0
docker run -p 8080:8080 \ 
    -e KEYCLOAK_ADMIN=admin \ 
    -e KEYCLOAK_ADMIN_PASSWORD=password \ 
    keycloak/keycloak:17.0.0  start-dev
```

Ou executar o docker-compose.yml

```bash
docker-compose up --build -d
```
## Ferramentas

- Java 17
- maven 3.8.3

## Url de acesso

- http://localhost:8080 ou http://http://0.0.0.0:8080 Acessar o keycloak
- http://localhost:9091  Acessar api de transação bancária


1. Ser desenvolvida em linguagem Java/Spring Boot; (OK)
2. Validar se o cliente que vai receber a transferência existe passando o idCliente na API
   de Cadastro;
3. Buscar dados da conta origem passando idConta na API de Contas;
4. Validar se a conta corrente está ativa;
5. Validar se o cliente tem saldo disponível na conta corrente para realizar a transferência;
6. A API de contas retornará o limite diário do cliente, caso o valor seja zero ou menor do
   que o valor da transferência a ser realizada, a transferência não poderá ser realizada;
7. Após a transferência é necessário notificar o BACEN de forma síncrona que a transação
   foi concluída com sucesso. A API do BACEN tem controle de rate limit e pode retornar
   429 em caso de chamadas que excedam o limite;
8. Impedir que falhas momentâneas das dependências da aplicação impactem a
   experiência do cliente;


2 - Desafio arquitetura de solução:
Crie um desenho de solução preferencialmente AWS para a API que foi desenvolvida no
desafio de engenharia de software considerando os requisitos abaixo:


Sem resolução AWS

1. Apresentar uma proposta de escalonamento para casos de oscilação de carga;
   - Utilização do K8s para escalar os pods de acordo com a demanda assim podendo gerenciar as maquinas de forma mais eficiente
   - Utilização de um balanceador de carga para distribuir as requisições entre os pods como o nginx
   - Utilização do proxy reverso para distribuir as requisições entre as Apis o Keycloak
2. Apresentar uma proposta de observabilidade;
   - Utilização de Logs como O Kibana para rastrear 
   - Utilização de metricas como o Prometheus para monitorar ou o New Relic
   - Utilização de Tracing como o Jaeger para rastrear requisições
3. Caso utilizado, justificar o uso de caching;
   - Utilização de cache para armazenar os dados de clientes e contas para evitar requisições desnecessárias
4. A solução precisa minimizar o impacto em caso de falhas das dependências (API
   Cadastro, Conta e BACEN).




AWS Lambda para Notificação Síncrona:

Após uma transferência bem-sucedida, utilize uma função AWS Lambda para notificar o BACEN de forma síncrona. Isso ajuda a separar o processo de notificação do restante da aplicação e permite dimensionar facilmente a notificação conforme necessário.
Amazon API Gateway:

Exponha sua API Spring Boot através do Amazon API Gateway. Isso permite que as chamadas para sua API sejam roteadas de forma controlada e gerenciada, além de fornecer autenticação, autorização, limites de taxa e monitoramento.
Amazon RDS para o Banco de Dados:

Hospede seu banco de dados relacional, como MySQL ou PostgreSQL, no Amazon RDS. Isso fornece escalabilidade, backup automático e gerenciamento simplificado do banco de dados.
Amazon SQS para Fila de Mensagens:

Utilize o Amazon SQS para desacoplar o processo de notificação do BACEN do processo principal de transferência. Após uma transferência bem-sucedida, envie uma mensagem para uma fila SQS, que será processada pela função Lambda de notificação.
AWS Lambda para Validações:

Crie funções AWS Lambda para realizar as validações necessárias, como validar a existência do cliente, buscar dados da conta origem, validar a conta corrente e verificar o saldo disponível. Isso garante que cada etapa seja escalável e independente.
Amazon CloudWatch para Monitoramento:

Use o Amazon CloudWatch para monitorar o desempenho de suas funções Lambda, fila SQS e outros recursos da AWS. Configure alarmes para detectar falhas ou atrasos no processamento de mensagens.
Amazon DynamoDB para Cache:

Utilize o Amazon DynamoDB como um cache para armazenar temporariamente informações de clientes, contas e limites diários. Isso pode ajudar a acelerar as operações de validação e reduzir o número de chamadas à API de Cadastro e Contas.
Amazon ElastiCache para Cache de Dados:

Use o Amazon ElastiCache para armazenar em cache os resultados de consultas frequentes ao banco de dados, como consultas de saldo de conta e limites diários. Isso ajuda a reduzir a carga no banco de dados e melhora o desempenho da aplic