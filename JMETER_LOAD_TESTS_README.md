# 🚀 Pruebas de Carga con JMeter - API Gestión de Calificaciones

## 📋 Descripción General

Este documento describe las 5 pruebas de carga implementadas para el microservicio de gestión de calificaciones MINEDU. Las pruebas están diseñadas para evaluar el rendimiento, estabilidad y capacidad de la API bajo diferentes escenarios de carga.

## 🎯 Archivo de Pruebas

**Archivo:** `Grade-Management-LoadTest.jmx`

Este archivo contiene todas las configuraciones necesarias para ejecutar las 5 pruebas de carga en JMeter.

---

## 📊 Las 5 Pruebas de Carga Implementadas

### Test 1: Registro Individual - Carga Normal ✅
**Objetivo:** Simular el registro normal de calificaciones individuales.

**Configuración:**
- **Usuarios concurrentes:** 50
- **Ramp-up:** 10 segundos
- **Iteraciones por usuario:** 10
- **Total de peticiones:** 500
- **Endpoint:** `POST /api/v1/grades`

**Escenario:**
Simula profesores registrando calificaciones de estudiantes de forma individual durante el periodo normal de clases.

**Validaciones:**
- Status Code: 200 o 201
- Respuesta JSON válida con `gradeId`
- Think time: 1 segundo entre peticiones

---

### Test 2: Registro en Lote - Alta Concurrencia 📦
**Objetivo:** Evaluar el rendimiento del endpoint de registro masivo.

**Configuración:**
- **Usuarios concurrentes:** 100
- **Ramp-up:** 20 segundos
- **Iteraciones por usuario:** 5
- **Total de peticiones:** 500
- **Endpoint:** `POST /api/v1/grades/batch`

**Escenario:**
Simula múltiples profesores registrando lotes de 3 calificaciones simultáneamente (fin de periodo académico).

**Validaciones:**
- Status Code: 200 o 201
- Procesamiento exitoso de lotes
- Think time: 2 segundos entre peticiones

---

### Test 3: Consulta por Estudiante - Lectura Masiva 🔍
**Objetivo:** Evaluar el rendimiento de consultas de lectura intensivas.

**Configuración:**
- **Usuarios concurrentes:** 75
- **Ramp-up:** 15 segundos
- **Iteraciones por usuario:** 20
- **Total de peticiones:** 1,500
- **Endpoint:** `GET /api/v1/grades/student`

**Escenario:**
Simula padres de familia y estudiantes consultando calificaciones simultáneamente (publicación de notas).

**Validaciones:**
- Status Code: 200
- Tiempo de respuesta < 2 segundos
- Think time: 500 ms entre peticiones

---

### Test 4: Reporte Consolidado - Consultas Complejas 📈
**Objetivo:** Evaluar consultas complejas con múltiples filtros y agregaciones.

**Configuración:**
- **Usuarios concurrentes:** 30
- **Ramp-up:** 10 segundos
- **Iteraciones por usuario:** 15
- **Total de peticiones:** 450
- **Endpoint:** `GET /api/v1/grades/classroom/report`

**Escenario:**
Simula directores y coordinadores generando reportes consolidados por aula y periodo.

**Validaciones:**
- Status Code: 200
- Tiempo de respuesta < 3 segundos
- Think time: 1.5 segundos entre peticiones

---

### Test 5: Prueba de Estrés - Picos de Tráfico ⚡
**Objetivo:** Evaluar el comportamiento del sistema bajo estrés extremo.

**Configuración:**
- **Usuarios concurrentes:** 200
- **Ramp-up:** 5 segundos (pico rápido)
- **Iteraciones por usuario:** 3
- **Total de peticiones:** 1,200 (600 POST + 600 GET)
- **Endpoints:** POST `/api/v1/grades` + GET `/api/v1/grades/student`

**Escenario:**
Simula un pico repentino de tráfico (apertura del sistema al público, campaña de matrícula).

**Validaciones:**
- Status Code: 200, 201, 429 (rate limit), o 503 (esperado bajo estrés)
- Identificación del punto de quiebre del sistema
- Think time: 100 ms (mínimo)

---

## 🛠️ Instalación de JMeter

