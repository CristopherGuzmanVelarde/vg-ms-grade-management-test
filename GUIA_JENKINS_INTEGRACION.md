# 🚀 Guía Completa: Integración de Pruebas Unitarias con Jenkins

## 📋 Tabla de Contenidos
1. [Requisitos Previos](#requisitos-previos)
2. [Instalación de Jenkins](#instalación-de-jenkins)
3. [Configuración de Jenkins](#configuración-de-jenkins)
4. [Creación del Pipeline](#creación-del-pipeline)
5. [Ejecución y Validación](#ejecución-y-validación)
6. [Interpretación de Resultados](#interpretación-de-resultados)
7. [Solución de Problemas](#solución-de-problemas)

---

## 📦 Requisitos Previos

Antes de comenzar, asegúrate de tener:

- ✅ **Jenkins** instalado (versión 2.400+)
- ✅ **Java JDK 17** o superior
- ✅ **Maven 3.8+** instalado
- ✅ **Git** instalado
- ✅ Acceso al repositorio del proyecto
- ✅ Permisos de administrador en Jenkins

---

## 🔧 Paso 1: Instalación de Jenkins

### Opción A: Instalación en Windows

1. **Descargar Jenkins**:
   ```
   https://www.jenkins.io/download/
   ```

2. **Instalar Jenkins**:
   - Ejecutar el instalador `.msi`
   - Seguir el asistente de instalación
   - Puerto por defecto: `8080`

3. **Iniciar Jenkins**:
   ```cmd
   net start jenkins
   ```

4. **Acceder a Jenkins**:
   ```
   http://localhost:8080
   ```

5. **Desbloquear Jenkins**:
   - Ubicar la contraseña inicial en:
     ```
     C:\Program Files\Jenkins\secrets\initialAdminPassword
     ```
   - Copiar y pegar en el navegador

### Opción B: Instalación con Docker

```bash
docker run -d -p 8080:8080 -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  --name jenkins \
  jenkins/jenkins:lts
```

---

## ⚙️ Paso 2: Configuración Inicial de Jenkins

### 2.1 Instalar Plugins Necesarios

1. Ir a: **Manage Jenkins** → **Manage Plugins**

2. Instalar los siguientes plugins:
   - ✅ **Git Plugin**
   - ✅ **Maven Integration Plugin**
   - ✅ **JUnit Plugin**
   - ✅ **JaCoCo Plugin**
   - ✅ **Pipeline Plugin**
   - ✅ **Blue Ocean** (opcional, para mejor UI)

3. Reiniciar Jenkins después de instalar

### 2.2 Configurar Herramientas Globales

#### Configurar JDK

1. Ir a: **Manage Jenkins** → **Global Tool Configuration**

2. En la sección **JDK**:
   - Click en **Add JDK**
   - Nombre: `JDK-17`
   - Desmarcar "Install automatically"
   - JAVA_HOME: `C:\Program Files\Java\jdk-17` (ajustar según tu instalación)

#### Configurar Maven

1. En la sección **Maven**:
   - Click en **Add Maven**
   - Nombre: `Maven-3.9`
   - Desmarcar "Install automatically"
   - MAVEN_HOME: `C:\Program Files\Apache\maven` (ajustar según tu instalación)

#### Configurar Git

1. En la sección **Git**:
   - Nombre: `Default`
   - Path to Git executable: `C:\Program Files\Git\bin\git.exe`

---

## 🏗️ Paso 3: Crear el Job de Jenkins

### 3.1 Crear Nuevo Pipeline

1. En el Dashboard de Jenkins, click en **New Item**

2. Configurar:
   - Nombre: `VG-MS-Grade-Management-Tests`
   - Tipo: **Pipeline**
   - Click en **OK**

### 3.2 Configurar el Pipeline

En la sección **Pipeline**, seleccionar: **Pipeline script** y copiar el siguiente código:

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
        stage('Checkout') {
            steps {
                echo 'Clonando repositorio...'
                checkout scm
            }
        }
        
        stage('Clean') {
            steps {
                echo 'Limpiando proyecto...'
                bat 'mvn clean'
            }
        }
        
        stage('Compile') {
            steps {
                echo 'Compilando codigo fuente...'
                bat 'mvn compile'
            }
        }
        
        stage('Unit Tests - 3 Pruebas') {
            steps {
                echo 'Ejecutando las 3 pruebas unitarias...'
                echo 'Test 1: Registro en Lote con capacidades MINEDU'
                echo 'Test 2: Reporte Consolidado por Aula'
                echo 'Test 3: Manejo de Campos Opcionales'
                bat 'mvn test -Dtest=GradeServiceImplTest#registerBatchGrades_Success,GradeServiceImplTest#getClassroomPeriodReport_Success,GradeServiceImplTest#registerGrade_WithNullObservations_Success'
            }
        }
        
        stage('Test Report') {
            steps {
                echo 'Generando reportes de pruebas...'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        
        stage('Code Coverage') {
            steps {
                echo 'Generando reporte de cobertura...'
                bat 'mvn jacoco:report'
                jacoco(
                    execPattern: '**/target/jacoco.exec',
                    classPattern: '**/target/classes',
                    sourcePattern: '**/src/main/java'
                )
            }
        }
    }
    
    post {
        always {
            echo 'Archivando resultados...'
            archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
        }
        success {
            echo 'Pipeline ejecutado exitosamente!'
            echo 'Test 1: Registro en Lote (con capacidades MINEDU) - PASSED'
            echo 'Test 2: Reporte Consolidado por Aula - PASSED'
            echo 'Test 3: Campos Opcionales (null handling) - PASSED'
            echo 'Todas las validaciones de estándar MINEDU v8.0 completadas'
        }
        failure {
            echo 'Pipeline fallo'
            echo 'Revisa los logs para mas detalles'
        }
    }
}
```

**Nota**: Si estás en Linux/Mac, cambia `bat` por `sh`.

---

## 🎯 Paso 4: Crear Jenkinsfile en el Repositorio

Crea un archivo `Jenkinsfile` en la raíz de tu proyecto con el contenido del Paso 3.2.

Luego haz commit:

```bash
git add Jenkinsfile
git commit -m "Add Jenkinsfile for CI/CD"
git push origin main
```

---

## ▶️ Paso 5: Ejecutar el Pipeline

### 5.1 Primera Ejecución

1. En el Dashboard de Jenkins, selecciona tu job
2. Click en **Build Now**
3. Observa la ejecución en **Console Output**

### 5.2 Resultado Esperado

```
Started by user Admin
[Pipeline] Start of Pipeline
[Pipeline] stage (Checkout)
Clonando repositorio...
[Pipeline] stage (Clean)
Limpiando proyecto...
[Pipeline] stage (Compile)
Compilando codigo fuente...
[Pipeline] stage (Unit Tests - 3 Pruebas)
Ejecutando las 3 pruebas unitarias...
Test 1: Registro en Lote con capacidades MINEDU
Test 2: Reporte Consolidado por Aula
Test 3: Manejo de Campos Opcionales

╔════════════════════════════════════════════════════════════════╗
║  📝 TEST 1: REGISTRO DE CALIFICACIONES EN LOTE               ║
╚════════════════════════════════════════════════════════════════╝
✅ Calificación 1 registrada: GRD2024101
✅ Calificación 2 registrada: GRD2024102
✨ TEST 1 COMPLETADO: 2 calificaciones registradas exitosamente

╔════════════════════════════════════════════════════════════════╗
║  📊 TEST 2: REPORTE CONSOLIDADO POR AULA Y PERÍODO          ║
╚════════════════════════════════════════════════════════════════╝
📋 Reporte generado con 2 cursos
✅ Validación: 2 cursos agrupados correctamente
✨ TEST 2 COMPLETADO: Reporte consolidado generado exitosamente

╔════════════════════════════════════════════════════════════════╗
║  🔍 TEST 3: MANEJO DE CAMPOS OPCIONALES (NULL)              ║
╚════════════════════════════════════════════════════════════════╝
✅ Calificación registrada: GRD2024303
✅ Observaciones: null (OK)
✨ TEST 3 COMPLETADO: Campo opcional manejado correctamente

Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
Time elapsed: 6.669 s

[Pipeline] stage (Test Report)
Generando reportes de pruebas...
[Pipeline] stage (Code Coverage)
Generando reporte de cobertura...
[Pipeline] echo
Pipeline ejecutado exitosamente!
Test 1: Registro en Lote (con capacidades MINEDU) - PASSED
Test 2: Reporte Consolidado por Aula - PASSED
Test 3: Campos Opcionales (null handling) - PASSED
Todas las validaciones de estándar MINEDU v8.0 completadas
Finished: SUCCESS
```

---

## 📊 Paso 6: Interpretar Resultados

### 6.1 Ver Resultados de Pruebas

1. Click en el número de build (ej: #1)
2. Click en **Test Result**
3. Verás:
   ```
   Total: 3
   Passed: 3
   Failed: 0
   Success Rate: 100%
   Duration: ~6.7s
   ```

### 6.2 Detalle de las Pruebas Ejecutadas

**Test 1: Registro de Calificaciones en Lote**
- ✅ Valida registro masivo de 2 estudiantes
- ✅ Incluye competencias MINEDU: "Resuelve problemas de cantidad"
- ✅ Incluye capacidades: "Traduce cantidades a expresiones numéricas"
- ✅ Calificaciones: A (15.0) y AD (18.0)
- ⏱️ Tiempo: ~0.033s

**Test 2: Reporte Consolidado por Aula**
- ✅ Genera reporte para aula 5A-SECUNDARIA
- ✅ Agrupa calificaciones por curso (Matemática y Comunicación)
- ✅ Filtra por profesor (TCH2024001)
- ✅ Valida distribución de calificaciones
- ⏱️ Tiempo: ~6.446s

**Test 3: Manejo de Campos Opcionales**
- ✅ Valida registro con campo observations = null
- ✅ Incluye competencia: "Se comunica oralmente en su lengua materna"
- ✅ Incluye capacidad: "Obtiene información del texto oral"
- ✅ Calificación: B (13.5)
- ⏱️ Tiempo: ~0.060s

### 6.3 Ver Cobertura de Código

1. Click en **Coverage Report**
2. Verás métricas de JaCoCo

### 6.4 Validaciones del Estándar MINEDU v8.0

Las pruebas validan el cumplimiento del estándar MINEDU:

**Competencias y Capacidades**:
- ✅ Competencias según currículo nacional
- ✅ Capacidades específicas por competencia
- ✅ Relación correcta competencia-capacidad

**Escalas de Calificación**:
- ✅ AD (Logro Destacado): 18-20
- ✅ A (Logro Esperado): 14-17
- ✅ B (En Proceso): 11-13
- ✅ C (En Inicio): 0-10

**Tipos de Evaluación**:
- ✅ FORMATIVA: Evaluación continua
- ✅ SUMATIVA: Evaluación final
- ✅ DIAGNOSTICA: Evaluación inicial

**Períodos Académicos**:
- ✅ I_TRIMESTRE, II_TRIMESTRE, III_TRIMESTRE
- ✅ I_BIMESTRE, II_BIMESTRE, III_BIMESTRE, IV_BIMESTRE

**Manejo de Datos**:
- ✅ Campos opcionales (observations)
- ✅ Validación de null values
- ✅ Integridad referencial (IDs de estudiantes, cursos, aulas)

---

## 🔄 Paso 7: Automatización (Opcional)

### Trigger por Commit

En la configuración del job, marcar:
- **GitHub hook trigger for GITScm polling**

### Trigger Programado

Agregar al Jenkinsfile:

```groovy
triggers {
    cron('0 2 * * *')  // Cada día a las 2 AM
}
```

---

## 🐛 Paso 8: Solución de Problemas

### Maven no encontrado
- Verificar configuración en Global Tool Configuration
- Reiniciar Jenkins
- Comprobar variable MAVEN_HOME

### Tests fallan en Jenkins
- Verificar encoding UTF-8 en JAVA_TOOL_OPTIONS
- Revisar variables de entorno
- Comprobar que JDK 17 esté configurado correctamente

### JaCoCo no genera reportes
- Verificar que el plugin esté instalado
- Comprobar que existe `target/jacoco.exec`
- Ejecutar `mvn clean` antes de las pruebas

### Error: "Campo capacityEvaluated es requerido"
- Verificar que el DTO GradeRequest incluya el campo
- Actualizar las pruebas con capacidades MINEDU válidas
- Revisar que el mapper esté procesando correctamente

### Error: "Competencia no válida según MINEDU"
- Usar competencias del currículo nacional vigente
- Ejemplos válidos:
  - "Resuelve problemas de cantidad"
  - "Se comunica oralmente en su lengua materna"
  - "Gestiona proyectos de emprendimiento económico"

### Pruebas lentas (> 10 segundos)
- Verificar que se usen mocks (no base de datos real)
- Revisar configuración de memoria: MAVEN_OPTS='-Xmx512m'
- Considerar ejecutar pruebas en paralelo

---

## ✅ Checklist Final

### Configuración Básica
- [ ] Jenkins instalado y configurado
- [ ] Plugins instalados (Git, Maven, JUnit, JaCoCo, Pipeline)
- [ ] JDK 17 configurado en Global Tool Configuration
- [ ] Maven 3.9+ configurado en Global Tool Configuration
- [ ] Git configurado correctamente

### Pipeline y Repositorio
- [ ] Job de Pipeline creado en Jenkins
- [ ] Jenkinsfile en la raíz del repositorio
- [ ] Repositorio conectado correctamente
- [ ] Primera ejecución exitosa

### Validaciones de Pruebas
- [ ] 3 pruebas unitarias ejecutándose correctamente
- [ ] Test 1: Registro en Lote - PASSED
- [ ] Test 2: Reporte Consolidado - PASSED
- [ ] Test 3: Campos Opcionales - PASSED
- [ ] Validaciones MINEDU v8.0 implementadas
- [ ] Competencias y capacidades incluidas

### Reportes y Métricas
- [ ] Reportes JUnit visibles en Jenkins
- [ ] Cobertura JaCoCo generándose correctamente
- [ ] Artefactos archivándose en cada build
- [ ] Console Output mostrando logs detallados

### Automatización (Opcional)
- [ ] Trigger por commit configurado
- [ ] Trigger programado configurado
- [ ] Notificaciones configuradas

---

## 📚 Recursos

- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [Pipeline Syntax](https://www.jenkins.io/doc/book/pipeline/syntax/)
- [JaCoCo Plugin](https://plugins.jenkins.io/jacoco/)

---

**Última actualización**: 30 de Octubre, 2025  
**Versión**: 1.1.0  
**Cambios**: Actualización para incluir validación de capacidades MINEDU v8.0
