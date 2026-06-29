# 🚀 Enterprise AI-Powered Microservices Platform

An enterprise-grade, distributed microservices application engineered to deliver AI-driven capabilities at scale.

This project demonstrates:

- Scalable backend architecture
- Secure authentication and authorization
- Event-driven processing
- Cloud-ready deployment
- AI integration with modern LLM providers

---

# 📑 Table of Contents

- [Project Overview](#-project-overview)
- [Technology Stack](#-technology-stack)
- [System Architecture](#-system-architecture)
- [Key Features](#-key-features)
- [Security Implementation](#-security-implementation)
- [Getting Started](#-getting-started)
- [API Reference](#-api-reference)
- [Author](#-author)

---

# 📖 Project Overview

This platform is built to handle complex AI processing tasks without compromising speed, scalability, or security.

Using a **Microservices Architecture**, the system provides:

- Independent service deployment
- High availability
- Better scalability
- Fault isolation

Heavy AI processing workloads are decoupled from the main request flow using **Apache Kafka**, enabling asynchronous and reliable background processing.

Security is managed through **Keycloak**, implementing authentication, authorization, and role-based access control using JWT.

---

# 🛠️ Technology Stack

## Backend & Core Frameworks

- Java 17+
- Spring Boot 3.x
- Spring Cloud

Components:

- Spring Cloud Gateway
- Eureka Discovery Server
- Spring Cloud Config Server


## Artificial Intelligence

- Spring AI
- OpenAI Integration
- Ollama Local LLM Support


## Data & Messaging

### Message Broker

- Apache Kafka


### Databases

- PostgreSQL
  - Relational transactional data

- MongoDB
  - AI responses
  - Event logs
  - Unstructured data


### ORM

- Spring Data JPA
- Hibernate


## Security

- Keycloak
- OAuth2
- JWT
- Spring Security


## DevOps & Cloud

- Docker
- Docker Compose

AWS:

- EC2
- S3
- RDS

---

# 🏗️ System Architecture

Client

|
v

API Gateway
(Spring Cloud Gateway)

|

| | |

AI Service User Service Event Service

| | |

Spring AI PostgreSQL Kafka Consumer

| |

OpenAI / Ollama MongoDB

    |
    v

Eureka Discovery Server

    |
    v

Config Server

    |
    v

Keycloak Authentication



---

# ✨ Key Features


## 🔹 Event-Driven Processing

Apache Kafka is used for asynchronous communication between services.

Benefits:

- Non-blocking processing
- Improved scalability
- Reduced API response time
- Better fault tolerance


---

## 🔹 Centralized Configuration

Spring Cloud Config Server manages configurations across all microservices.

Benefits:

- Single configuration source
- Easier environment management
- Secure configuration handling


---

## 🔹 Cloud Native Architecture

The complete infrastructure can be started locally using Docker Compose.

Includes:

- Kafka
- Databases
- Keycloak
- Microservices


The same architecture can be deployed to cloud environments like AWS.

---

## 🔹 Extensible AI Layer

Spring AI provides abstraction over LLM providers.

Supported:

- OpenAI
- Ollama


LLM providers can be switched without changing business logic.

---

# 🔒 Security Implementation


The platform follows a secure authentication flow:



Client

|

Login Request

|

Keycloak Server

|

JWT Token Generated

|

API Gateway

|

Microservice Validation

|

Authorized Request Processing



Security Features:

- JWT authentication
- Role-Based Access Control (RBAC)
- Secure API endpoints
- Stateless authentication


---

# ⚙️ Getting Started


## Prerequisites

Install:

- Java 17+
- Maven 3.8+
- Docker
- Docker Compose


---

# Clone Repository


```bash
git clone https://github.com/your-username/spring-boot-ai-microservices.git

cd spring-boot-ai-microservices
Start Infrastructure

Run:

docker-compose up -d

This initializes:

Kafka
Zookeeper
PostgreSQL
Keycloak
Configure Environment Variables

Add required configurations:

aws.s3.bucket-name=your-s3-bucket

aws.access-key=YOUR_AWS_ACCESS_KEY

aws.secret-key=YOUR_AWS_SECRET_KEY

spring.ai.openai.api-key=YOUR_OPENAI_API_KEY
Build Application
mvn clean install
Run Services

Start services in order:

Discovery Server
Config Server
API Gateway
Business Microservices

Run:

mvn spring-boot:run
📊 API Reference
Service	Endpoint	Method	Description	Access
AI Service	/api/v1/ai/generate	POST	Submit AI processing request	Authenticated
Event Service	/api/v1/events/logs	GET	Retrieve Kafka event logs	Admin
Auth Server	/realms/ai-realm/token	POST	Generate JWT Token	Public
👨‍💻 Author
Dinesh Shivaji Bodhapalle

Java Full Stack Developer

GitHub: https://github.com/so-called-dinesh

LinkedIn: https://www.linkedin.com/in/dineshbodhapalle

Email: bodhapalleds2022@gmail.com
