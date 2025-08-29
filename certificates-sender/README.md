<h1 align="center">Certificates Hub: Sender</h1>

<p align="center"><em>Microsserviço responsável pelo envio assíncrono de certificados via email</em></p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-Em%20Testes-9b59b6?style=flat">
  <img src="https://img.shields.io/badge/Java-17-9b59b6?style=flat&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-9b59b6?style=flat&logo=spring-boot&logoColor=white">
  <img src="https://img.shields.io/badge/REST%20API-9b59b6?style=flat&logo=OpenAPI-Initiative&logoColor=white">
  <img src="https://img.shields.io/badge/JUnit5-Testes-9b59b6?style=flat&logo=junit5&logoColor=white">
  <img src="https://img.shields.io/badge/Maven-Build-9b59b6?style=flat&logo=apache-maven&logoColor=white">
</p>

---

## Sumário

* [Visão Geral](#visão-geral)
* [Configuração SMTP](#configuração-smtp)
* [Primeiros Passos](#primeiros-passos)
  * [Pré-requisitos](#pré-requisitos)
  * [Instalação](#instalação)
  * [Uso](#uso)
  * [Testes](#testes)
* [Exemplo de Requisição](#exemplo-de-requisição)

---

## Visão Geral

Este microsserviço é responsável por:

1. Expor um **endpoint RESTful** para recebimento dos dados de envio de certificado: **nome, email e certificado**.
2. Enviar o certificado por email de forma assíncrona para cada participante.

Faz parte do ecossistema **Certificates Hub**.

---

## Configuração SMTP

Este serviço utiliza **SMTP** para envio de emails.
A seguir estão as configurações recomendadas para uso em **produção (Gmail)** e **ambiente de testes (Mailtrap)**.

---

### Produção - Gmail

* Servidor SMTP: `smtp.gmail.com`
* Porta: `587`
* Usuário: `seu-email@gmail.com`
* Senha: senha do aplicativo gerada no Gmail (**não use sua senha da conta diretamente**)

> **Importante:**
>
> * Ative a **verificação em 2 etapas** na sua conta Google.
> * Gere uma **senha de app** para o SMTP em: [https://myaccount.google.com/apppasswords](https://myaccount.google.com/apppasswords)
> * A senha vem com espaços, ex.: `abcd efgh ijkl mnop`.
> * **Remova os espaços** antes de colocar no `application.properties`, ficando assim:

```properties
spring.mail.password=abcdefghijklmnop
```

---

### Testes - Mailtrap

* Servidor SMTP: `sandbox.smtp.mailtrap.io`
* Porta: `2525`
* Usuário e senha: fornecidos pelo Mailtrap na sua conta.
* Acesse: [https://mailtrap.io/home](https://mailtrap.io/home)

#### Exemplo `application-test.properties`

```properties
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=${SMTP_TEST_USERNAME}
spring.mail.password=${SMTP_TEST_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

### Produção - Exemplo `application.properties`

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${SMTP_PROD_USERNAME}
spring.mail.password=${SMTP_PROD_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## Primeiros Passos

### Pré-requisitos

* **Java 17**
* **Maven**
* Conta configurada em servidor **SMTP** (Gmail para produção ou Mailtrap para testes)

---

### Instalação

Clone o repositório:

```bash
❯ git clone https://github.com/nataliatsi/certificates-hub
```

Acesse a pasta do projeto:

```bash
❯ cd certificates-sender
```

Instale as dependências:

```bash
❯ ./mvnw clean install
```

---

### Uso

Inicie o microsserviço:

```bash
❯ ./mvnw spring-boot:run
```

---

### Testes

Rodar os testes com **JUnit 5**:

```bash
❯ ./mvnw test
```

---

## Exemplo de Requisição

Endpoint:

```http
POST /api/v1/certificates/send-email
Content-Type: application/json
```

Body:

```json
{
  "name": "Tanjiro Kamado",
  "email": "tanjiro.kamado@demoncorp.org",
  "certificate": "certificates/Tanjiro_Kamado_certificate.pdf"
}
```

---

<div align="center">

[↑ **Voltar ao topo** 🟪](#-certificates-hub-sender)

</div>

