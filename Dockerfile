FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/security-0.0.1-SNAPSHOT.jar /app/fourleavesshoedocker.jar

EXPOSE 8080

CMD ["java","-jar","/app/fourleavesshoedocker.jar"]