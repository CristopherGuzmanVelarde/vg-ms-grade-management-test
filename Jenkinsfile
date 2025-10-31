pipeline {
    agent any
    
    environment {
        JAVA_TOOL_OPTIONS = '-Dfile.encoding=UTF-8'
        MAVEN_OPTS = '-Xmx512m'
        SLACK_CHANNEL = '#jenkins-builds'  // Canal de Slack para notificaciones
    }
    
    stages {
        stage('🔍 Checkout') {
            steps {
                echo '📦 Obteniendo código fuente del repositorio...'
                checkout scm
            }
        }
        
        stage('🧹 Clean & Compile') {
            steps {
                echo '🧹 Limpiando y compilando el proyecto...'
                script {
                    if (isUnix()) {
                        sh '''
                            # Configurar JAVA_HOME para Linux
                            if [ -d "/usr/lib/jvm/java-17-openjdk-amd64" ]; then
                                export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
                            elif [ -d "/usr/lib/jvm/java-17" ]; then
                                export JAVA_HOME=/usr/lib/jvm/java-17
                            fi
                            export PATH=$JAVA_HOME/bin:$PATH
                            
                            echo "Java version:"
                            java -version
                            mvn clean compile
                        '''
                    } else {
                        bat '''
                            @echo off
                            echo ========================================
                            echo Verificando Java en el sistema...
                            echo ========================================
                            
                            where java >nul 2>&1
                            if %ERRORLEVEL% NEQ 0 (
                                echo [ERROR] Java no encontrado en PATH
                                exit /b 1
                            )
                            
                            echo [INFO] Java encontrado en PATH
                            java -version
                            
                            echo.
                            echo ========================================
                            echo Iniciando compilacion...
                            echo ========================================
                            
                            mvn clean compile
                        '''
                    }
                }
            }
        }
        
        stage('🧪 Ejecutar 3 Pruebas Unitarias') {
            steps {
                echo '🧪 Ejecutando las 3 pruebas unitarias específicas...'
                echo '   📝 Test 1: Registro de Calificaciones en Lote (con capacidades MINEDU)'
                echo '   📊 Test 2: Reporte Consolidado por Aula y Período'
                echo '   🔍 Test 3: Manejo de Campos Opcionales (null handling)'
                echo ''
                echo '📋 Validaciones incluidas:'
                echo '   ✓ Competencias y capacidades según estándar MINEDU v8.0'
                echo '   ✓ Registro masivo de calificaciones (batch)'
                echo '   ✓ Reportes consolidados por profesor'
                echo '   ✓ Manejo de campos opcionales'
                script {
                    if (isUnix()) {
                        sh '''
                            # Configurar JAVA_HOME para Linux
                            if [ -d "/usr/lib/jvm/java-17-openjdk-amd64" ]; then
                                export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
                            elif [ -d "/usr/lib/jvm/java-17" ]; then
                                export JAVA_HOME=/usr/lib/jvm/java-17
                            fi
                            export PATH=$JAVA_HOME/bin:$PATH
                            
                            mvn test -Dtest=GradeServiceImplTest#registerBatchGrades_Success,GradeServiceImplTest#getClassroomPeriodReport_Success,GradeServiceImplTest#registerGrade_WithNullObservations_Success
                        '''
                    } else {
                        bat '''
                            @echo off
                            echo Ejecutando pruebas unitarias...
                            mvn test -Dtest=GradeServiceImplTest#registerBatchGrades_Success,GradeServiceImplTest#getClassroomPeriodReport_Success,GradeServiceImplTest#registerGrade_WithNullObservations_Success
                        '''
                    }
                }
            }
        }
        
        stage('📊 Publicar Resultados de Pruebas') {
            steps {
                echo '📊 Publicando reportes de pruebas JUnit...'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        
        stage('📈 Generar Cobertura de Código') {
            steps {
                echo '📈 Generando reporte de cobertura con JaCoCo...'
                script {
                    if (isUnix()) {
                        sh '''
                            # Configurar JAVA_HOME para Linux
                            if [ -d "/usr/lib/jvm/java-17-openjdk-amd64" ]; then
                                export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
                            elif [ -d "/usr/lib/jvm/java-17" ]; then
                                export JAVA_HOME=/usr/lib/jvm/java-17
                            fi
                            export PATH=$JAVA_HOME/bin:$PATH
                            
                            mvn jacoco:report
                        '''
                    } else {
                        bat '''
                            @echo off
                            echo Generando reporte de cobertura...
                            mvn jacoco:report
                        '''
                    }
                }
                echo '✅ Reporte de cobertura generado (archivos XML/HTML disponibles en target/site/jacoco/)'
            }
        }
    }
    
    post {
        always {
            echo '📦 Archivando artefactos generados...'
            archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
            archiveArtifacts artifacts: '**/target/site/jacoco/**', allowEmptyArchive: true
        }
        success {
            echo '✅ ¡BUILD EXITOSO!'
            echo '═══════════════════════════════════════════════════════════════'
            echo '📊 RESUMEN DE PRUEBAS UNITARIAS:'
            echo '   ✓ Test 1: Registro de Calificaciones en Lote - PASSED'
            echo '      • Validación de competencias y capacidades MINEDU'
            echo '      • 2 estudiantes registrados exitosamente'
            echo '   ✓ Test 2: Reporte Consolidado por Aula - PASSED'
            echo '      • Agrupación por curso validada'
            echo '      • Filtrado por profesor correcto'
            echo '   ✓ Test 3: Manejo de Campos Opcionales - PASSED'
            echo '      • Campos null manejados correctamente'
            echo '      • Capacidades evaluadas incluidas'
            echo '═══════════════════════════════════════════════════════════════'
            echo '📈 Reportes de cobertura y pruebas disponibles en Jenkins'
            echo '🎉 Todas las validaciones completadas exitosamente'
            echo '📅 Última actualización: 31/10/2025'
            
            // Notificación de éxito a Slack
            slackSend(
                channel: env.SLACK_CHANNEL,
                color: 'good',
                message: """
                ✅ *BUILD EXITOSO* - ${env.JOB_NAME} #${env.BUILD_NUMBER}
                
                *Proyecto:* vg-ms-grade-management
                *Branch:* ${env.GIT_BRANCH ?: 'main'}
                *Commit:* ${env.GIT_COMMIT?.take(7) ?: 'N/A'}
                
                *Pruebas Ejecutadas:*
                ✓ Test 1: Registro de Calificaciones en Lote ✅
                ✓ Test 2: Reporte Consolidado por Aula ✅  
                ✓ Test 3: Manejo de Campos Opcionales ✅
                
                *Duración:* ${currentBuild.durationString.replace(' and counting', '')}
                *Ver build:* ${env.BUILD_URL}
                """.stripIndent(),
                teamDomain: 'psw-demos',
                tokenCredentialId: 'slack-token-psw-demos'
            )
        }
        failure {
            echo '❌ BUILD FALLÓ'
            echo '═══════════════════════════════════════════════════════════════'
            echo '🔍 Revisa los logs de consola para identificar el error'
            echo '📧 Se recomienda notificar al equipo de desarrollo'
            echo '═══════════════════════════════════════════════════════════════'
            
            // Notificación de fallo a Slack
            slackSend(
                channel: env.SLACK_CHANNEL,
                color: 'danger',
                message: """
                ❌ *BUILD FALLIDO* - ${env.JOB_NAME} #${env.BUILD_NUMBER}
                
                *Proyecto:* vg-ms-grade-management
                *Branch:* ${env.GIT_BRANCH ?: 'main'}
                *Commit:* ${env.GIT_COMMIT?.take(7) ?: 'N/A'}
                
                *Error:* El build ha fallado durante la ejecución
                *Duración:* ${currentBuild.durationString.replace(' and counting', '')}
                
                *Acción requerida:* Revisa los logs de consola
                *Ver build:* ${env.BUILD_URL}console
                """.stripIndent(),
                teamDomain: 'psw-demos',
                tokenCredentialId: 'slack-token-psw-demos'
            )
        }
        unstable {
            echo '⚠️ BUILD INESTABLE'
            echo 'Algunas pruebas fallaron o hay problemas de calidad'
            
            // Notificación de build inestable a Slack
            slackSend(
                channel: env.SLACK_CHANNEL,
                color: 'warning',
                message: """
                ⚠️ *BUILD INESTABLE* - ${env.JOB_NAME} #${env.BUILD_NUMBER}
                
                *Proyecto:* vg-ms-grade-management
                *Branch:* ${env.GIT_BRANCH ?: 'main'}
                
                *Estado:* Algunas pruebas fallaron o hay problemas de calidad
                *Duración:* ${currentBuild.durationString.replace(' and counting', '')}
                *Ver build:* ${env.BUILD_URL}
                """.stripIndent(),
                teamDomain: 'psw-demos',
                tokenCredentialId: 'slack-token-psw-demos'
            )
        }
    }
}
