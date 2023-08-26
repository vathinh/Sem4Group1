# Sem4Group1
# Spring Boot Microservices

## How to run the application using Docker

1. Run `mvn clean install -DskipTests -pl common-service` to build common-service first.
2. Run `mvn clean package -DskipTests -pl !common-service` to build the applications and create the docker image locally.
3. Run `docker-compose up -d` to start the applications.

## How to run the application without Docker

1. Run `mvn clean verify -DskipTests` by going inside each folder to build the applications.
2. After that run `mvn spring-boot:run` by going inside each folder to start the applications.
