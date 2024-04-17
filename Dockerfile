FROM maven:3.8.3-openjdk-17 AS build
LABEL authors="higorlucas"
RUN mkdir /c
WORKDIR /c

COPY pom.xml .
COPY src ./src

RUN mvn clean install

RUN mv target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
#ENTRYPOINT ["java","-jar","target/Desafio.api.transferencia-0.0.1-SNAPSHOT.jar"]


