# Loja de jogos Spring Boot 

- Projeto de loja de jogos com cadastro e verificação de usuário
- O projeto consiste em uma loja de jogos que funciona a partir de itens inseridos em um carrinho
- O objeto de carrinho com os itens selecionados é associado com o objeto pessoa no banco de dados
- Esse projeto NÃO utiliza front end no momento, os testes sao feitos através de requisições no postman 
- O projeto está sendo devidamente organizado para uma possivel implementação de front end no futuro

## Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.4.1**
- **Spring Data JPA**
- **Spring Web**
- **MySQL**
- **Maven**

## Dependências Maven

O projeto utiliza as seguintes dependências definidas no `pom.xml`:

- `spring-boot-starter-data-jpa`: para integração com o banco de dados via JPA/Hibernate
- `spring-boot-starter-web`: para criação da API REST
- `spring-boot-devtools`: para recarregamento automático em desenvolvimento
- `mysql-connector-j`: driver JDBC para conexão com MySQL
- `spring-boot-starter-test`: para testes unitários e de integração
