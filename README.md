# Projeto Funcionário

## 📌 Resumo
Este projeto é uma **API RESTful** desenvolvida para gerenciar **projetos** e **funcionários**, onde:
- Cada **projeto** pode ter vários funcionários.
- Cada **funcionário** pode participar de vários projetos.

Foram implementadas operações de **CRUD completo (Create, Read, Update, Delete)**:
- Criar funcionário e projeto (**POST**).
- Listar funcionários e projetos, incluindo seus vínculos (**GET**).
- Atualizar dados de funcionário e projeto (**PUT**).
- Excluir funcionário e projeto (**DELETE**).

---

## 🚀 Tecnologias utilizadas
- **Java 17** → linguagem base do projeto.  
- **Spring Boot 3.5.5** → framework principal para criação da API.  
- **Spring Web (REST Controller)** → expõe endpoints RESTful.  
- **Spring Data JPA / Hibernate** → ORM para persistência em banco relacional.  
- **PostgreSQL** → banco de dados relacional utilizado.  
- **Flyway** → versionamento e migração de schema do banco.  
- **Lombok** → reduz boilerplate (getters, setters, builders).  
- **Swagger / OpenAPI 3** → documentação e testes interativos da API.  
- **JUnit 5 + Mockito** → testes unitários e integrados (Service e Controller).  
- **Maven** → gerenciamento de dependências e build.  

---

## 📂 Estrutura do projeto
- `controllers/` → classes REST Controllers (`FuncionarioController`, `ProjetoController`).  
- `service/` → regras de negócio (`FuncionarioService`, `ProjetoService`).  
- `repository/` → interfaces JPA (`FuncionarioRepository`, `ProjetoRepository`).  
- `model/` → entidades JPA (`Funcionario`, `Projeto`).  
- `dto/` → objetos de transferência de dados (Request e Response).  
- `mapper/` → conversão entre entidades e DTOs.  
- `resources/db/migration` → scripts SQL gerenciados pelo Flyway.  
- `resources/postman/` → coleção do Postman (`projeto-funcionario.postman_collection.json`).  

---

## 🗄️ Banco de Dados

### Criando o banco no PostgreSQL
```sql
CREATE DATABASE projeto_funcionario;
```

### Configuração no `application.properties`
```
spring.datasource.url=jdbc:postgresql://localhost:5432/projeto_funcionario
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

---

## ▶️ Como rodar a aplicação

### 1. Clonar o repositório
```
git clone https://github.com/cspassos/projeto_funcionario_backend.git
cd projeto-funcionario
```

### 2. Rodar com Maven
```
./mvnw spring-boot:run
```

### 3. Rodar pela IDE (Eclipse/IntelliJ/STS)
- Importar o projeto na IDE.  
- Rodar a classe principal:  
```
ProjetoFuncionarioApplication
```

---

## 📖 Documentação da API (Swagger)
Após rodar a aplicação, a documentação interativa pode ser acessada em:  
👉 http://localhost:8080/swagger-ui.html  

No Swagger é possível:
- Visualizar todos os endpoints (`GET`, `POST`, `PUT`, `DELETE`).  
- Enviar requisições diretamente do navegador.  
- Ver exemplos de **Request** e **Response**.  

---

## 📬 Testando com Postman
Na pasta `src/main/resources/postman` está disponível a coleção:  
```
projeto-funcionario.postman_collection.json
```
Basta importar no Postman e já terá todos os endpoints configurados para teste.

---

## 🧪 Testes

### Rodar testes
```
./mvnw test
```
Ou na IDE: **Botão direito > Run As > Maven Test**  

### Relatório de cobertura (Jacoco)
Após rodar os testes, o relatório será gerado em:  
```
target/site/jacoco/index.html
```

---

## 📌 Endpoints principais

### Funcionários
- POST `/api/funcionarios`  
- GET `/api/funcionarios`  
- GET `/api/funcionarios/{id}`  
- GET `/api/funcionarios/{id}/projetos`  
- GET `/api/funcionarios/{id}/projetos-resumo`  
- PUT `/api/funcionarios/{id}`  
- DELETE `/api/funcionarios/{id}`  

### Projetos
- POST `/api/projetos`  
- GET `/api/projetos`  
- GET `/api/projetos/{id}`  
- GET `/api/projetos/{id}/funcionarios`  
- PUT `/api/projetos/{id}`  
- DELETE `/api/projetos/{id}`  

---

## ✅ Conclusão
Este projeto demonstra:
- Implementação de CRUD completo seguindo **padrão RESTful**.  
- Estrutura em camadas: Controller, Service, Repository e DTOs.  
- Migração de banco com **Flyway**.  
- Documentação automática com **Swagger**.  
- Testes unitários e de integração com **JUnit + Mockito**.  
- Relatório de cobertura com **Jacoco**.  
- Coleção **Postman** incluída para facilitar os testes.  
