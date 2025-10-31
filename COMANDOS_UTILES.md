# 🛠️ Comandos Útiles - Jenkins y Pruebas

## 📋 Comandos Maven

### Ejecutar todas las pruebas
```bash
mvn test
```

### Ejecutar solo las 3 pruebas específicas
```bash
mvn test -Dtest=GradeServiceImplTest
```

### Ejecutar una prueba específica
```bash
mvn test -Dtest=GradeServiceImplTest#registerBatchGrades_Success
```

### Limpiar y compilar
```bash
mvn clean compile
```

### Limpiar, compilar y probar
```bash
mvn clean test
```

### Generar reporte de cobertura
```bash
mvn clean test jacoco:report
```

### Ver reporte de cobertura
```bash
# Windows
start target\site\jacoco\index.html

# Linux/Mac
open target/site/jacoco/index.html
```

### Saltar pruebas
```bash
mvn clean install -DskipTests
```

### Ejecutar con logs detallados
```bash
mvn test -X
```

---

## 🐳 Comandos Docker (Jenkins)

### Ejecutar Jenkins
```bash
docker run -d --name jenkins -p 8080:8080 -p 50000:50000 jenkins/jenkins:lts
```

### Ver logs de Jenkins
```bash
docker logs jenkins
```

### Ver logs en tiempo real
```bash
docker logs -f jenkins
```

### Detener Jenkins
```bash
docker stop jenkins
```

### Iniciar Jenkins
```bash
docker start jenkins
```

### Reiniciar Jenkins
```bash
docker restart jenkins
```

### Eliminar contenedor
```bash
docker rm -f jenkins
```

### Acceder al contenedor
```bash
docker exec -it jenkins bash
```

### Ver contraseña inicial
```bash
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

### Backup de Jenkins
```bash
docker cp jenkins:/var/jenkins_home ./jenkins_backup
```

### Restaurar Jenkins
```bash
docker cp ./jenkins_backup jenkins:/var/jenkins_home
```

---

## 🖥️ Comandos Jenkins (Windows)

### Iniciar servicio
```cmd
net start jenkins
```

### Detener servicio
```cmd
net stop jenkins
```

### Reiniciar servicio
```cmd
net stop jenkins && net start jenkins
```

### Ver estado
```cmd
sc query jenkins
```

### Ver contraseña inicial
```cmd
type "C:\Program Files\Jenkins\secrets\initialAdminPassword"
```

### Ver logs
```cmd
type "C:\Program Files\Jenkins\jenkins.log"
```

---

## 🐧 Comandos Jenkins (Linux)

### Iniciar servicio
```bash
sudo systemctl start jenkins
```

### Detener servicio
```bash
sudo systemctl stop jenkins
```

### Reiniciar servicio
```bash
sudo systemctl restart jenkins
```

### Ver estado
```bash
sudo systemctl status jenkins
```

### Ver contraseña inicial
```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

### Ver logs
```bash
sudo journalctl -u jenkins -f
```

---

## 🔍 Comandos de Verificación

### Verificar Java
```bash
java -version
```

### Verificar Maven
```bash
mvn -version
```

### Verificar Git
```bash
git --version
```

### Verificar Docker
```bash
docker --version
```

### Verificar puertos en uso
```bash
# Windows
netstat -ano | findstr :8080

# Linux/Mac
lsof -i :8080
```

---

## 📊 Comandos de Reportes

### Ver reportes JUnit
```bash
# Windows
start target\surefire-reports\index.html

# Linux/Mac
open target/surefire-reports/index.html
```

### Ver reportes JaCoCo
```bash
# Windows
start target\site\jacoco\index.html

# Linux/Mac
open target/site/jacoco/index.html
```

### Generar reporte HTML de Surefire
```bash
mvn surefire-report:report
```

---

## 🔧 Comandos de Limpieza

### Limpiar target
```bash
mvn clean
```

### Limpiar caché de Maven
```bash
# Windows
rmdir /s /q %USERPROFILE%\.m2\repository

# Linux/Mac
rm -rf ~/.m2/repository
```

