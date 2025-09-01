<h1 align="center">Certificates Hub: ProGirls 👩🏻‍💻💜</h1>

<p align="center"><em>Ecossistema de microsserviços para automação da geração e envio de certificados</em></p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-Concluído-6d28d9?style=flat">
  <img src="https://img.shields.io/badge/Java-17-6d28d9?style=flat&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6d28d9?style=flat&logo=spring-boot&logoColor=white">
  <img src="https://img.shields.io/badge/RabbitMQ-grey?style=flat&logo=rabbitmq&logoColor=white">
  <img src="https://img.shields.io/badge/Docker-6d28d9?style=flat&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/Arquitetura-Microsserviços-6d28d9?style=flat&logo=microservices&logoColor=white">
</p>

---

## Sumário

---

  * [Visão Geral](#visão-geral)
  * [Arquitetura](#arquitetura)
  * [Como Executar](#como-executar)
    * [Pré-requisitos](#pré-requisitos)
    * [Executando com Docker Compose](#executando-com-docker-compose)
    * [Testando a Aplicação](#testando-a-aplicação)
  * [Microsserviços](#microsserviços)

---

## Visão Geral

**Certificates Hub: ProGirls** é um sistema completo para **geração e envio automatizado de certificados digitais**.  
O projeto utiliza **arquitetura de microsserviços** e comunicação assíncrona com **RabbitMQ**, garantindo escalabilidade, modularidade e baixo acoplamento entre os serviços.

Principais responsabilidades do sistema:

1. **Receber arquivos de participantes** (`csv` ou `json`) e colocá-los em uma fila de processamento.
2. **Gerar certificados personalizados** para cada participante.
3. **Enviar os certificados por e-mail** de forma automática.

---

## Arquitetura

O sistema é formado por **3 microsserviços RESTful**, todos em **Spring Boot**:

1. **Data Upload (MS1)**
    - Endpoint: `POST /api/uploads/participants`
    - Recebe arquivos (`csv` ou `json`), mapeia os dados para DTOs e envia para a fila do RabbitMQ.

2. **Certificates Generator (MS2)**
    - Consome os dados da fila.
    - Gera certificados digitais personalizados.
    - Salva localmente e envia (`name`, `email`, `certificate`) via **POST** para o MS3.

3. **Certificates Sender (MS3)**
    - Endpoint: `POST /api/v1/certificates/send-email`
    - Recebe os certificados gerados e dispara o envio por e-mail para cada participante.

Fluxo resumido:

```mermaid
sequenceDiagram
    participant User as Usuário
    participant MS1 as MS1: Data Upload
    participant Queue1 as RabbitMQ - Fila de Participantes
    participant MS2 as MS2: Certificate Generator
    participant MS3 as MS3: Certificate Sender
    participant Email as Servidor de Email

    User->>MS1: POST /api/uploads/participants (arquivo CSV/Excel)
    MS1->>MS1: Escolher estratégia baseada no tipo de arquivo
    MS1->>MS1: Mapear dados para DTOs
    MS1->>Queue1: Publicar mensagem (ParticipantDTO)

    Queue1-->>MS2: Consumir mensagem (ParticipantDTO)
    MS2->>MS2: Preencher template HTML (Thymeleaf)
    MS2->>MS2: Gerar PDF a partir do template
    MS2->>MS3: POST /api/v1/certificates/send-email (PDF anexado)

    MS3->>Email: Enviar certificado por e-mail
    Email-->>User: Certificado entregue na caixa de entrada

````

---

## Como Executar

### Pré-requisitos

Para executar o sistema completo, é necessário ter instalado:

* **Docker**
* **Docker Compose**

### Executando com Docker Compose

Clone o repositório principal:

```bash
❯ git clone https://github.com/nataliatsi/certificates-hub-progirls
```

Entre no diretório:

```bash
❯ cd certificates-hub
```

Suba todos os microsserviços e dependências com um único comando:

```bash
❯ docker compose up --build -d
```

Isso iniciará automaticamente:

* RabbitMQ
* certificates-data-upload
* certificates-generator
* certificates-sender

---

### Testando a Aplicação

Após os serviços estarem rodando, basta acessar no navegador:

👉 [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

No Swagger UI:

1. Faça o **upload** do arquivo de participantes (`csv` ou `json`).
2. Clique em **Enviar**.
3. Pronto 🎉 → o fluxo completo será iniciado automaticamente:

    * Dados vão para a fila RabbitMQ
    * Certificados são gerados
    * Certificados são enviados por e-mail

---

## Microsserviços

Cada microsserviço possui sua própria documentação detalhada em repositórios separados:

* [**Certificates Data Upload**](https://github.com/nataliatsi/certificates-hub-progirls/tree/main/certificates-data-upload) → Recebe e despacha dados de participantes
* [**Certificates Generator**](https://github.com/nataliatsi/certificates-hub-progirls/tree/main/certificates-generator) → Gera certificados a partir da fila
* [**Certificates Sender**](https://github.com/nataliatsi/certificates-hub-progirls/tree/main/certificates-sender) → Envia certificados por e-mail

---

<div align="center">

Made with 💜 by [**Natália**](https://github.com/nataliatsi)

[↑ **Voltar ao topo** ↑](#certificates-hub-progirls)

</div>
