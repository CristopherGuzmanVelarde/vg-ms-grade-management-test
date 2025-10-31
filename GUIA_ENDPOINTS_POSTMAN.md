# 📋 Guía de Endpoints - Grade Management API v8.0

## 🎯 Colección Completa de Postman

### Headers Obligatorios para Todos los Endpoints
```
X-User-Id: 550e8400-e29b-41d4-a716-446655440000
X-User-Roles: teacher
X-Institution-Id: 660e8400-e29b-41d4-a716-446655440000
```

---

## 📍 Endpoints Disponibles

### 1️⃣ Registrar Calificación Individual
**Método:** `POST`  
**URL:** `http://localhost:8104/api/v1/grades/teacher/register-grade`

**Body:**
```json
{
  "studentId": "student-uuid-123",
  "courseId": "course-uuid-mat",
  "classroomId": "classroom-uuid-001",
  "periodId": "period-uuid-001",
  "typePeriod": "III_TRIMESTRE",
  "competenceName": "Resuelve problemas de cantidad",
  "capacityEvaluated": "Traduce cantidades a expresiones numéricas",
  "gradeScale": "A",
  "numericGrade": 16.5,
  "evaluationType": "FORMATIVA",
  "evaluationDate": "2025-10-28",
  "observations": "Muy buen desempeño en operaciones con fracciones"
}
```

**Respuesta Exitosa:**
```json
{
  "message": "Calificación registrada exitosamente",
  "grade": {
    "id": "...",
    "status": "A",
    ...
  }
}
```

---

### 2️⃣ Registrar Calificaciones en Lote
**Método:** `POST`  
**URL:** `http://localhost:8104/api/v1/grades/teacher/register-batch-grades`

**Body:**
```json
{
  "grades": [
    {
      "studentId": "student-uuid-001",
      "courseId": "course-uuid-mat",
      "classroomId": "classroom-uuid-001",
      "periodId": "period-uuid-001",
      "typePeriod": "III_TRIMESTRE",
      "competenceName": "Resuelve problemas de cantidad",
      "capacityEvaluated": "Traduce cantidades a expresiones numéricas",
      "gradeScale": "A",
      "numericGrade": 16.0,
      "evaluationType": "SUMATIVA",
      "evaluationDate": "2025-10-28"
    },
    {
      "studentId": "student-uuid-002",
      ...
    }
  ]
}
```

---

### 3️⃣ Actualizar Calificación ✨ (Mejorado)
**Método:** `PUT`  
**URL:** `http://localhost:8104/api/v1/grades/teacher/update-grade/{gradeId}`

**Path Variable:**
- `gradeId`: ID de la calificación (ej: `grade-uuid-001`)

**Body:**
```json
{
  "studentId": "student-uuid-123",
  "courseId": "course-uuid-mat",
  "classroomId": "classroom-uuid-001",
  "periodId": "period-uuid-001",
  "typePeriod": "III_TRIMESTRE",
  "competenceName": "Resuelve problemas de cantidad",
  "capacityEvaluated": "Traduce cantidades a expresiones numéricas",
  "gradeScale": "AD",
  "numericGrade": 18.5,
  "evaluationType": "SUMATIVA",
  "evaluationDate": "2025-10-28",
  "observations": "Excelente desempeño, superó las expectativas"
}
```

**⚠️ CAMBIOS IMPORTANTES:** 
- Los campos **studentId, courseId, classroomId, periodId, typePeriod** son **requeridos por validación** pero **NO se actualizan** (son IDs de referencia que definen la identidad de la calificación)
- Los campos que **SÍ se actualizan** son:
  - `competenceName` - Nombre de la competencia
  - `capacityEvaluated` - Capacidad evaluada
  - `gradeScale` - Escala de calificación (AD, A, B, C)
  - `numericGrade` - Nota numérica (0-20)
  - `evaluationType` - Tipo de evaluación (FORMATIVA, SUMATIVA, DIAGNOSTICA)
  - `evaluationDate` - Fecha de evaluación
  - `observations` - Observaciones del teacher
- El estado se mantiene en `A` (Active) - no cambia a `M` (Modified)
- Solo el teacher propietario puede actualizar sus calificaciones

---

### 4️⃣ Ver Mis Calificaciones
**Método:** `GET`  
**URL:** `http://localhost:8104/api/v1/grades/teacher/my-grades`

**Respuesta:**
```json
{
  "message": "Mis calificaciones obtenidas exitosamente",
  "total_grades": 25,
  "grades": [...]
}
```

---

### 5️⃣ Reporte Consolidado por Aula
**Método:** `GET`  
**URL:** `http://localhost:8104/api/v1/grades/teacher/classroom/{classroomId}/period/{periodId}/type/{typePeriod}/report`

**Path Variables:**
- `classroomId`: classroom-uuid-001
- `periodId`: period-uuid-001
- `typePeriod`: III_TRIMESTRE

**Tipos de Período Válidos:**
- `I_BIMESTRE`, `II_BIMESTRE`, `III_BIMESTRE`, `IV_BIMESTRE`
- `I_TRIMESTRE`, `II_TRIMESTRE`, `III_TRIMESTRE`
- `I_SEMESTRE`, `II_SEMESTRE`

