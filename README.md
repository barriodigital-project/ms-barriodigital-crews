# ms-barriodigital-crews

Microservicio encargado de la gestión y asignación de cuadrillas de **BarrioDigital**.

## Responsabilidades

- Crear cuadrillas.
- Listar cuadrillas.
- Consultar disponibilidad.
- Asignar cuadrillas a trámites.
- Registrar fecha de visita.
- Mantener estado básico de las asignaciones.

## Stack tecnológico

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- MySQL
- Spring Security
- Eureka Client
- Spring Boot Actuator
- Docker

## Puerto

```text
8083
```

## Base de datos

```text
barriodigital_crews_db
```

## API

```http
GET  /api/v1/crews
POST /api/v1/crews

GET  /api/v1/crew-assignments
POST /api/v1/crew-assignments
```

## Modelos

```text
Crew
├── id
├── name
└── available
```

```text
CrewAssignment
├── id
├── requestId
├── crewId
├── scheduledAt
└── status
```

Estados:

```text
ASIGNADA
EN_TERRENO
COMPLETADA
```

## Integraciones

```text
Requests ──REST──► Crews
BFF      ──REST──► Crews
```

## Seguridad

- JWT.
- Spring Security.
- Amazon Cognito.
- RBAC.

## Observabilidad

- Spring Boot Actuator.
- Logs.
- Health checks.
- Amazon CloudWatch.

## Estructura esperada

```text
src/
├── main/
│   ├── java/
│   │   └── cl/brarriodigital/crews/
│   │       ├── controller/
│   │       ├── dto/
│   │       │   ├── request/
│   │       │   └── response/
│   │       ├── exception/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── CrewsMsApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

## Alcance de la primera etapa

Este primer incremento establece la estructura por capas. La implementación de
los endpoints y reglas de negocio definidos en el contrato se realizará en
incrementos posteriores. No se incorpora mensajería en esta etapa.

## Contrato

```text
barriodigital-contracts/openapi/crews.openapi.yaml
```

## Ejecución

```bash
./mvnw spring-boot:run
```

## Docker

```bash
docker build -t barriodigital/crews:1.0.0 .
```

## Estrategia Git

```text
main
develop
feature/*
fix/*
```

## Estado

🚧 Proyecto en etapa inicial de diseño e implementación.
