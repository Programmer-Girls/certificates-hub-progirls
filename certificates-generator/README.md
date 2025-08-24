<h1 align="center">Certificates Hub: Generator</h1>

<p align="center"><em>Messaging microservice for asynchronous certificate generation and PDF creation</em></p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-In%20Testing-9b59b6?style=flat">
  <img src="https://img.shields.io/badge/Java-17-9b59b6?style=flat&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-9b59b6?style=flat&logo=spring-boot&logoColor=white">
  <img src="https://img.shields.io/badge/RabbitMQ-Queue-9b59b6?style=flat&logo=rabbitmq&logoColor=white">
  <img src="https://img.shields.io/badge/PDF%20Generation-OpenPDF-9b59b6?style=flat">
  <img src="https://img.shields.io/badge/Template-HTML%2FThymeleaf-9b59b6?style=flat">
  <img src="https://img.shields.io/badge/JUnit5-Test-9b59b6?style=flat&logo=junit5&logoColor=white">
  <img src="https://img.shields.io/badge/Maven-Build-9b59b6?style=flat&logo=apache-maven&logoColor=white">
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

1. Creating and consuming a RabbitMQ queue where **MessageDTOs** are published by the **certificates-hub-upload** (MS1).
2. Reading participant data (name, email, and certificate link) from the queue.
3. Generating a PDF certificate for each participant and saving it locally.
4. Sending the access link of the generated PDF to be published in another RabbitMQ queue for the **certificates-hub-sender** (MS3).

> ⚠️ This microservice does **not** expose any REST endpoints. It only works asynchronously with RabbitMQ messaging.

---

## Getting Started

### Prerequisites

This project requires the following dependencies:

- **Programming Language:** Java 17
- **Build Tool:** Maven
- **Queue Broker:** RabbitMQ
- **PDF Generation Library:** OpenPDF (or similar)

---

### Installation

Build `certificates-hub-generator` from the source and install dependencies:

Clone the repository:

```bash
❯ git clone https://github.com/nataliatsi/certificates-hub
````

Navigate to the project directory:

```bash
❯ cd certificates-generator
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

The service will automatically create and consume its queue, generating PDF certificates locally and publishing access links to the next queue.

### Testing

certificates-hub-generator uses the JUnit 5 test framework. Run the test suite with:

```bash
❯ ./mvnw test
```

---

#### Example MessageDTO (from RabbitMQ)

```json
{
  "participantName": "Tanjiro Kamado",
  "participantEmail": "tanjiro.kamado@demoncorp.org",
  "certificateLink": "certificates/Tanjiro_Kamado_certificate.pdf"
}
```

---

<div align="center">

[↑ **Back to top**](#certificates-hub-generator)

</div>
