# Usar uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho no container
WORKDIR /app

# Copiar o arquivo JAR da aplicação para o container
COPY target/order-hub-*.jar order-hub-0.0.1-SNAPSHOT.jar

# Expor a porta que a aplicação Spring Boot está usando
EXPOSE 8080

# Comando para executar a aplicação Spring Boot
CMD ["java", "-jar", "order-hub-0.0.1-SNAPSHOT.jar", "--server.port=8080"]
