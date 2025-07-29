# tech-challenge-4a-fase_cliente_quarkus

## Descrição
Este projeto é uma API desenvolvida utilizando o framework Quarkus, seguindo os princípios da Arquitetura Limpa. A API oferece endpoints para gerenciar clientes, incluindo operações de criação, atualização, listagem e exclusão.

## Endpoints da API
- **POST /clientes**: Cadastra um novo cliente.
- **GET /clientes**: Lista todos os clientes.
- **GET /clientes/{id}**: Obtém detalhes de um cliente específico.
- **PUT /clientes/{id}**: Atualiza as informações de um cliente.
- **DELETE /clientes/{id}**: Remove um cliente do sistema.

## Arquitetura Limpa
Este projeto segue os princípios da Arquitetura Limpa, que promove a separação de responsabilidades e facilita a manutenção e evolução do código. A lógica de negócios é isolada dos detalhes de implementação, como frameworks e bancos de dados.

## Configuração do Ambiente
1. **Variável de Ambiente**:
    - Crie uma variável de ambiente chamada `USER_DATASOURCE_PASSWORD` com o valor `root`.

2. **Configuração do Banco de Dados**:
    - Execute o script `script_db_cliente.sql` linha por linha no seu MySQL local ou remoto para configurar o banco de dados necessário para a API.

## Próximos Passos
Nos próximos passos, estaremos criando um container para esta API. Isso facilitará a inicialização do banco de dados, dos serviços da API e de outros serviços utilizados, garantindo um ambiente de desenvolvimento e execução mais consistente.

## Execução
Para executar a API localmente, siga as instruções abaixo:
1. Certifique-se de que o MySQL está em execução e configurado corretamente.
2. Execute o projeto usando o Maven:
   ```bash
   ./mvnw quarkus:dev