# ============================================
# BUILD
# ============================================
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .

# Baixa dependências
RUN mvn dependency:go-offline -B

# Copia código fonte
COPY src src

# Build da aplicação
RUN mvn clean package -DskipTests


# ============================================
# RUNTIME
# ============================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/BACK-END-JAVA-0.0.1-SNAPSHOT.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Inicia aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]