### Limpiar workspace de Jenkins
```bash
# En Jenkins UI:
# Job → Workspace → Wipe Out Workspace
```

---

## 🚀 Comandos de Despliegue

### Empaquetar aplicación
```bash
mvn clean package
```

### Empaquetar sin pruebas
```bash
mvn clean package -DskipTests
```

### Instalar en repositorio local
```bash
mvn clean install
```

### Crear imagen Docker
```bash
docker build -t vg-grade-management .
```

### Ejecutar aplicación
```bash
docker run -p 8081:8081 vg-grade-management
```

---

## 📝 Comandos Git

### Clonar repositorio
```bash
git clone [url-repositorio]
```

### Ver estado
```bash
git status
```

### Agregar cambios
```bash
git add .
```

### Commit
```bash
git commit -m "Actualizar pruebas unitarias"
```

### Push
```bash
git push origin main
```

### Pull
```bash
git pull origin main
```

### Ver logs
```bash
git log --oneline
```

---

## 🔐 Comandos de Seguridad

### Cambiar contraseña de Jenkins (CLI)
```bash
java -jar jenkins-cli.jar -s http://localhost:8080/ -auth admin:admin123 change-password admin newpassword
```

### Descargar Jenkins CLI
```bash
wget http://localhost:8080/jnlpJars/jenkins-cli.jar
```

---

## 📦 Comandos de Backup

### Backup de proyecto
```bash
# Windows
xcopy /E /I . ..\backup\vg-grade-management

# Linux/Mac
cp -r . ../backup/vg-grade-management
```

### Backup de base de datos (si aplica)
```bash
mongodump --db grades --out ./backup
```

---

## 🎯 Comandos Útiles del Proyecto

### Ejecutar validación local
```bash
# Windows
validar-pruebas.bat

# Linux/Mac
./validar-pruebas.sh
```

### Ver estructura del proyecto
```bash
# Windows
tree /F

# Linux/Mac
tree
```

### Contar líneas de código
```bash
# Windows
dir /s /b *.java | find /c /v ""

# Linux/Mac
find . -name "*.java" | xargs wc -l
```

---

## 🌐 URLs Útiles

### Jenkins Local
```
http://localhost:8080
```

### Aplicación Local
```
http://localhost:8081
```

### Swagger UI (si está configurado)
```
http://localhost:8081/swagger-ui.html
```

### Actuator
```
http://localhost:8081/actuator
http://localhost:8081/actuator/health
```

---

## 📚 Comandos de Documentación

### Generar Javadoc
```bash
mvn javadoc:javadoc
```

### Ver Javadoc
```bash
# Windows
start target\site\apidocs\index.html

# Linux/Mac
open target/site/apidocs/index.html
```

---

## 🔄 Comandos de CI/CD

### Trigger build desde CLI
```bash
curl -X POST http://localhost:8080/job/VG-Grade-Management-Tests/build \
  --user admin:admin123
```

### Ver último build
```bash
curl http://localhost:8080/job/VG-Grade-Management-Tests/lastBuild/api/json \
  --user admin:admin123
```

---

## 💡 Tips

### Alias útiles (agregar a .bashrc o .bash_profile)
```bash
alias mvnt='mvn clean test'
alias mvnc='mvn clean compile'
alias mvnp='mvn clean package'
alias jstart='sudo systemctl start jenkins'
alias jstop='sudo systemctl stop jenkins'
alias jrestart='sudo systemctl restart jenkins'
```

### Variables de entorno útiles
```bash
# Windows (cmd)
set JAVA_HOME=C:\Program Files\Java\jdk-17
set MAVEN_HOME=C:\Program Files\Apache\maven
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

# Linux/Mac (bash)
export JAVA_HOME=/usr/lib/jvm/java-17
export MAVEN_HOME=/opt/maven
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
```

---

**Última actualización**: 30/10/2025  
**Proyecto**: VG MS Grade Management
