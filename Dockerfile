FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/*.jar ./financialinvestment.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "financialinvestment.jar"]
