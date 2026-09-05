# ms-barriodigital-crews

Microservicio encargado de la gestión de cuadrillas, asignaciones y visitas en terreno de **BarrioDigital**.

## Descripción

Este servicio separa la gestión operacional de las cuadrillas del ciclo de vida transaccional del trámite, manteniendo responsabilidades claramente delimitadas.

## Responsabilidades

- Crear y modificar cuadrillas.
- Consultar cuadrillas disponibles.
- Gestionar disponibilidad.
- Asignar una cuadrilla a un trámite.
- Programar visitas.
- Consultar asignaciones.
- Cambiar estado de una visita.
- Procesar tareas asíncronas relacionadas con cuadrillas.

## Stack tecnológico

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- MySQL
- RabbitMQ
- Spring Security
- Eureka Client
- Spring Boot Actuator
- Docker

## Puerto local

```text
8083
```

## Base de datos

```text
barriodigital_crews_db
```

Este servicio es propietario exclusivo de esta base.

## API inicial

### Cuadrillas

```http
GET  /api/v1/crews
GET  /api/v1/crews/{crewId}
POST /api/v1/crews
PUT  /api/v1/crews/{crewId}
```

### Asignaciones

```http
POST  /api/v1/crew-assignments
GET   /api/v1/crew-assignments
GET   /api/v1/crew-assignments/{assignmentId}
PATCH /api/v1/crew-assignments/{assignmentId}/status
```

Ejemplo:

```json
{
  "requestId": "REQ-2026-000001",
  "crewId": "CREW-004",
  "scheduledAt": "2026-09-07T10:00:00Z"
}
```

## Estados iniciales de asignación

```text
SCHEDULED
IN_PROGRESS
COMPLETED
CANCELLED
```

## Comunicación síncrona

Puede ser consumido por:

```text
ms-barriodigital-bff
ms-barriodigital-requests
```

## RabbitMQ

Puede consumir tareas relacionadas con terreno.

Ejemplo:

```text
q.cmd.crew
```

Routing key:

```text
crew.ticket
```

DLQ:

```text
q.cmd.crew.dlq
```

## Seguridad

Roles:

```text
ADMIN
OPERATOR
```

## Buenas prácticas de mensajería

- ACK / NACK.
- DLQ.
- Idempotencia.
- `eventId`.
- `correlationId`.
- `traceId`.

## Observabilidad

- Spring Boot Actuator.
- Logs.
- Health checks.
- Métricas.
- CloudWatch.

## Variables de entorno

```env
SERVER_PORT=8083

MYSQL_HOST=
MYSQL_PORT=3306
MYSQL_DATABASE=barriodigital_crews_db
MYSQL_USER=
MYSQL_PASSWORD=

RABBITMQ_HOST=
RABBITMQ_PORT=5672
RABBITMQ_USER=
RABBITMQ_PASSWORD=

EUREKA_SERVER_URL=
COGNITO_ISSUER_URI=
```

## Contratos

Repositorio:

```text
barriodigital-contracts
```

Contratos:

- OpenAPI.
- AsyncAPI.

## Estructura esperada

```text
src/
├── main/
│   ├── java/
│   │   └── cl/duoc/barriodigital/crews/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── exception/
│   │       ├── messaging/
│   │       ├── repository/
│   │       ├── security/
│   │       └── service/
│   └── resources/
│       └── application.yml
└── test/
```

## Docker

```bash
docker build -t barriodigital/crews:1.0.0 .
```

## CI/CD

GitHub Actions + SonarQube + Snyk + Docker + Amazon ECR.

## Estrategia Git

```text
main
develop
feature/*
fix/*
```

## Estado

🚧 Proyecto en etapa inicial de diseño y construcción.
