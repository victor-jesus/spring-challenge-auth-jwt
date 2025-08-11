
# API movies com auth 

Esse projeto implementa *Spring Security* com auth0, fornecendo endpoints seguros com JWT.
---

## 🚀 Tecnologias utilizadas

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.2-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white"/>
</p>

---

## 🛠 Funcionalidades

| Função                   | Descrição                                    |
| ------------------------ | -------------------------------------------- |
| 👤 Criar usuário         | Cria um novo usuário com senha criptografada |
| 🔑 Autenticar usuário    | Gera JWT Token para acesso                   |
| 🔍 Buscar usuário por ID | Retorna um usuário específico                |
| 🗑 Excluir usuário       | Remove um usuário do sistema                 |
| 📜 Listar filmes       | Retorna filmes(todos, por genero, nome ou diretor) do banco de dados        |
| 💾 Salva filme       | Salva e persiste um filme no banco de dados        |

---

## 📌 Endpoints

### **🔹 Autenticação**

| Método | Endpoint      | Descrição                           | ROLE |
| ------ | ------------- | ----------------------------------- |------|
| `POST` | `/auth/login` | Realiza login e retorna o token JWT | ANY  |
| `POST`   | `/auth/create` | Cria um novo usuário    | ANY |

**Exemplo Request**

```json
{
  "login": "victor",
  "password": "123456"
}
```

```json
{
  "login": "victor",
  "password": "123456",
  "role": "ADMIN"
}
```

**Exemplo Response**

```json
{
  "token": "eyJhbGciOiJIUzI1..."
}
```

---
### 🔹 Users

| Método   | Endpoint        | Descrição               | Role |
| -------- | --------------- | ----------------------- |------|
| `GET`    | `/users`        | Busca todos os usuários | ADMIN |
| `GET`    | `/users/{id}`   | Busca um usuário por ID | ADMIN |
| `DELETE` | `/users/{id}`   | Deleta um usuário       | ADMIN |

---
### 🔹 Movies


| Método   | Endpoint        | Descrição               | Role |
| -------- | --------------- | ----------------------- |------|
| `GET`    | `/movies`        | Lista todos os filmes | ANY |
| `GET`    | `/movies/search`   | Lista por nome | ANY |
| `GET`    | `/movies/search/genre`   | Lista por genero | ANY |
| `GET`    | `/movies/search/director`   | Lista por diretor | ANY |
| `POST` | `/movies`   | Cria um filme e persiste no banco de dados | ADMIN |


**Exemplo Post movie**

```json
{
  "name": "superman",
  "durationInMinutes": 120,
  "genre": "ACTION",
  "directorName": "James Gunn"
}
```

---



---

## 📜 Documentação da API

Acesse o Swagger para explorar e testar os endpoints:

🔗 **`http://localhost:8080/swagger-ui.html`**

---

---

## 🛡 Segurança

* Todas as senhas são **criptografadas com BCrypt**
* A autenticação é baseada em **JWT Tokens**
* Endpoints protegidos exigem envio de **Bearer Token** no `Authorization Header`

---

---

## 💻 Como Executar

```bash
# Clonar o repositório
git clone https://github.com/victor-jesus/spring-challenge-auth-jwt.git

# Entrar na pasta do projeto
cd auth

## Rodar a aplicação
./mvnw spring-boot:run

## Rodar os testes automatizados
./mvnw test

## Gerar o pacote (build)
./mvnw clean package

## Após isso, o .jar estará disponível na pasta target/, podendo ser executado com:
java -jar target/auth-0.0.1-SNAPSHOT.jar

```

---

## 📄 Licença

Este projeto é distribuído sob a licença MIT.

---

## ✍️ Autor

Victor Gustavo
[GitHub: @victor-jesus](https://github.com/victor-jesus)