### Opción 1: Descarga Directa
1. Visita: https://jmeter.apache.org/download_jmeter.cgi
2. Descarga Apache JMeter (versión 5.6.3 o superior)
3. Extrae el archivo ZIP/TGZ
4. JMeter requiere Java 8 o superior

### Opción 2: Usando Package Manager

**Windows (Chocolatey):**
```bash
choco install jmeter
```

**macOS (Homebrew):**
```bash
brew install jmeter
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt-get update
sudo apt-get install jmeter
```

---

## 🚀 Ejecución de las Pruebas

### Modo GUI (Recomendado para desarrollo)

1. **Abrir JMeter:**
   ```bash
   # Windows
   jmeter.bat
   
   # Linux/Mac
   jmeter.sh
   ```

2. **Abrir el archivo de pruebas:**
   - File → Open → Seleccionar `Grade-Management-LoadTest.jmx`

3. **Configurar variables (opcional):**
   - En el Test Plan, modificar:
     - `BASE_URL`: localhost (o IP del servidor)
     - `PORT`: 8080
     - `PROTOCOL`: http o https

4. **Ejecutar las pruebas:**
   - Opción A: Ejecutar todas → Botón "Play" verde
   - Opción B: Ejecutar una prueba específica → Click derecho en Thread Group → Start

5. **Ver resultados:**
   - View Results Tree (resultados detallados)
   - Summary Report (resumen estadístico)
   - Aggregate Report (métricas agregadas)
   - Graph Results (gráficos de rendimiento)

### Modo CLI (Recomendado para producción)

**Ejecutar todas las pruebas:**
```bash
jmeter -n -t Grade-Management-LoadTest.jmx -l results.jtl -e -o report/
```

**Ejecutar una prueba específica:**
```bash
# Solo Test 1
jmeter -n -t Grade-Management-LoadTest.jmx -l results-test1.jtl \
  -e -o report-test1/ \
  -Jtest.name="Test 1: Registro Individual - Carga Normal"
```

**Parámetros:**
- `-n`: Modo non-GUI
- `-t`: Archivo de test plan
- `-l`: Archivo de log de resultados
- `-e`: Generar reporte HTML al final
- `-o`: Directorio de salida del reporte

---

## 📁 Estructura de Reportes

Después de ejecutar en modo CLI, se genera:

```
report/
├── index.html              # Reporte principal
├── content/
│   ├── pages/
│   │   ├── Graph.html     # Gráficos de rendimiento
│   │   └── ...
│   └── js/
└── sbadmin2-1.0.7/        # Assets del reporte
```

Abrir `report/index.html` en un navegador para ver el dashboard completo.

---

## 📊 Métricas Clave a Monitorear

### 1. **Throughput (Rendimiento)**
- **Objetivo:** > 100 peticiones/segundo para operaciones simples
- **Crítico:** < 50 peticiones/segundo indica problemas

### 2. **Response Time (Tiempo de Respuesta)**
- **Excelente:** < 500 ms
- **Aceptable:** 500 ms - 2 segundos
- **Crítico:** > 2 segundos

### 3. **Error Rate (Tasa de Error)**
- **Objetivo:** < 1%
- **Aceptable:** 1% - 5%
- **Crítico:** > 5%

### 4. **Percentiles (90th, 95th, 99th)**
- **90th percentile:** 90% de usuarios experimentan este tiempo o menos
- **Objetivo:** P95 < 2 segundos, P99 < 3 segundos

---

## ⚙️ Configuración del Sistema a Probar

### Antes de ejecutar las pruebas:

1. **Iniciar la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

2. **Verificar que la API está activa:**
   ```bash
   curl http://localhost:8080/api/v1/grades/health
   ```

3. **Configurar base de datos:**
   - Asegurar que MongoDB está corriendo
   - Tener datos de prueba precargados (opcional)

4. **Recursos del servidor:**
   - Monitorear CPU, memoria y conexiones de BD durante las pruebas
   - Usar herramientas: `top`, `htop`, VisualVM, JConsole

---

## 🎨 Personalización de las Pruebas

### Modificar número de usuarios:
En cada Thread Group, cambiar:
- `Number of Threads (users)`: Número de usuarios concurrentes
- `Ramp-Up Period (seconds)`: Tiempo para alcanzar el número de usuarios
- `Loop Count`: Iteraciones por usuario

