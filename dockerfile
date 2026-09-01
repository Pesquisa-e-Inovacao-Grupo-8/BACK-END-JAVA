# ============================================
# BUILD
# ============================================
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copia Maven Wrapper
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Permissão para executar o Maven Wrapper
RUN chmod +x mvnw

# Baixa dependências
RUN ./mvnw dependency:go-offline -B

# Copia código fonte
COPY src src

# Build da aplicação
RUN ./mvnw clean package -DskipTests


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