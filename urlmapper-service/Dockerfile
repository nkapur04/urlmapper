FROM openjdk:17-alpine
RUN apk add --no-cache mysql-client
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# Expose the port that the application will run on
EXPOSE 1111
ENTRYPOINT ["java","-jar","/app.jar"]