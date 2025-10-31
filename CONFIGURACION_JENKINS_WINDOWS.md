# 🪟 Configuración de Jenkins en Windows

## ⚠️ Problema: Java 17 no encontrado

El pipeline busca Java 17 pero no lo encuentra en tu servidor Jenkins de Windows.

```
ERROR: No se encontro Java 17
Por favor instala Java 17 o configura JAVA_HOME manualmente
```

---

## � Solución Rápida (5 minutos)

### Paso 1: Descargar Java 17

Descarga **Eclipse Adoptium JDK 17** (Recomendado):
- 🔗 **Link directo**: https://adoptium.net/temurin/releases/?version=17
- Selecciona:
  - **Operating System**: Windows
  - **Architecture**: x64 (64-bit)
  - **Package Type**: JDK
  - **Version**: 17 LTS

### Paso 2: Instalar Java 17

1. Ejecuta el instalador descargado (`.msi` o `.exe`)
2. Durante la instalación:
   - ✅ Acepta la ruta por defecto: `C:\Program Files\Eclipse Adoptium\jdk-17.x.x`
   - ✅ Marca la opción: **"Set JAVA_HOME variable"**
   - ✅ Marca la opción: **"Add to PATH"**
3. Completa la instalación

### Paso 3: Verificar la instalación

Abre **PowerShell** o **CMD** y ejecuta:

```cmd
java -version
```

Deberías ver algo como:
```
openjdk version "17.0.x" 2024-xx-xx
OpenJDK Runtime Environment Temurin-17.0.x+x (build 17.0.x+x)
OpenJDK 64-Bit Server VM Temurin-17.0.x+x (build 17.0.x+x, mixed mode, sharing)
```

### Paso 4: Reiniciar Jenkins

1. Ve a **Jenkins Dashboard**
2. Click en **Administrar Jenkins**
3. Click en **Reload Configuration from Disk** o **Reiniciar**
4. Ejecuta el pipeline de nuevo

---

## 🔧 Solución Alternativa: Configurar JAVA_HOME en Jenkins

Si ya tienes Java 17 instalado en otra ubicación:

### Opción A: Variables de entorno globales

1. Ve a **Jenkins Dashboard**
2. Click en **Administrar Jenkins** → **Configurar el sistema**
3. Busca la sección **Propiedades globales**
4. Marca ☑️ **Variables de entorno**
5. Click en **Añadir**
6. Configura:
   ```
   Nombre: JAVA_HOME
   Valor: C:\Program Files\Java\jdk-17.0.10
   ```
   (Ajusta la ruta según tu instalación)
7. Click en **Guardar**

### Opción B: Configurar herramienta JDK

1. Ve a **Jenkins Dashboard**
2. Click en **Administrar Jenkins** → **Global Tool Configuration**
3. En la sección **JDK**, click en **Añadir JDK**
4. Configura:
   - **Nombre**: `JDK-17`
   - ☑️ Desmarcar **"Instalar automáticamente"**
   - **JAVA_HOME**: `C:\Program Files\Eclipse Adoptium\jdk-17.x.x`
5. Click en **Guardar**

6. Modifica el `Jenkinsfile` agregando al inicio:
   ```groovy
   pipeline {
       agent any
       
       tools {
           jdk 'JDK-17'  // Debe coinciir con el nombre configurado
       }
       
       // ... resto del pipeline
   }
   ```

---

## � Diagnóstico: ¿Dónde está Java?

### Buscar Java en tu sistema

Ejecuta en **PowerShell**:

```powershell
# Buscar todas las instalaciones de Java
Get-ChildItem -Path "C:\Program Files" -Filter "jdk*" -Recurse -ErrorAction SilentlyContinue | Select-Object FullName

# Verificar variable JAVA_HOME
$env:JAVA_HOME

# Ver rutas en PATH
$env:PATH -split ';' | Where-Object { $_ -like '*java*' }
```

### Ubicaciones comunes donde busca el pipeline

El Jenkinsfile busca automáticamente en:
- ✅ `C:\Program Files\Java\jdk-17`
- ✅ `C:\Program Files\Java\jdk-17.0.10` (y otras versiones menores)
- ✅ `C:\Program Files\OpenJDK\jdk-17`
- ✅ `C:\Program Files\Eclipse Adoptium\jdk-17*`
- ✅ `C:\Program Files\Microsoft\jdk-17`

---

## 📋 Versiones Requeridas

| Componente | Versión Requerida | Verificación |
|------------|-------------------|--------------|
| **Java** | 17 o superior | `java -version` |
| **Maven** | 3.6.0 o superior | `mvn -version` |
| **Spring Boot** | 3.4.9 | (automático en proyecto) |

---

## 🆘 Troubleshooting

### Problema: "java no se reconoce como comando"

**Solución:**
1. Verifica que Java esté instalado
2. Agrega Java al PATH del sistema:
   - Panel de Control → Sistema → Configuración avanzada del sistema
   - Variables de entorno → Path del sistema
   - Agregar: `C:\Program Files\Eclipse Adoptium\jdk-17.x.x\bin`
3. Reinicia Jenkins

### Problema: "mvn no se reconoce como comando"

**Solución:**
1. Descarga Maven: https://maven.apache.org/download.cgi
2. Extrae en: `C:\Program Files\Apache\maven`
3. Agrega al PATH: `C:\Program Files\Apache\maven\bin`
4. Reinicia Jenkins

### Problema: Jenkins no ve los cambios

**Solución:**
1. En Jenkins, ve a **Administrar Jenkins**
2. Click en **Reload Configuration from Disk**
3. O reinicia el servicio de Jenkins:
   ```cmd
   net stop jenkins
   net start jenkins
   ```

---

## 📞 Soporte Adicional

Si después de seguir estos pasos el problema persiste:

1. ✅ Verifica los permisos del usuario de Jenkins en la carpeta de Java
2. ✅ Revisa los logs: `C:\ProgramData\Jenkins\.jenkins\logs\jenkins.log`
3. ✅ Consulta la documentación: [GUIA_JENKINS_INTEGRACION.md](./GUIA_JENKINS_INTEGRACION.md)
4. ✅ Ejecuta el pipeline con el nuevo diagnóstico mejorado

---

**Última actualización**: 31/10/2025  
**Autor**: Equipo de Desarrollo VG
