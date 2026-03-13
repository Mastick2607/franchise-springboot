# Fase 1: Compilación (Build)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copiar el pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Fase 2: Imagen de ejecución (Runtime) - AQUÍ ESTABA EL ERROR
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el JAR generado desde la fase de build
COPY --from=build /app/target/*.jar app.jar

# Puerto por defecto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]