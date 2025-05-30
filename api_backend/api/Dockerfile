FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y

ENV LANG C.UTF-8
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/controle-financeiro.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]