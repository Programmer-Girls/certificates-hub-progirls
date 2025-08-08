<h1 align="center">Certificates Hub: Data Upload</h1>

<p align="center"><em>Messaging microservice for asynchronous participant data delivery</em></p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-In%20Development-2496ED?style=flat">
  <img src="https://img.shields.io/badge/Java-17-2496ED?style=flat&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-2496ED?style=flat&logo=spring-boot&logoColor=white">
  <img src="https://img.shields.io/badge/RabbitMQ-Queue-2496ED?style=flat&logo=rabbitmq&logoColor=white">
  <img src="https://img.shields.io/badge/REST%20API-Design-2496ED?style=flat&logo=OpenAPI-Initiative&logoColor=white">
  <img src="https://img.shields.io/badge/Swagger-UI-2496ED?style=flat&logo=swagger&logoColor=white">
  <img src="https://img.shields.io/badge/JUnit5-Test-2496ED?style=flat&logo=junit5&logoColor=white">
  <img src="https://img.shields.io/badge/Maven-Build-2496ED?style=flat&logo=apache-maven&logoColor=white">
</p>

---

## Table of Contents

- [Overview](#overview)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Usage](#usage)
    - [Testing](#testing)
- [Endpoint](#endpoint)

---

## Overview

This microservice is responsible for:

1. Receiving a participant file via HTTP `POST /upload/participants` endpoint.
2. Detecting the mapping strategy based on the provided format (`csv`, `json`, etc.).
3. Mapping the file content to internal DTOs.
4. Dispatching the mapped data to a RabbitMQ queue for asynchronous processing.

It is one of the microservices in the **Certificate Hub** ecosystem.

---

## Getting Started

### Prerequisites

This project requires the following dependencies:

- **Programming Language:** Java 17
- **Build Tool:** Maven
- **Queue Broker:** RabbitMQ

---

### Installation

Build `certificates-hub-data-upload` from the source and install dependencies:

Clone the repository:

```bash
❯ git clone https://github.com/nataliatsi/certificates-hub
```

Navigate to the project directory:

```bash
❯ cd certificates-data-upload
```

Install the dependencies:

```bash
❯ ./mvnw clean install
```

### Usage

Run the project with:

```bash
❯ ./mvnw spring-boot:run
```

Then access:

`http://localhost:8081/swagger-ui.html` → Swagger UI

### Testing

certificates-hub-data-upload uses the JUnit 5 test framework. Run the test suite with:

```bash
❯ ./mvnw test
```

---

## Endpoints

| Method | Route                  | Description                                                                                |
|--------|------------------------|--------------------------------------------------------------------------------------------|
| POST   | `/upload/participants` | Receives a file and its format type, maps the data accordingly, and sends it to the queue. |


#### Request

* **Content-Type**: `multipart/form-data`
* **Parameters**:

    * `file`: the file to be processed
    * `type`: format of the file (e.g., `"csv"`, `"json"`)

#### Response

```json
{
  "timestamp": "2025-08-07T15:28:50.5926834",
  "status": 202,
  "message": "Upload received and is being processed."
}
```

---

<div align="center">

[↑ **Back to top**](#certificates-hub-data-upload)

</div>