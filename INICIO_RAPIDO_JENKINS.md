# ⚡ Inicio Rápido - Validación con Jenkins

## 🎯 Objetivo
Validar las 3 pruebas unitarias del microservicio de calificaciones usando Jenkins.

---

## 📋 Antes de Empezar

### ✅ Requisitos
- Java JDK 17+
- Maven 3.8+
- Git (opcional)

### 🔍 Verificar Requisitos

**Windows**:
```cmd
validar-pruebas.bat
```

**Linux/Mac**:
```bash
chmod +x validar-pruebas.sh
./validar-pruebas.sh
```

Si todas las pruebas pasan localmente, continúa con Jenkins.

---

## 🚀 Opción 1: Jenkins con Docker (5 minutos)

### Paso 1: Instalar Docker
```
https://www.docker.com/products/docker-desktop
```

### Paso 2: Ejecutar Jenkins
```cmd
docker run -d --name jenkins -p 8080:8080 -p 50000:50000 jenkins/jenkins:lts
```

### Paso 3: Obtener Contraseña
```cmd
docker logs jenkins
```
Busca: `Please use the following password to proceed to installation`

### Paso 4: Acceder
```
http://localhost:8080
```

### Paso 5: Configurar
1. Pegar contraseña inicial
2. Click en "Install suggested plugins"
3. Crear usuario admin
4. Click en "Save and Finish"

### Paso 6: Configurar Herramientas
```
Manage Jenkins → Global Tool Configuration

JDK:
  Name: JDK-17
  JAVA_HOME: /opt/java/openjdk (en Docker)
  
Maven:
  Name: Maven-3.9
  ☑ Install automatically
  Version: 3.9.5
```

### Paso 7: Crear Job
```
New Item → Pipeline → OK

Pipeline:
  Definition: Pipeline script from SCM
  SCM: Git
  Repository URL: [tu-repositorio]
  Branch: */main
  Script Path: Jenkinsfile
  
Save → Build Now
```

---

## 🖥️ Opción 2: Jenkins Local (15 minutos)

### Paso 1: Descargar Jenkins
```
https://www.jenkins.io/download/
```

### Paso 2: Instalar
- Ejecutar el instalador `.msi` (Windows)
- Seguir el asistente
- Puerto: 8080

### Paso 3: Iniciar
```cmd
net start jenkins
```

### Paso 4: Acceder
```
http://localhost:8080
```

### Paso 5: Obtener Contraseña
```cmd
type "C:\Program Files\Jenkins\secrets\initialAdminPassword"
```

### Paso 6: Configurar
1. Pegar contraseña
2. Install suggested plugins
3. Crear usuario admin

### Paso 7: Configurar Herramientas
```
Manage Jenkins → Global Tool Configuration

JDK:
  Name: JDK-17
  JAVA_HOME: C:\Program Files\Java\jdk-17
  ☐ Install automatically
  
Maven:
  Name: Maven-3.9
  MAVEN_HOME: C:\Program Files\Apache\maven
  ☐ Install automatically
```

### Paso 8: Crear Job
```
New Item
  Name: VG-Grade-Management-Tests
  Type: Pipeline
  OK

Pipeline:
  Definition: Pipeline script from SCM
  SCM: Git
  Repository URL: [tu-repositorio]
  Script Path: Jenkinsfile
  
Save
```

### Paso 9: Ejecutar
```
Build Now
```

---

## ✅ Validar Resultados

### 1. Ver Console Output
```
Click en #1 → Console Output
```

**Debes ver**:
```
╔════════════════════════════════════════════════════════════════╗
║  📝 TEST 1: REGISTRO DE CALIFICACIONES EN LOTE               ║
╚════════════════════════════════════════════════════════════════╝
✅ Calificación 1 registrada: GRD2024101
✅ Calificación 2 registrada: GRD2024102

[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### 2. Ver Test Results
```
Click en #1 → Test Result
```

**Debes ver**:
```
Total: 3
Passed: 3 ✅
Failed: 0
Success Rate: 100%
```

### 3. Ver Detalles
```
Click en cada test:
✅ registerBatchGrades_Success
✅ getClassroomPeriodReport_Success
✅ registerGrade_WithNullObservations_Success
```

---

## 🎯 Checklist de Validación

Marca cada item:

### Configuración
- [ ] Jenkins instalado y accesible
- [ ] JDK 17 configurado
- [ ] Maven configurado
- [ ] Job creado

### Ejecución
- [ ] Build ejecutado
- [ ] Console Output visible
- [ ] Sin errores de compilación

### Resultados
- [ ] 3 tests ejecutados
- [ ] 3 tests pasados (100%)
- [ ] BUILD SUCCESS
- [ ] Tiempo < 10 segundos

### Validaciones MINEDU
- [ ] Test 1: Competencias y capacidades ✅
- [ ] Test 2: Reportes consolidados ✅
- [ ] Test 3: Campos opcionales ✅

---

## 🐛 Problemas Comunes

### ❌ "Maven not found"
```
Manage Jenkins → Global Tool Configuration → Maven
Configurar MAVEN_HOME o marcar "Install automatically"
```

### ❌ "Tests fail in Jenkins"
```groovy
// Agregar al Jenkinsfile:
environment {
    JAVA_TOOL_OPTIONS = '-Dfile.encoding=UTF-8'
}
```

### ❌ "Cannot connect to repository"
```
Manage Jenkins → Manage Credentials
Add Credentials → Username with password
```

### ❌ "JUnit plugin not found"
```
Manage Jenkins → Manage Plugins → Available
Buscar: JUnit
Install without restart
```

---

## 📚 Documentación Completa

Para más detalles, consulta:
- `VALIDACION_JENKINS_PASO_A_PASO.md` - Guía detallada
- `GUIA_JENKINS_INTEGRACION.md` - Guía completa de integración
- `PRUEBAS_UNITARIAS_IMPLEMENTADAS.md` - Detalles de las pruebas
- `Jenkinsfile` - Script del pipeline

---

## 🎉 ¡Listo!

Si completaste el checklist, tus pruebas están validadas con Jenkins.

**Próximos pasos**:
1. Configurar triggers automáticos
2. Agregar más pruebas
3. Integrar con GitHub/GitLab
4. Configurar notificaciones

---

**Tiempo estimado**: 5-15 minutos  
**Dificultad**: Fácil  
**Fecha**: 30/10/2025
