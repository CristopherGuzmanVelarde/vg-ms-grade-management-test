@echo off
chcp 65001 >nul
echo.
echo ╔════════════════════════════════════════════════════════════════╗
echo ║     VALIDACIÓN DE PRUEBAS UNITARIAS - VG GRADE MANAGEMENT    ║
echo ╚════════════════════════════════════════════════════════════════╝
echo.

echo 📋 Verificando requisitos previos...
echo.

REM Verificar Java
echo 🔍 Verificando Java JDK 17...
java -version 2>&1 | findstr /C:"17" >nul
if %errorlevel% neq 0 (
    echo ❌ Java JDK 17 no encontrado
    echo    Instalar desde: https://adoptium.net/
    pause
    exit /b 1
) else (
    echo ✅ Java JDK 17 encontrado
)
echo.

REM Verificar Maven
echo 🔍 Verificando Maven...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven no encontrado
    echo    Instalar desde: https://maven.apache.org/download.cgi
    pause
    exit /b 1
) else (
    echo ✅ Maven encontrado
)
echo.

REM Verificar Git
echo 🔍 Verificando Git...
git --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  Git no encontrado (opcional para Jenkins)
    echo    Instalar desde: https://git-scm.com/download/win
) else (
    echo ✅ Git encontrado
)
echo.

echo ════════════════════════════════════════════════════════════════
echo.
echo 🧪 EJECUTANDO PRUEBAS UNITARIAS...
echo.
echo    📝 Test 1: Registro de Calificaciones en Lote
echo    📊 Test 2: Reporte Consolidado por Aula
echo    🔍 Test 3: Manejo de Campos Opcionales
echo.
echo ════════════════════════════════════════════════════════════════
echo.

REM Ejecutar pruebas
mvn clean test -Dtest=GradeServiceImplTest

if %errorlevel% neq 0 (
    echo.
    echo ════════════════════════════════════════════════════════════════
    echo ❌ PRUEBAS FALLARON
    echo ════════════════════════════════════════════════════════════════
    echo.
    echo 🔍 Revisa los errores arriba
    echo 📝 Verifica que el código esté actualizado
    echo 🔧 Ejecuta: mvn clean compile
    echo.
    pause
    exit /b 1
)

echo.
echo ════════════════════════════════════════════════════════════════
echo ✅ ¡TODAS LAS PRUEBAS PASARON!
echo ════════════════════════════════════════════════════════════════
echo.
echo 📊 Resumen:
echo    ✓ Test 1: Registro en Lote - PASSED
echo    ✓ Test 2: Reporte Consolidado - PASSED
echo    ✓ Test 3: Campos Opcionales - PASSED
echo.
echo 🎉 Tu código está listo para Jenkins
echo.
echo 📋 Próximos pasos:
echo    1. Instalar Jenkins (ver VALIDACION_JENKINS_PASO_A_PASO.md)
echo    2. Configurar JDK y Maven en Jenkins
echo    3. Crear job con el Jenkinsfile
echo    4. Ejecutar Build Now
echo.
echo 📁 Reportes generados en:
echo    - target\surefire-reports\
echo    - target\site\jacoco\
echo.

pause