### Modificar endpoints:
En cada HTTP Request, cambiar:
- `Protocol`: http/https
- `Server Name`: localhost o IP del servidor
- `Port Number`: 8080 o el puerto configurado
- `Path`: Ruta del endpoint

### Agregar nuevas validaciones:
- Right Click en HTTP Request → Add → Assertions
- Opciones: Response Code, JSON Path, Duration, Size, etc.

---

## 🐛 Troubleshooting

### Error: Connection refused
**Solución:** Verificar que la aplicación está corriendo en el puerto correcto.
```bash
netstat -an | grep 8080
```

### Error: OutOfMemoryError en JMeter
**Solución:** Aumentar memoria heap de JMeter.
Editar `jmeter.bat` o `jmeter.sh`:
```bash
# Cambiar de:
HEAP="-Xms1g -Xmx1g"
# A:
HEAP="-Xms2g -Xmx4g"
```

### Resultados inconsistentes
**Solución:**
1. Ejecutar pruebas en horarios consistentes
2. Cerrar aplicaciones innecesarias
3. Usar un servidor dedicado para pruebas
4. Ejecutar múltiples veces y promediar resultados

### Tasas de error altas
**Solución:**
1. Revisar logs de la aplicación
2. Verificar configuración de base de datos
3. Revisar límites de conexiones
4. Considerar reducir número de usuarios concurrentes

---

## 📈 Mejores Prácticas

1. **No ejecutar pruebas de carga en producción** sin autorización
2. **Usar modo CLI** para pruebas largas (menos consumo de recursos)
3. **Monitorear el servidor** durante las pruebas (CPU, RAM, I/O)
4. **Ejecutar pruebas múltiples veces** para obtener resultados confiables
5. **Documentar los resultados** de cada ejecución
6. **Escalar gradualmente** el número de usuarios
7. **Limpiar datos de prueba** después de cada ejecución

---

## 📝 Ejemplo de Reporte de Resultados

```
=================================================
REPORTE DE PRUEBAS DE CARGA - API CALIFICACIONES
Fecha: 31/10/2025
Duración Total: 15 minutos
=================================================

TEST 1: Registro Individual
- Total Peticiones: 500
- Throughput: 83.33 req/s
- Tiempo Promedio: 350 ms
- Error Rate: 0.2%
- P95: 850 ms
- Resultado: ✅ APROBADO

TEST 2: Registro en Lote
- Total Peticiones: 500
- Throughput: 41.67 req/s
- Tiempo Promedio: 1,200 ms
- Error Rate: 0.8%
- P95: 2,100 ms
- Resultado: ✅ APROBADO

TEST 3: Consulta por Estudiante
- Total Peticiones: 1,500
- Throughput: 166.67 req/s
- Tiempo Promedio: 280 ms
- Error Rate: 0.0%
- P95: 650 ms
- Resultado: ✅ APROBADO

TEST 4: Reporte Consolidado
- Total Peticiones: 450
- Throughput: 50.00 req/s
- Tiempo Promedio: 1,800 ms
- Error Rate: 1.2%
- P95: 2,850 ms
- Resultado: ⚠️  ACEPTABLE

TEST 5: Prueba de Estrés
- Total Peticiones: 1,200
- Throughput: 240.00 req/s
- Tiempo Promedio: 2,500 ms
- Error Rate: 8.5%
- P95: 5,200 ms
- Resultado: ⚠️  SISTEMA BAJO ESTRÉS

=================================================
CONCLUSIÓN: Sistema estable bajo carga normal.
Requiere optimización para escenarios de estrés.
=================================================
```

---

## 🔗 Referencias

- **JMeter Documentation:** https://jmeter.apache.org/usermanual/index.html
- **Best Practices:** https://jmeter.apache.org/usermanual/best-practices.html
- **JMeter Plugins:** https://jmeter-plugins.org/

---

## 📞 Soporte

Para dudas o problemas con las pruebas de carga:
1. Revisar los logs de JMeter: `jmeter.log`
2. Revisar los logs de la aplicación
3. Consultar la documentación oficial de JMeter
4. Contactar al equipo de desarrollo

---

**Última actualización:** 31/10/2025
**Versión del archivo:** 1.0
**Autor:** Sistema de Testing Automatizado
