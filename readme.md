# Patrimony Api Manager

### Introdução

Este projeto tem o intuito de apresentar a solução dos requisitos levantados no exercicio numero 2 do teste da navita.

### Descrição

Para esse projeto foi utilizado o Spring boot + Postgresql + Jwt.

### Pre Requisitos

	- Java 11
	- Docker & Docker-compose

### Iniciando o projeto
```shellscript
git clone https://github.com/MAAARKIN/navita-api.git

cd navita-api

#windows setup
.\mvnw.cmd clean package
docker-compose up

#linux setup
./mvnw clean package
docker-compose up
```

A aplicação será disponibilizara através da porta 8080, para ter acesso aos serviços disponibilizados da api é possível acessar a url http://localhost:8080/swagger-ui.html

### Autenticação

Para o cadastro de novos acessos, foi disponibilizado o endpoint /auth/signup que poderá ser vista no Swagger, e para se autenticar na aplicação utilizar o endpoint /auth/signin.

Vale salientar que esses são os unicos endpoints sem proteção.

Todos os outros necessitam da autenticação via JWT, o token será disponibilizado no /signin e com esse token é possível através do swagger-ui consumir os serviços já com autenticação. Basta clicar na opção Authorize e no campo disponível utilizar o seguinte padrão "Bearer + token"

Exemplo:

```
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtLmZpbGhvd0BnbWFpbC5jb20iLCJleHAiOjE1OTcwMTM0MTksImlhdCI6MTU5Njk5NTQxOX0.zf6JFxWf07jEMxDL_LnRDlrKpLdZE4K1R0FBmHrdFswjO_u5P-OxqEejvtSWOZzRaFcCQdVziDPEs4l5F1oOIg
```

ou via algum client rest, basta utilizar esse valor no Header Authorization com o padrão "Authorization: Bearer + Token"

vale salientar que entre a palavra Bearer e o Token, contem um espaço.