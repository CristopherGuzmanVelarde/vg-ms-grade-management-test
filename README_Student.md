# Microservicio de Gestión de Estudiantes (Student Management)

## Detalle del Microservicio
Este microservicio se encarga de la gestión de estudiantes, permitiendo registrar, consultar, actualizar y eliminar estudiantes. Está diseñado siguiendo una arquitectura hexagonal y utiliza programación reactiva con Spring WebFlux y MongoDB como base de datos.

## Estructura del Proyecto
```
src/main/java/pe/edu/vallegrande/vg_ms_grade_management/
├── domain/
│   ├── model/
│   │   └── Student.java               // Entidad que representa un estudiante
│   │   └── DatabaseSequence.java      // Entidad para la secuencia de IDs
│   ├── enums/
│   │   └── ... (otros enums del dominio)
│   └── repository/
│       └── StudentRepository.java     // Interfaz para la persistencia de estudiantes
├── application/
│   ├── service/
│   │   ├── StudentService.java        // Interfaz para los servicios de estudiantes
│   │   └── SequenceGeneratorService.java // Servicio para generar secuencias de IDs
│   └── impl/
│       └── StudentServiceImpl.java    // Implementación de los servicios de estudiantes
├── infrastructure/
│   ├── config/
│   │   └── ... (configuraciones específicas de infraestructura)
│   ├── document/
│   │   └── StudentDocument.java       // Documento MongoDB para estudiantes
│   ├── dto/
│   │   ├── request/
│   │   │   └── StudentRequest.java  // DTO para la creación/actualización de estudiantes
│   │   └── response/
│   │       └── StudentResponse.java // DTO para las respuestas de estudiantes
│   ├── mapper/
│   │   └── StudentMapper.java         // Mapper entre Student y StudentDocument
│   ├── repository/
│   │   └── StudentRepository.java     // Repositorio Spring Data MongoDB para estudiantes
│   ├── rest/
│   │   └── StudentRest.java           // Controlador REST para los estudiantes
│   └── service/
│       └── ApiService.java            // Servicio de API genérico
└── VgMsGradeManagementApplication.java  // Clase principal de la aplicación
```

## Documentación de API

### Endpoints de Estudiantes (`/api/students`)

| Método | Path                                      | Descripción                                   | JSON de Request (Ejemplo) | JSON de Response (Ejemplo) |
|--------|-------------------------------------------|-----------------------------------------------|---------------------------|----------------------------|
| GET    | `/`                                       | Obtiene todos los estudiantes                 | N/A                       | `[ { "id": "S001", "firstName": "Juan", "lastName": "Perez", "deleted": false } ]` |
| GET    | `/{id}`                                   | Obtiene un estudiante por su ID               | N/A                       | `{ "id": "S001", "firstName": "Juan", "lastName": "Perez", "deleted": false }` |
| POST   | `/`                                       | Crea un nuevo estudiante                      | `{ "firstName": "Maria", "lastName": "Gomez" }` | `{ "id": "S002", "firstName": "Maria", "lastName": "Gomez", "deleted": false }` |
| PUT    | `/{id}`                                   | Actualiza un estudiante existente             | `{ "firstName": "Juan", "lastName": "Rodriguez" }` | `{ "id": "S001", "firstName": "Juan", "lastName": "Rodriguez", "deleted": false }` |
| DELETE | `/{id}`                                   | Elimina lógicamente un estudiante             | N/A                       | `{ "id": "S001", "firstName": "Juan", "lastName": "Perez", "deleted": true }` |
| PUT    | `/{id}/restore`                           | Restaura un estudiante eliminado lógicamente  | N/A                       | `{ "id": "S001", "firstName": "Juan", "lastName": "Perez", "deleted": false }` |
| GET    | `/inactive`                               | Obtiene todos los estudiantes inactivos (eliminados lógicamente) | N/A                       | `[ { "id": "S003", "firstName": "Pedro", "lastName": "Lopez", "deleted": true } ]` |

## Ejemplos de JSON para Endpoints

### StudentRequest (POST /api/students, PUT /api/students/{id})
```json
{
  "firstName": "Maria",
  "lastName": "Gomez"
}
```

### StudentResponse (GET /api/students, GET /api/students/{id}, etc.)
```json
{
  "id": "S002",
  "firstName": "Maria",
  "lastName": "Gomez",
  "deleted": false
}
```