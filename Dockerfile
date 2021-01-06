FROM openjdk:11-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV PROFILE=local
ENTRYPOINT ["java","-jar","/app.jar"]