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
            echo 'Test 1: Registro en Lote - PASSED'
            echo 'Test 2: Reporte Consolidado - PASSED'
            echo 'Test 3: Campos Opcionales - PASSED'
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
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[Pipeline] stage (Test Report)
Generando reportes de pruebas...
[Pipeline] stage (Code Coverage)
Generando reporte de cobertura...
[Pipeline] echo
Pipeline ejecutado exitosamente!
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
   ```

### 6.2 Ver Cobertura de Código

1. Click en **Coverage Report**
2. Verás métricas de JaCoCo

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

### Tests fallan en Jenkins
- Verificar encoding UTF-8
- Revisar variables de entorno

### JaCoCo no genera reportes
- Verificar que el plugin esté instalado
- Comprobar que existe `target/jacoco.exec`

---

## ✅ Checklist Final

- [ ] Jenkins instalado y configurado
- [ ] Plugins instalados
- [ ] JDK y Maven configurados
- [ ] Job creado
- [ ] Jenkinsfile en repositorio
- [ ] Primera ejecución exitosa
- [ ] Reportes visibles

---

## 📚 Recursos

- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [Pipeline Syntax](https://www.jenkins.io/doc/book/pipeline/syntax/)
- [JaCoCo Plugin](https://plugins.jenkins.io/jacoco/)

---

**Última actualización**: 29 de Octubre, 2025  
**Versión**: 1.0.0
