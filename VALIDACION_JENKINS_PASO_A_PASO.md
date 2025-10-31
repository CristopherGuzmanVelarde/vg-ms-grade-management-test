# 🚀 Validación de Pruebas Unitarias con Jenkins - Guía Rápida

## 📋 Índice
1. [Opción A: Jenkins Local (Recomendado para desarrollo)](#opción-a-jenkins-local)
2. [Opción B: Jenkins con Docker (Más rápido)](#opción-b-jenkins-con-docker)
3. [Configuración del Pipeline](#configuración-del-pipeline)
4. [Validación de Resultados](#validación-de-resultados)

---

## 🎯 Opción A: Jenkins Local

### Paso 1: Instalar Jenkins

#### Windows:
```cmd
# Descargar Jenkins desde:
https://www.jenkins.io/download/

# Instalar el archivo .msi
# Jenkins se instalará en: C:\Program Files\Jenkins

# Iniciar Jenkins
net start jenkins

# Acceder a Jenkins
http://localhost:8080
```

#### Obtener contraseña inicial:
```cmd
type "C:\Program Files\Jenkins\secrets\initialAdminPassword"
```

### Paso 2: Configuración Inicial

1. **Desbloquear Jenkins**:
   - Pegar la contraseña inicial
   - Click en "Continue"

2. **Instalar Plugins**:
   - Seleccionar "Install suggested plugins"
   - Esperar a que termine la instalación

3. **Crear Usuario Admin**:
   - Username: `admin`
   - Password: `admin123` (o el que prefieras)
   - Full name: `Administrador`
   - Email: `admin@localhost`

### Paso 3: Configurar Herramientas

1. **Ir a**: `Manage Jenkins` → `Global Tool Configuration`

2. **Configurar JDK**:
   ```
   Name: JDK-17
   JAVA_HOME: C:\Program Files\Java\jdk-17
   ☐ Install automatically (desmarcar)
   ```

3. **Configurar Maven**:
   ```
   Name: Maven-3.9
   MAVEN_HOME: C:\Program Files\Apache\maven
   ☐ Install automatically (desmarcar)
   ```
   
   **Si no tienes Maven instalado**:
   ```cmd
   # Descargar Maven desde:
   https://maven.apache.org/download.cgi
   
   # Extraer en: C:\Program Files\Apache\maven
   
   # Agregar al PATH:
   C:\Program Files\Apache\maven\bin
   ```

4. **Configurar Git**:
   ```
   Name: Default
   Path: C:\Program Files\Git\bin\git.exe
   ```

### Paso 4: Instalar Plugins Necesarios

1. **Ir a**: `Manage Jenkins` → `Manage Plugins` → `Available`

2. **Buscar e instalar**:
   - ✅ JUnit Plugin
   - ✅ JaCoCo Plugin
   - ✅ Pipeline Plugin
   - ✅ Git Plugin

3. **Click en**: `Install without restart`

### Paso 5: Crear el Job

1. **En Dashboard**: Click en `New Item`

2. **Configurar**:
   ```
   Item name: VG-Grade-Management-Tests
   Type: Pipeline
   ```

3. **Click en**: `OK`

### Paso 6: Configurar el Pipeline

1. **En la sección Pipeline**:
   - Definition: `Pipeline script from SCM`
   - SCM: `Git`
   - Repository URL: `[URL de tu repositorio]`
   - Branch: `*/main` (o tu rama principal)
   - Script Path: `Jenkinsfile`

   **O si prefieres copiar el script directamente**:
   - Definition: `Pipeline script`
   - Copiar el contenido del archivo `Jenkinsfile` del proyecto

2. **Click en**: `Save`

### Paso 7: Ejecutar las Pruebas

1. **Click en**: `Build Now`

2. **Ver progreso**: Click en el número del build (ej: `#1`)

3. **Ver logs**: Click en `Console Output`

### Paso 8: Ver Resultados

**Resultado esperado en Console Output**:
```
╔════════════════════════════════════════════════════════════════╗
║  📝 TEST 1: REGISTRO DE CALIFICACIONES EN LOTE               ║
╚════════════════════════════════════════════════════════════════╝
✅ Calificación 1 registrada: GRD2024101
✅ Calificación 2 registrada: GRD2024102
✨ TEST 1 COMPLETADO

╔════════════════════════════════════════════════════════════════╗
║  📊 TEST 2: REPORTE CONSOLIDADO POR AULA Y PERÍODO          ║
╚════════════════════════════════════════════════════════════════╝
📋 Reporte generado con 2 cursos
✅ Validación: 2 cursos agrupados correctamente
✨ TEST 2 COMPLETADO

╔════════════════════════════════════════════════════════════════╗
║  🔍 TEST 3: MANEJO DE CAMPOS OPCIONALES (NULL)              ║
╚════════════════════════════════════════════════════════════════╝
✅ Calificación registrada: GRD2024303
✅ Observaciones: null (OK)
✨ TEST 3 COMPLETADO

[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 🐳 Opción B: Jenkins con Docker (Más Rápido)

### Paso 1: Instalar Docker

```cmd
# Descargar Docker Desktop desde:
https://www.docker.com/products/docker-desktop

# Instalar y reiniciar el sistema
```

### Paso 2: Ejecutar Jenkins en Docker

```cmd
# Crear volumen para persistencia
docker volume create jenkins_home

# Ejecutar Jenkins
docker run -d ^
  --name jenkins ^
  -p 8080:8080 ^
  -p 50000:50000 ^
  -v jenkins_home:/var/jenkins_home ^
  -v /var/run/docker.sock:/var/run/docker.sock ^
  jenkins/jenkins:lts

# Ver logs y obtener contraseña inicial
docker logs jenkins
```

### Paso 3: Acceder a Jenkins

```
http://localhost:8080
```

### Paso 4: Configurar (igual que Opción A)

Seguir los pasos 2-8 de la Opción A.

---

## 📊 Configuración del Pipeline

### Opción 1: Pipeline desde SCM (Recomendado)

Tu proyecto ya tiene el archivo `Jenkinsfile` en la raíz. Solo necesitas:

1. Subir el código a Git:
   ```bash
   git add .
   git commit -m "Add Jenkins pipeline"
   git push origin main
   ```

2. En Jenkins, configurar:
   - SCM: Git
   - Repository URL: Tu repositorio
   - Script Path: `Jenkinsfile`

### Opción 2: Pipeline Script Directo

Copiar este script en Jenkins:

```groovy
pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }
    
    environment {
        JAVA_TOOL_OPTIONS = '-Dfile.encoding=UTF-8'
        MAVEN_OPTS = '-Xmx512m'
    }
    
    stages {
        stage('🔍 Checkout') {
            steps {
                echo '📦 Obteniendo código fuente...'
                checkout scm
            }
        }
        
        stage('🧹 Clean & Compile') {
            steps {
                echo '🧹 Limpiando y compilando...'
                bat 'mvn clean compile'
            }
        }
        
        stage('🧪 Ejecutar 3 Pruebas Unitarias') {
            steps {
                echo '🧪 Ejecutando pruebas...'
                bat 'mvn test -Dtest=GradeServiceImplTest'
            }
        }
        
        stage('📊 Publicar Resultados') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }
    
    post {
        success {
            echo '✅ ¡BUILD EXITOSO!'
            echo 'Tests: 3/3 PASSED'
        }
        failure {
            echo '❌ BUILD FALLÓ'
        }
    }
}
```

**Nota**: Si estás en Linux/Mac, cambia `bat` por `sh`.

---

## ✅ Validación de Resultados

### 1. Ver Resultados de Pruebas

**En el Dashboard del Job**:
- Click en el número del build (ej: `#1`)
- Click en `Test Result`

**Deberías ver**:
```
Test Result
Total: 3
Passed: 3 ✅
Failed: 0
Skipped: 0
Success Rate: 100%
```

### 2. Ver Detalles de Cada Test

Click en cada test para ver:
- ✅ `registerBatchGrades_Success` - PASSED
- ✅ `getClassroomPeriodReport_Success` - PASSED
- ✅ `registerGrade_WithNullObservations_Success` - PASSED

### 3. Ver Cobertura de Código

- Click en `Coverage Report` (si JaCoCo está configurado)
- Verás métricas de cobertura por clase

### 4. Ver Console Output

Click en `Console Output` para ver:
- Logs detallados de cada test
- Mensajes con emojis y formato visual
- Tiempos de ejecución
- Resultado final: `BUILD SUCCESS`

---

## 🔧 Solución de Problemas Comunes

### ❌ Error: "Maven not found"

**Solución**:
```cmd
# Verificar instalación de Maven
mvn -version

# Si no está instalado, descargar desde:
https://maven.apache.org/download.cgi

# Configurar en Jenkins:
Manage Jenkins → Global Tool Configuration → Maven
```

### ❌ Error: "JDK not found"

**Solución**:
```cmd
# Verificar instalación de Java
java -version

# Debe ser JDK 17 o superior
# Configurar JAVA_HOME en Jenkins
```

### ❌ Error: "Tests fail in Jenkins but pass locally"

**Solución**:
```groovy
// Agregar al Jenkinsfile:
environment {
    JAVA_TOOL_OPTIONS = '-Dfile.encoding=UTF-8'
    MAVEN_OPTS = '-Xmx512m'
}
```

### ❌ Error: "Cannot connect to repository"

**Solución**:
1. Verificar URL del repositorio
2. Configurar credenciales en Jenkins:
   - `Manage Jenkins` → `Manage Credentials`
   - Agregar credenciales de Git

### ❌ Error: "Plugin not found"

**Solución**:
```
Manage Jenkins → Manage Plugins → Available
Buscar: JUnit, JaCoCo, Pipeline
Install without restart
```

---

## 📈 Validación Exitosa - Checklist

Marca cada item cuando lo completes:

### Configuración Inicial
- [ ] Jenkins instalado y accesible en http://localhost:8080
- [ ] Usuario admin creado
- [ ] Plugins instalados (JUnit, JaCoCo, Pipeline, Git)
- [ ] JDK 17 configurado
- [ ] Maven 3.9+ configurado

### Creación del Job
- [ ] Job "VG-Grade-Management-Tests" creado
- [ ] Pipeline configurado (SCM o script directo)
- [ ] Jenkinsfile en el repositorio

### Primera Ejecución
- [ ] Build ejecutado con "Build Now"
- [ ] Console Output muestra los 3 tests
- [ ] Resultado: BUILD SUCCESS
- [ ] Tests: 3/3 PASSED

### Validación de Resultados
- [ ] Test Result muestra 100% success rate
- [ ] Cada test individual está en verde
- [ ] Console Output muestra formato visual correcto
- [ ] Tiempos de ejecución razonables (< 10s)

### Validaciones MINEDU
- [ ] Test 1: Competencias y capacidades validadas
- [ ] Test 2: Reportes consolidados correctos
- [ ] Test 3: Campos opcionales manejados

---

## 🎯 Comandos Útiles

### Ejecutar pruebas localmente (antes de Jenkins):
```cmd
# Todas las pruebas
mvn clean test

# Solo las 3 pruebas específicas
mvn test -Dtest=GradeServiceImplTest

# Con reporte de cobertura
mvn clean test jacoco:report
```

### Ver reportes locales:
```cmd
# Abrir reporte JUnit
start target\surefire-reports\index.html

# Abrir reporte JaCoCo
start target\site\jacoco\index.html
```

### Reiniciar Jenkins:
```cmd
# Windows
net stop jenkins
net start jenkins

# Docker
docker restart jenkins
```

---

## 📚 Recursos Adicionales

- **Jenkins Documentation**: https://www.jenkins.io/doc/
- **Pipeline Syntax**: https://www.jenkins.io/doc/book/pipeline/syntax/
- **JUnit Plugin**: https://plugins.jenkins.io/junit/
- **JaCoCo Plugin**: https://plugins.jenkins.io/jacoco/

---

## 🎉 ¡Listo!

Si completaste todos los pasos del checklist, tus pruebas unitarias están validadas con Jenkins.

**Próximos pasos**:
1. Configurar triggers automáticos (por commit)
2. Agregar notificaciones por email
3. Integrar con GitHub/GitLab
4. Configurar despliegue automático

---

**Fecha**: 30/10/2025  
**Versión**: 1.0  
**Proyecto**: VG MS Grade Management
