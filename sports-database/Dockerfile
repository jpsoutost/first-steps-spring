FROM maven:3.8-openjdk-17-slim as builder

RUN mkdir -p /app
WORKDIR /app
ADD pom.xml .
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn package -DskipTests

ENTRYPOINT ["java","-jar","/app.jar"]

FROM openjdk:17-jdk-alpine as runner
ENV SERVER_PORT=8002
ENV SPRING_PROFILES_ACTIVE="prod"
COPY --from=builder /app/target/*.jar /app.jar
EXPOSE 8002
ENTRYPOINT ["java","-jar","/app.jar"]