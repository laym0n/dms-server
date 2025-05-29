FROM eclipse-temurin:23-jdk-alpine
ARG jar_name
COPY ./target/${jar_name}.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]