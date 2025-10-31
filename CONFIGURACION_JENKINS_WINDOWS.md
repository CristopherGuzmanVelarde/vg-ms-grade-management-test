# 🪟 Configuración de Jenkins en Windows

## Problema: JAVA_HOME no definido correctamente

### ✅ Solución Implementada

El Jenkinsfile ahora **detecta automáticamente** Java 17 en las siguientes ubicaciones comunes de Windows:

1. `C:\Program Files\Java\jdk-17`
2. `C:\Program Files\OpenJDK\jdk-17`
3. `C:\Program Files\Eclipse Adoptium\jdk-17`
4. `C:\Program Files\Microsoft\jdk-17`

### 🔧 Si el problema persiste

#### Opción 1: Instalar Java 17 (Recomendado)

1. **Descargar Java 17** desde una de estas fuentes:
   - [Eclipse Adoptium (Temurin)](https://adoptium.net/temurin/releases/?version=17)
   - [Oracle JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
   - [Microsoft OpenJDK 17](https://learn.microsoft.com/en-us/java/openjdk/download)

2. **Instalar** en una de las rutas soportadas:
   ```
   C:\Program Files\Java\jdk-17
   C:\Program Files\OpenJDK\jdk-17
   C:\Program Files\Eclipse Adoptium\jdk-17
   ```

3. **Verificar** la instalación:
   ```cmd
   java -version
   ```
   Debería mostrar: `openjdk version "17.x.x"`

#### Opción 2: Configurar JAVA_HOME manualmente en Jenkins

Si Java 17 está instalado en otra ubicación:

1. Ir a **Jenkins Dashboard** → **Administrar Jenkins** → **Configurar el sistema**
2. En **Propiedades globales**, marcar **Variables de entorno**
3. Agregar:
   - **Nombre**: `JAVA_HOME`
   - **Valor**: Ruta completa a tu JDK 17 (ejemplo: `C:\Java\jdk-17.0.10`)

#### Opción 3: Configurar herramienta JDK en Jenkins

1. Ir a **Jenkins Dashboard** → **Administrar Jenkins** → **Herramientas Globales**
2. En la sección **JDK**, clic en **Añadir JDK**
3. Configurar:
   - **Nombre**: `JDK-17`
   - **JAVA_HOME**: Ruta a tu instalación de Java 17
   - Desmarcar "Instalar automáticamente"

4. Modificar el Jenkinsfile para usar la herramienta:
   ```groovy
   pipeline {
       agent any
       
       tools {
           jdk 'JDK-17'  // Debe coincidir con el nombre configurado
       }
       // ... resto del pipeline
   }
   ```

### 📋 Verificar la configuración actual

Para ver qué Java está usando Jenkins:

1. Crear un pipeline de prueba con este código:
   ```groovy
   pipeline {
       agent any
       stages {
           stage('Test Java') {
               steps {
                   bat '''
                       echo JAVA_HOME: %JAVA_HOME%
                       where java
                       java -version
                   '''
               }
           }
       }
   }
   ```

2. Ejecutar y revisar la salida de consola

### 🎯 Versiones requeridas

- **Java**: 17 o superior
- **Maven**: 3.6.0 o superior
- **Spring Boot**: 3.4.9 (requiere Java 17 mínimo)

### 📞 Soporte adicional

Si después de seguir estos pasos el problema persiste:

1. Verifica que la cuenta de Jenkins tenga permisos de lectura en la carpeta de Java
2. Revisa los logs de Jenkins en `C:\ProgramData\Jenkins\.jenkins\logs`
3. Asegúrate de que no haya espacios o caracteres especiales en la ruta de instalación

---
**Última actualización**: 31/10/2025
