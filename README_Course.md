# Microservicio de Gestión de Cursos (Course Management)

## Detalle del Microservicio
Este microservicio se encarga de la gestión de cursos, permitiendo registrar, consultar, actualizar y eliminar cursos. Está diseñado siguiendo una arquitectura hexagonal y utiliza programación reactiva con Spring WebFlux y MongoDB como base de datos.

## Estructura del Proyecto
```
src/main/java/pe/edu/vallegrande/vg_ms_grade_management/
├── domain/
│   ├── model/
│   │   └── Course.java                // Entidad que representa un curso
│   │   └── DatabaseSequence.java      // Entidad para la secuencia de IDs
│   ├── enums/
│   │   └── ... (otros enums del dominio)
│   └── repository/
│       └── CourseRepository.java      // Interfaz para la persistencia de cursos
├── application/
│   ├── service/
│   │   ├── CourseService.java         // Interfaz para los servicios de cursos
│   │   └── SequenceGeneratorService.java // Servicio para generar secuencias de IDs
│   └── impl/
│       └── CourseServiceImpl.java     // Implementación de los servicios de cursos
├── infrastructure/
│   ├── config/
│   │   └── ... (configuraciones específicas de infraestructura)
│   ├── document/
│   │   └── CourseDocument.java        // Documento MongoDB para cursos
│   ├── dto/
│   │   ├── request/
│   │   │   └── CourseRequest.java   // DTO para la creación/actualización de cursos
│   │   └── response/
│   │       └── CourseResponse.java  // DTO para las respuestas de cursos
│   ├── mapper/
│   │   └── CourseMapper.java          // Mapper entre Course y CourseDocument
│   ├── repository/
│   │   └── CourseRepository.java      // Repositorio Spring Data MongoDB para cursos
│   ├── rest/
│   │   └── CourseRest.java            // Controlador REST para los cursos
│   └── service/
│       └── ApiService.java            // Servicio de API genérico
└── VgMsGradeManagementApplication.java  // Clase principal de la aplicación
```

## Documentación de API

### Endpoints de Cursos (`/api/courses`)

| Método | Path                                      | Descripción                                   | JSON de Request (Ejemplo) | JSON de Response (Ejemplo) |
|--------|-------------------------------------------|-----------------------------------------------|---------------------------|----------------------------|
| GET    | `/`                                       | Obtiene todos los cursos                      | N/A                       | `[ { "id": "C001", "name": "Matematicas", "description": "Curso de Matematicas", "deleted": false } ]` |
| GET    | `/{id}`                                   | Obtiene un curso por su ID                    | N/A                       | `{ "id": "C001", "name": "Matematicas", "description": "Curso de Matematicas", "deleted": false }` |
| POST   | `/`                                       | Crea un nuevo curso                           | `{ "name": "Fisica", "description": "Curso de Fisica" }` | `{ "id": "C002", "name": "Fisica", "description": "Curso de Fisica", "deleted": false }` |\
| PUT    | `/{id}`                                   | Actualiza un curso existente                  | `{ "name": "Matematicas Avanzadas", "description": "Curso de Matematicas Avanzadas" }` | `{ "id": "C001", "name": "Matematicas Avanzadas", "description": "Curso de Matematicas Avanzadas", "deleted": false }` |\
| DELETE | `/{id}`                                   | Elimina lógicamente un curso                  | N/A                       | `{ "id": "C001", "name": "Matematicas", "description": "Curso de Matematicas", "deleted": true }` |\
| PUT    | `/{id}/restore`                           | Restaura un curso eliminado lógicamente       | N/A                       | `{ "id": "C001", "name": "Matematicas", "description": "Curso de Matematicas", "deleted": false }` |\
| GET    | `/inactive`                               | Obtiene todos los cursos inactivos (eliminados lógicamente) | N/A                       | `[ { "id": "C003", "name": "Quimica", "description": "Curso de Quimica", "deleted": true } ]` |

## Ejemplos de JSON para Endpoints

### CourseRequest (POST /api/courses, PUT /api/courses/{id})
```json
{
  "name": "Fisica",
  "description": "Curso de Fisica"
}
```

### CourseResponse (GET /api/courses, GET /api/courses/{id}, etc.)
```json
{
  "id": "C002",
  "name": "Fisica",
  "description": "Curso de Fisica",
  "deleted": false
}
```