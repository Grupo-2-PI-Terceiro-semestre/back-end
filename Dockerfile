# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:resolve

# Copia o código-fonte
COPY src ./src

# Compila a aplicação sem rodar testes
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o JAR gerado do estágio de build
COPY --from=build /app/target/order-hub-0.0.1-SNAPSHOT.jar ./order-hub.jar

# Configurações do Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_DATASOURCE_URL=jdbc:mysql://${RDS_ENDPOINT}:3306/order_hub
ENV SPRING_DATASOURCE_USERNAME=${DB_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}

# Expõe a porta 8080
EXPOSE 8080

# Define o ponto de entrada
ENTRYPOINT ["java", "-jar", "order-hub.jar"]
