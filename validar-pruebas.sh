#!/bin/bash

echo ""
echo "╔════════════════════════════════════════════════════════════════╗"
echo "║     VALIDACIÓN DE PRUEBAS UNITARIAS - VG GRADE MANAGEMENT    ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

echo "📋 Verificando requisitos previos..."
echo ""

# Verificar Java
echo "🔍 Verificando Java JDK 17..."
if java -version 2>&1 | grep -q "17"; then
    echo "✅ Java JDK 17 encontrado"
else
    echo "❌ Java JDK 17 no encontrado"
    echo "   Instalar desde: https://adoptium.net/"
    exit 1
fi
echo ""

# Verificar Maven
echo "🔍 Verificando Maven..."
if command -v mvn &> /dev/null; then
    echo "✅ Maven encontrado"
else
    echo "❌ Maven no encontrado"
    echo "   Instalar desde: https://maven.apache.org/download.cgi"
    exit 1
fi
echo ""

# Verificar Git
echo "🔍 Verificando Git..."
if command -v git &> /dev/null; then
    echo "✅ Git encontrado"
else
    echo "⚠️  Git no encontrado (opcional para Jenkins)"
    echo "   Instalar desde: https://git-scm.com/"
fi
echo ""

echo "════════════════════════════════════════════════════════════════"
echo ""
echo "🧪 EJECUTANDO PRUEBAS UNITARIAS..."
echo ""
echo "   📝 Test 1: Registro de Calificaciones en Lote"
echo "   📊 Test 2: Reporte Consolidado por Aula"
echo "   🔍 Test 3: Manejo de Campos Opcionales"
echo ""
echo "════════════════════════════════════════════════════════════════"
echo ""

# Ejecutar pruebas
mvn clean test -Dtest=GradeServiceImplTest

if [ $? -ne 0 ]; then
    echo ""
    echo "════════════════════════════════════════════════════════════════"
    echo "❌ PRUEBAS FALLARON"
    echo "════════════════════════════════════════════════════════════════"
    echo ""
    echo "🔍 Revisa los errores arriba"
    echo "📝 Verifica que el código esté actualizado"
    echo "🔧 Ejecuta: mvn clean compile"
    echo ""
    exit 1
fi

echo ""
echo "════════════════════════════════════════════════════════════════"
echo "✅ ¡TODAS LAS PRUEBAS PASARON!"
echo "════════════════════════════════════════════════════════════════"
echo ""
echo "📊 Resumen:"
echo "   ✓ Test 1: Registro en Lote - PASSED"
echo "   ✓ Test 2: Reporte Consolidado - PASSED"
echo "   ✓ Test 3: Campos Opcionales - PASSED"
echo ""
echo "🎉 Tu código está listo para Jenkins"
echo ""
echo "📋 Próximos pasos:"
echo "   1. Instalar Jenkins (ver VALIDACION_JENKINS_PASO_A_PASO.md)"
echo "   2. Configurar JDK y Maven en Jenkins"
echo "   3. Crear job con el Jenkinsfile"
echo "   4. Ejecutar Build Now"
echo ""
echo "📁 Reportes generados en:"
echo "   - target/surefire-reports/"
echo "   - target/site/jacoco/"
echo ""