---

### 6️⃣ Historial de Estudiante
**Método:** `GET`  
**URL:** `http://localhost:8104/api/v1/grades/teacher/student/{studentId}/grades`

**Path Variable:**
- `studentId`: student-uuid-123

**Respuesta:**
```json
{
  "message": "Historial de estudiante obtenido exitosamente",
  "student_id": "student-uuid-123",
  "total_grades": 12,
  "grades": [...]
}
```

---

### 7️⃣ Eliminar Calificación (Lógico) 🆕
**Método:** `DELETE`  
**URL:** `http://localhost:8104/api/v1/grades/teacher/delete-grade/{gradeId}`

**Path Variable:**
- `gradeId`: grade-uuid-001

**Descripción:**
- Realiza eliminación lógica (estado → `I`)
- No elimina el registro de la base de datos
- Solo el teacher propietario puede eliminar
- Valida que no esté ya inactiva

**Respuesta Exitosa:**
```json
{
  "message": "Calificación eliminada lógicamente exitosamente",
  "grade": {
    "id": "grade-uuid-001",
    "status": "I",
    "updatedAt": "2025-10-29T..."
  }
}
```

**Errores Posibles:**
```json
{
  "error": "No tiene permiso para eliminar esta calificación"
}
```
```json
{
  "error": "La calificación ya está inactiva"
}
```

---

### 8️⃣ Restaurar Calificación 🆕
**Método:** `PATCH`  
**URL:** `http://localhost:8104/api/v1/grades/teacher/restore-grade/{gradeId}`

**Path Variable:**
- `gradeId`: grade-uuid-001

**Descripción:**
- Restaura una calificación eliminada
- Cambia estado de `I` (Inactive) → `A` (Active)
- Solo el teacher propietario puede restaurar
- Valida que esté realmente inactiva

**Respuesta Exitosa:**
```json
{
  "message": "Calificación restaurada exitosamente",
  "grade": {
    "id": "grade-uuid-001",
    "status": "A",
    "updatedAt": "2025-10-29T..."
  }
}
```

**Errores Posibles:**
```json
{
  "error": "No tiene permiso para restaurar esta calificación"
}
```
```json
{
  "error": "La calificación no está inactiva, no se puede restaurar"
}
```

---

## 🔄 Flujo Completo de Testing

### Escenario 1: Ciclo de Vida Normal
```
1. POST /register-grade                    → Crea calificación (Status: A)
2. PUT /update-grade/{id}                  → Actualiza (Status: A, se mantiene)
3. GET /my-grades                          → Aparece en la lista
```

### Escenario 2: Eliminación y Restauración
```
1. POST /register-grade                    → Crea calificación (Status: A)
2. DELETE /delete-grade/{id}               → Elimina lógicamente (Status: I)
3. GET /my-grades                          → NO aparece (solo muestra activas)
4. PATCH /restore-grade/{id}               → Restaura (Status: A)
5. GET /my-grades                          → Aparece nuevamente
```

### Escenario 3: Validación de Permisos
```
1. Teacher A crea calificación
2. Teacher B intenta eliminar              → Error: Sin permiso
3. Teacher A elimina exitosamente          → OK
4. Teacher B intenta restaurar             → Error: Sin permiso
5. Teacher A restaura exitosamente         → OK
```

---

## 📊 Escalas de Calificación MINEDU

### Nivel Inicial y Primaria
- `AD` - Logro Destacado
- `A` - Logro Esperado
- `B` - En Proceso
- `C` - En Inicio

### Nivel Secundaria (Opcional Numérico)
- `numericGrade`: 0-20 puntos
- Escalas: `AD`, `A`, `B`, `C`

### Tipos de Evaluación
- `DIAGNOSTICA` - Evaluación inicial
- `FORMATIVA` - Evaluación continua
- `SUMATIVA` - Evaluación final

---

## 🛡️ Seguridad y Validaciones

### Headers Requeridos
- ✅ `X-User-Id` - ID del teacher (UUID)
- ✅ `X-User-Roles` - Debe contener "teacher"
- ✅ `X-Institution-Id` - ID de la institución

### Validaciones Automáticas
- ✅ Teacher solo puede modificar/eliminar/restaurar sus propias calificaciones
- ✅ No se permite eliminar calificaciones ya inactivas
- ✅ No se permite restaurar calificaciones ya activas
- ✅ Todas las operaciones actualizan `updatedAt`

---

## 📁 Importar en Postman

1. Abrir Postman
2. Click en **Import**
3. Seleccionar el archivo `grade_collection.json`
4. La colección aparecerá con todos los endpoints configurados

### Variables de Entorno Sugeridas
```
baseUrl = http://localhost:8104
userId = 550e8400-e29b-41d4-a716-446655440000
userRoles = teacher
institutionId = 660e8400-e29b-41d4-a716-446655440000
```

---

## 🚀 Puerto del Microservicio
```
http://localhost:8104
```

Asegúrate de que el microservicio esté ejecutándose antes de probar los endpoints.
