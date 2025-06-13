FROM amazoncorretto:17
WORKDIR /app
COPY target/supportWebapp-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "supportWebapp-0.0.1-SNAPSHOT.jar"]