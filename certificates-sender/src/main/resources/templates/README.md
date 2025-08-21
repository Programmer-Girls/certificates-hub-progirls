<h1 align="center">Certificates Hub: Sender</h1>

<p align="center"><em>Messaging microservice for asynchronous certificate delivery via email</em></p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-In%20Development-4CAF50?style=flat">
  <img src="https://img.shields.io/badge/Java-17-4CAF50?style=flat&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-4CAF50?style=flat&logo=spring-boot&logoColor=white">
  <img src="https://img.shields.io/badge/RabbitMQ-Queue-4CAF50?style=flat&logo=rabbitmq&logoColor=white">
  <img src="https://img.shields.io/badge/REST%20API-Design-4CAF50?style=flat&logo=OpenAPI-Initiative&logoColor=white">
  <img src="https://img.shields.io/badge/JUnit5-Test-4CAF50?style=flat&logo=junit5&logoColor=white">
  <img src="https://img.shields.io/badge/Maven-Build-4CAF50?style=flat&logo=apache-maven&logoColor=white">
</p>

---

## Table of Contents

- [Overview](#overview)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Usage](#usage)
    - [Testing](#testing)

---

## Overview

This microservice is responsible for:

1. Consuming a RabbitMQ queue containing **MessageDTOs** with participant email and certificate link.
2. Mapping the DTO data to the internal email service format.
3. Sending the certificate link via email to each participant asynchronously.

It is one of the microservices in the **Certificate Hub** ecosystem.

---

## Getting Started

### Prerequisites

This project requires the following dependencies:

- **Programming Language:** Java 17
- **Build Tool:** Maven
- **Queue Broker:** RabbitMQ
- **Email Service:** SMTP or any supported email provider

---

### Installation

Build `certificates-hub-sender` from the source and install dependencies:

Clone the repository:

```bash
❯ git clone https://github.com/nataliatsi/certificates-hub
```

Navigate to the project directory:

```bash
❯ cd certificates-sender
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

### Testing

certificates-hub-sender uses the JUnit 5 test framework. Run the test suite with:

```bash
❯ ./mvnw test
```

---

#### Example MessageDTO (from RabbitMQ)

```json
{
  "name": "Kyojuro Rengoku",
  "email": "rengoku@kimetsu.no.yaiba",
  "subject": "Your certificate is ready!",
  "certificateLink": "http://localhost:8080/certificates/12345"
}
```

---

<div align="center">

[↑ **Back to top**](#certificates-hub-sender)

</div>
