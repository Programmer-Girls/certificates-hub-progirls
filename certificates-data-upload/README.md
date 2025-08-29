<h1 align="center">Certificates Hub: Data Upload</h1>

<p align="center"><em>Microsserviço responsável pelo envio assíncrono de dados de participantes</em></p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-Concluído-9b59b6?style=flat">
  <img src="https://img.shields.io/badge/Java-17-9b59b6?style=flat&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-9b59b6?style=flat&logo=spring-boot&logoColor=white">
  <img src="https://img.shields.io/badge/RabbitMQ-Fila-9b59b6?style=flat&logo=rabbitmq&logoColor=white">
  <img src="https://img.shields.io/badge/REST%20API-9b59b6?style=flat&logo=OpenAPI-Initiative&logoColor=white">
  <img src="https://img.shields.io/badge/Swagger-UI-9b59b6?style=flat&logo=swagger&logoColor=white">
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
* [Endpoints](#endpoints)

---

## Visão Geral

Este microsserviço é responsável por:

1. Receber um arquivo de participantes via endpoint HTTP `POST /api/upload/participants`.
2. Detectar a estratégia de mapeamento com base no formato do arquivo (`csv`, `json`, etc.).
3. Mapear o conteúdo do arquivo para DTOs internos.
4. Enviar os dados mapeados para uma fila RabbitMQ para processamento assíncrono.

Faz parte do ecossistema **Certificates Hub**.

---

## Primeiros Passos

### Pré-requisitos

Este projeto requer as seguintes dependências:

* **Java 17**
* **Maven**
* **RabbitMQ**

---

### Instalação

Clone o repositório:

```bash
❯ git clone https://github.com/nataliatsi/certificates-hub
```

Acesse a pasta do projeto:

```bash
❯ cd certificates-data-upload
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

Depois acesse:

`http://localhost:8081/swagger-ui.html` → **Swagger UI**

---

### Testes

O `certificates-hub-data-upload` utiliza **JUnit 5**.
Rode a suíte de testes com:

```bash
❯ ./mvnw test
```

---

## Endpoints

| Método | Rota                   | Descrição                                                                              |
| ------ | ---------------------- | -------------------------------------------------------------------------------------- |
| POST   | `/api/upload/participants` | Recebe um arquivo e seu tipo de formato, mapeia os dados e envia para a fila RabbitMQ. |

### Requisição

* **Content-Type**: `multipart/form-data`
* **Parâmetros**:

  * `file`: arquivo a ser processado
  * `type`: formato do arquivo (ex.: `"csv"`, `"json"`)

### Resposta

```json
{
  "timestamp": "2025-08-07T15:28:50.5926834",
  "status": 202,
  "message": "Upload recebido e está sendo processado."
}
```

---

<div align="center">

[↑ **Voltar ao topo** 🟪](#-certificates-hub-data-upload)

</div>