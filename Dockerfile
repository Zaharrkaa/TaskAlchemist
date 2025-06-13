FROM maven:3-amazoncorretto-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY ./src ./src
RUN mvn clean install -DskipTests

FROM amazoncorretto:17
WORKDIR /app
COPY --from=builder /app/target/supportWebapp-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "supportWebapp-0.0.1-SNAPSHOT.jar"]