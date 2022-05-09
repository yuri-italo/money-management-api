# money-management-api

API desenvolvida durante a Edição #2: Desafios Back-End da [Alura Challenges.](https://www.alura.com.br/challenges/back-end-2/)

Após alguns testes com protótipos feitos pelo time de UX de uma empresa, foi requisitada a primeira versão de uma aplicação para controle de orçamento familiar.** A aplicação deve permitir que uma pessoa cadastre suas receitas e despesas do mês, bem como gerar um relatório mensal.

Os times de frontend e UI já estão trabalhando no layout e nas telas. Para o back-end, as principais funcionalidades a serem implementadas são:

1. **API com rotas implementadas seguindo as boas práticas do modelo REST**;
2. **Validações feitas conforme as regras de negócio**;
3. **Implementação de base de dados para persistência das informações**;
4. **Serviço de autenticação/autorização para restringir acesso às informações**.

## Tecnologias utizadas

1. Java 17
2. Spring
3. Banco de dados PostgreSQL
4. JPA / Hibernate
5. Maven
6. JUnit e Postman para testes
7. Swagger para documentação
8. Heroku para deploy

## Como utilizar
### Heroku deploy
API disponível em: https://money-management-api.herokuapp.com/swagger-ui/index.html

1. Crie um novo usuário
2. Faça a autenticação
3. Copie e cole o token retornado no campo 'Authorize' para liberar as demais rotas.

### Postman

Importar a collection disponível em: https://www.postman.com/collections/db73bdfd8b07fbe9abc6

## Autor
Projeto completo desenvolvido por [Yuri Italo](https://www.linkedin.com/in/yuri-italo/)

