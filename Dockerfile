FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/marumbi-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENV JAVA_OPTS="-Xmx512m -Xms256m"
# Adicionar opção para aguardar até 120 segundos pela disponibilidade do banco de dados
CMD ["sh", "-c", "java ${JAVA_OPTS} -Dserver.port=8080 -Dspring.datasource.hikari.initialization-fail-timeout=60000 -jar app.jar"]