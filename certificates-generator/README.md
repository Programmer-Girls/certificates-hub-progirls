<h1 align="center">Certificates Hub: Generator</h1>

<p align="center"><em>Microsserviço responsável pela geração assíncrona de certificados e criação de PDFs</em></p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-Em%20Testes-9b59b6?style=flat">
  <img src="https://img.shields.io/badge/Java-17-9b59b6?style=flat&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-9b59b6?style=flat&logo=spring-boot&logoColor=white">
  <img src="https://img.shields.io/badge/PDF-Flying%20Saucer-9b59b6?style=flat">
  <img src="https://img.shields.io/badge/Template-Thymeleaf-9b59b6?style=flat&logo=thymeleaf&logoColor=white">
  <img src="https://img.shields.io/badge/JUnit5-Testes-9b59b6?style=flat&logo=junit5&logoColor=white">
  <img src="https://img.shields.io/badge/Maven-Build-9b59b6?style=flat&logo=apache-maven&logoColor=white">
</p>

---

## Sumário

* [Visão Geral](#visão-geral)
* [Primeiros Passos](#primeiros-passos)
  * [Pré-requisitos](#pré-requisitos)
  * [Instalação](#instalação)
  * [Uso](#uso)
  * [Testes](#testes)
* [Exemplo de fluxo](#exemplo-de-fluxo)

---

## Visão Geral

Este microsserviço é responsável por:

1. Consumir uma fila RabbitMQ onde o **certificates-hub-upload** (MS1) publica os **ParticipantDTO** com dados dos participantes.
2. Ler os dados do participante (nome e email).
3. Gerar o **PDF do certificado** com **Thymeleaf + Flying Saucer** e salvar localmente.
4. Realizar uma **requisição POST** para o microsserviço **certificates-hub-sender** (MS3), enviando os dados do participante e anexando o certificado gerado.

> ⚠️ Este microsserviço **não expõe endpoints REST**. Ele atua de forma assíncrona consumindo mensagens do RabbitMQ e utiliza `RestTemplate` para comunicação com o MS3.

---

## Primeiros Passos

### Pré-requisitos

Este projeto requer as seguintes dependências:

* **Java 17**
* **Maven**
* **RabbitMQ**
* **Thymeleaf** para renderização de templates HTML
* **Flying Saucer (v9.12.0)** para geração de PDFs

---

### Instalação

Clone o repositório:

```bash
❯ git clone https://github.com/nataliatsi/certificates-hub
```

Acesse a pasta do projeto:

```bash
❯ cd certificates-generator
```

Instale as dependências:

```bash
❯ ./mvnw clean install
```

---

### Uso

Execute o projeto com:

```bash
❯ ./mvnw spring-boot:run
```

O serviço irá:

* Consumir os participantes da fila configurada no RabbitMQ.
* Gerar os certificados em PDF localmente.
* Chamar via **`RestTemplate`** o endpoint do **MS3 (Sender)** para disparar o email.

---

### Testes

O `certificates-hub-generator` utiliza **JUnit 5**.
Rode a suíte de testes com:

```bash
❯ ./mvnw test
```

---

## Exemplo de fluxo

### Mensagem recebida do RabbitMQ (entrada MS2):

```json
{
  "participantName": "Tanjiro Kamado",
  "participantEmail": "tanjiro.kamado@demoncorp.org"
}
```

### Requisição enviada pelo MS2 para o MS3 (`POST /api/v1/certificates/send`):

```json
{
  "name": "Tanjiro Kamado",
  "email": "tanjiro.kamado@demoncorp.org",
  "certificate": "certificates/Tanjiro_Kamado_certificate.pdf"
}
```

---

<div align="center">

[↑ **Voltar ao topo** 🟪](#-certificates-hub-generator)

</div>