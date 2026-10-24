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
- Lombok
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

Al ejecutar desde esta carpeta, Spring carga `DB_PASSWORD` desde el archivo
compartido `../.env` del workspace. Ese archivo local no se versiona.

## API

```http
GET  /api/v1/crews
POST /api/v1/crews

GET  /api/v1/crew-assignments
POST /api/v1/crew-assignments
```

```bash
curl -X POST http://localhost:8083/api/v1/crews `
  -H "Content-Type: application/json" `
  -d '{"name":"Cuadrilla Norte"}'
```

La cuadrilla nueva queda disponible (`available: true`) si no se indica el
campo.

Los listados se ordenan por nombre para cuadrillas y por fecha de agenda para
asignaciones. Estas últimas se pueden filtrar por `requestId`, `crewId` o
ambos. Crear una asignación requiere una cuadrilla existente y disponible;
una cuadrilla marcada como no disponible produce HTTP 409. El contrato no
define cambio de disponibilidad ni transición de estado, por lo que una
asignación no modifica automáticamente `Crew.available`.

```bash
curl "http://localhost:8083/api/v1/crew-assignments?requestId=00000000-0000-0000-0000-000000000001"
```

```json
[
  {
    "id": "6dcc94c3-d355-4ff4-9cb2-52e15952ffdc",
    "requestId": "00000000-0000-0000-0000-000000000001",
    "crewId": "796426f7-c60b-4ab1-a23c-dcd2d8df80fe",
    "scheduledAt": "2026-06-01T10:00:00Z",
    "status": "ASIGNADA"
  }
]
```

## Modelos

```text
Crew
├── id
├── name
├── available
└── assignments (1:N)
```

```text
CrewAssignment
├── id
├── requestId
├── crew (N:1, FK crew_id)
├── scheduledAt
└── status
```

Una `Crew` puede tener muchas asignaciones y cada `CrewAssignment` pertenece a
una única cuadrilla. La relación JPA se gestiona con `Crew.assignments`
(`@OneToMany`) y `CrewAssignment.crew` (`@ManyToOne`); `crew_id` es la clave
foránea persistida. La API mantiene `crewId` en las solicitudes y respuestas.

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

El BFF/gateway autentica al usuario y valida permisos. Este servicio no valida
tokens y debe aceptar solo tráfico interno; no exponer directamente el puerto
`8083`.

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

Se implementan los endpoints definidos en OpenAPI, el modelo JPA, persistencia,
validaciones y manejo de errores. Este servicio no usa mensajería.

## Configuración local

El servicio escucha en `localhost:8083` y usa MySQL en `localhost:3306` por
defecto. Se pueden sobrescribir `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`
y `DB_PASSWORD`. `JPA_DDL_AUTO` controla el DDL de Hibernate (por defecto
`update`; usar migraciones controladas en producción). Actuator expone
`health`, `info` y `metrics` bajo `/actuator`.

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
