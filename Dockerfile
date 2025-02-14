FROM openjdk:22-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/SpringApi-0.0.1-SNAPSHOT.jar spring-api.jar
ENTRYPOINT ["java", "-jar", "spring-api.jar"]