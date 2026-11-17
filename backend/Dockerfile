# Dockerfile del backend (Spring Boot).
# Se arranca desde el docker-compose.yml de la raiz del proyecto.

# ---- Paso 1: compilar el proyecto con Maven y Java 25 ----
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY . .

# El application.properties no esta en git, asi que copiamos el de ejemplo.
# Los datos de la base de datos se pasan con variables de entorno desde el docker-compose.
RUN cp src/main/resources/application.properties.example src/main/resources/application.properties

# Saltamos los tests porque necesitan una base de datos de test que todavia no hay
RUN mvn -DskipTests package

# ---- Paso 2: imagen final, solo con Java para ejecutar el .jar ----
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/3vetas-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
