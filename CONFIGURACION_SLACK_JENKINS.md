# 🔔 Configuración de Notificaciones Slack en Jenkins

## 📋 Requisitos Previos

- Jenkins instalado y funcionando
- Acceso administrativo a Jenkins
- Workspace de Slack (psw-demos)
- Token de integración de Slack

---

## 🔧 Paso 1: Instalar el Plugin de Slack en Jenkins

1. Accede a Jenkins: `http://localhost:8080`
2. Ve a **Administrar Jenkins** → **Plugins**
3. En la pestaña **Available plugins**, busca: `Slack Notification`
4. Marca la casilla del plugin **Slack Notification Plugin**
5. Haz clic en **Install**
6. Espera a que se complete la instalación
7. **Reinicia Jenkins** si es necesario

---

## 🔑 Paso 2: Crear Credencial del Token de Slack

### 2.1 Acceder a Credenciales

1. Ve a **Administrar Jenkins** → **Credentials**
2. Haz clic en **(global)** domain
3. Haz clic en **Add Credentials**

### 2.2 Configurar la Credencial

Completa el formulario con los siguientes datos:

- **Kind (Tipo)**: `Secret text`
- **Scope (Ámbito)**: `Global (Jenkins, nodes, items, all child items, etc)`
- **Secret (Secreto)**: `tCO6hJacZ0PRG6S28QgTOJHT`
- **ID**: `slack-token-psw-demos` ⚠️ **IMPORTANTE: Usa exactamente este ID**
- **Description (Descripción)**: `Slack Integration Token for psw-demos workspace`

4. Haz clic en **Create**

---

## ⚙️ Paso 3: Configurar el Plugin de Slack

1. Ve a **Administrar Jenkins** → **System** (Configurar el sistema)
2. Busca la sección **Slack** (desplázate hacia abajo)
3. Configura los siguientes campos:

### Configuración de Slack

| Campo | Valor |
|-------|-------|
| **Workspace (Subdominio de equipo)** | `psw-demos` |
| **Credential** | Selecciona: `Slack Integration Token for psw-demos workspace` |
| **Default channel / member id** | `#jenkins-builds` |

4. Haz clic en **Test Connection** para verificar la conexión
   - Si aparece **"Success"** ✅, la configuración es correcta
   - Si aparece un error ❌, verifica el token y el workspace

5. Haz clic en **Save** (Guardar)

---

## 📢 Paso 4: Crear el Canal en Slack (Opcional)

Si el canal `#jenkins-builds` no existe en tu workspace de Slack:

1. Abre Slack (psw-demos.slack.com)
2. Haz clic en el **+** junto a "Canales"
3. Selecciona **Crear un canal**
4. Nombre del canal: `jenkins-builds`
5. Descripción: `Notificaciones automáticas de builds de Jenkins`
6. Tipo: **Público** (recomendado) o **Privado**
7. Haz clic en **Crear**

---

## 🔄 Paso 5: Actualizar el Jenkinsfile

El Jenkinsfile ya está configurado con las notificaciones de Slack. Solo necesitas **actualizar el ID de la credencial**:

### Opción A: Usar el ID correcto (RECOMENDADO)

Si usaste el ID `slack-token-psw-demos` en el Paso 2, actualiza el Jenkinsfile:

```groovy
tokenCredentialId: 'slack-token-psw-demos'
```

### Opción B: Usar el ID que aparece en el error

Si prefieres mantener el ID original, asegúrate de que coincida en:
- La credencial creada en Jenkins
- El `tokenCredentialId` en el Jenkinsfile

---

## ✅ Paso 6: Probar las Notificaciones

1. Ve a tu job en Jenkins
2. Haz clic en **Build Now**
3. Espera a que termine el build
4. Verifica en Slack (canal `#jenkins-builds`) que recibiste la notificación

### Tipos de Notificaciones

- **✅ SUCCESS (Verde)**: Build exitoso con resumen de pruebas
- **❌ FAILURE (Rojo)**: Build fallido con enlace a los logs
- **⚠️ UNSTABLE (Amarillo)**: Build inestable con advertencias

---

## 🎨 Ejemplo de Notificación Exitosa

```
✅ BUILD EXITOSO - JavaTest #15

Proyecto: vg-ms-grade-management
Branch: main
Commit: a1b2c3d

Pruebas Ejecutadas:
✓ Test 1: Registro de Calificaciones en Lote ✅
✓ Test 2: Reporte Consolidado por Aula ✅
✓ Test 3: Manejo de Campos Opcionales ✅

Duración: 25 segundos
Ver build: http://localhost:8080/job/JavaTest/15/
```

---

## 🔧 Personalización (Opcional)

### Cambiar el Canal de Notificaciones

Edita el Jenkinsfile:

```groovy
environment {
    SLACK_CHANNEL = '#mi-canal-personalizado'
}
```

### Personalizar Mensajes

Modifica las secciones `slackSend` en el bloque `post` del Jenkinsfile según tus necesidades.

---

## 🐛 Solución de Problemas

### ❌ Error: "Credential not found"

**Solución:**
1. Verifica que el ID de la credencial coincida exactamente
2. Revisa que la credencial esté en el scope **Global**
3. Actualiza el `tokenCredentialId` en el Jenkinsfile

### ❌ Error: "Invalid token"

**Solución:**
1. Verifica que el token de Slack sea correcto
2. Regenera el token en Slack si es necesario
3. Actualiza la credencial en Jenkins con el nuevo token

### ❌ Error: "Channel not found"

**Solución:**
1. Verifica que el canal exista en Slack
2. Asegúrate de que el bot de Jenkins tenga acceso al canal
3. Invita al bot al canal privado si es necesario

### ⚠️ No llegan notificaciones

**Solución:**
1. Verifica la configuración en **Administrar Jenkins** → **System** → **Slack**
2. Haz clic en **Test Connection** para verificar la conexión
3. Revisa los logs de Jenkins: **Administrar Jenkins** → **System Log**
4. Verifica que el plugin de Slack esté activo

---

## 📚 Recursos Adicionales

- [Documentación oficial del plugin de Slack](https://plugins.jenkins.io/slack/)
- [API de Slack para Jenkins](https://api.slack.com/messaging/webhooks)
- [Slack Apps](https://api.slack.com/apps)

---

## 📅 Última Actualización

- **Fecha**: 31/10/2025
- **Versión**: 1.0
- **Autor**: Sistema de Integración Continua

---

**¡Listo!** 🎉 Ahora tu pipeline de Jenkins enviará notificaciones automáticas a Slack cada vez que se ejecute un build.
