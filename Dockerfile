# Usar Java 21 como base
FROM openjdk:21-jdk-slim

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Hacer mvnw ejecutable
RUN chmod +x mvnw

# Descargar dependencias
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src src

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer puerto
EXPOSE $PORT

# Comando para ejecutar la aplicación
CMD ["java", "-Dserver.port=${PORT:-8080}", "-jar", "target/psicoclinic-0.0.1-SNAPSHOT.jar"]