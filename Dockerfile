FROM openjdk:11.0.10-slim

EXPOSE 8080

ARG JAR_FILE=target/config-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]