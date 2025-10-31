# ⚡ Solución Rápida - Jenkins necesita Java 17

## 🎯 Tu error actual:
```
ERROR: No se encontro Java 17
Por favor instala Java 17 o configura JAVA_HOME manualmente
```

## ✅ SOLUCIÓN EN 3 PASOS (5 minutos)

### 📥 PASO 1: Descargar Java 17
1. Abre este link en tu servidor Windows de Jenkins:
   ```
   https://adoptium.net/temurin/releases/?version=17
   ```

2. Descarga:
   - **Operating System**: Windows
   - **Architecture**: x64
   - **Package Type**: JDK
   - Click en el botón de descarga `.msi`

### 💾 PASO 2: Instalar
1. Ejecuta el archivo `.msi` descargado
2. Durante la instalación, MARCA estas opciones:
   - ✅ **Set JAVA_HOME variable**
   - ✅ **Add to PATH**
   - ✅ **JavaSoft (Oracle) registry keys**
3. Click en **Install**
4. Espera a que termine (1-2 minutos)

### ✔️ PASO 3: Verificar
Abre **CMD** o **PowerShell** y ejecuta:
```cmd
java -version
```

Deberías ver:
```
openjdk version "17.0.x"
```

### 🔄 PASO 4: Ejecutar pipeline de nuevo
1. Ve a Jenkins
2. Ejecuta el build de nuevo
3. ✅ ¡Debería funcionar!

---

## 🔧 ¿Ya tienes Java 17 instalado?

Si ya tienes Java 17 pero Jenkins no lo encuentra:

### Verifica la ubicación:
```cmd
where java
```

### Si está en una ubicación diferente:

**En Jenkins:**
1. Dashboard → **Administrar Jenkins** → **Configurar el sistema**
2. Marca ☑️ **Variables de entorno**
3. Añade:
   ```
   Nombre: JAVA_HOME
   Valor: C:\tu\ruta\a\java\jdk-17
   ```
4. **Guardar** y reinicia Jenkins

---

## 📚 Documentación completa

Ver: [CONFIGURACION_JENKINS_WINDOWS.md](./CONFIGURACION_JENKINS_WINDOWS.md)

---

## 🆘 ¿Necesitas ayuda?

El pipeline ahora muestra diagnóstico detallado. Ejecuta el build de nuevo y verás:
- ✅ Todas las ubicaciones donde busca Java
- ✅ Si Java está en el PATH
- ✅ Qué versión tiene instalada
- ✅ Instrucciones específicas para tu caso

---

**⏱️ Tiempo estimado**: 5 minutos  
**✅ Solución garantizada**
