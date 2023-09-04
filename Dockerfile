# Use uma imagem base do Maven para compilar o projeto
FROM maven:3.8.4-openjdk-11 AS build
# Define o diretório de trabalho
WORKDIR /app
# Copia o arquivo POM (descritor de projeto) para baixar dependências
COPY pom.xml .
# Baixa as dependências do projeto
RUN mvn dependency:go-offline
# Copia o código-fonte
COPY src ./src
# Compila e empacota o projeto
RUN mvn package
# Imagem final da aplicação
FROM openjdk:21-ea-17-jdk-slim
# Define o diretório de trabalho
WORKDIR /app
# Copia o JAR compilado da etapa anterior
COPY --from=build /app/target/store-1.0.jar app.jar
# Comando para iniciar a aplicação quando o contêiner for executado
CMD ["java", "-jar", "app.jar"]