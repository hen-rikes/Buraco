# --- Estágio 1: Construção (Build) ---
# Usa uma imagem oficial do Maven com Java 17 para compilar o projeto.
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copia o arquivo pom.xml primeiro para aproveitar o cache de dependências do Docker.
COPY pom.xml .

# Baixa todas as dependências do projeto.
RUN mvn dependency:go-offline

# Copia todo o resto do código-fonte da sua API.
COPY src ./src

# Executa o comando do Maven para empacotar a aplicação em um arquivo .jar.
# -DskipTests pula a execução dos testes para acelerar o build.
RUN mvn package -DskipTests


# --- Estágio 2: Execução (Runtime) ---
# Usa uma imagem oficial do Java 17, que é menor e mais segura, apenas para rodar.
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho.
WORKDIR /app

# Copia o arquivo .jar que foi gerado no estágio de construção.
# O nome do arquivo .jar deve corresponder ao que está no seu pom.xml (artifactId e version).
# Verifique o nome do .jar gerado na pasta 'target'.
COPY --from=build /app/target/buraco-api-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080 para que o mundo externo possa se comunicar com a API.
# A Render usará esta porta.
EXPOSE 8080

# O comando que será executado quando o contêiner iniciar.
ENTRYPOINT ["java", "-jar", "app.jar"]
