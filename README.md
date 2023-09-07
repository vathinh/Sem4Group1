# Sem4Group1
# Spring Boot Microservices

## Run the application using Docker

## Guide for Developers
# Developer's Guide: Setting up the Project

## Prerequisites
Before getting started, ensure you have the following prerequisites in place:

- Docker
- Java Development Kit (JDK)
- Integrated Development Environment (IDE) of your choice

## Docker Compose Setup
To create the database server and other necessary containers, follow these steps:

1. In folder sem4Infras create a docker script for database server.

## Spring Boot Project Setup
Setting up your Spring Boot project involves several steps:

1. Create the necessary packages for your model, DTO, repository, service, and controller.
2. Implement your application's logic in the respective components.
3. Add application properties to configure data source settings like URL, username, and password.

## Implement Pagination and Specification
In your service implementation, ensure you implement pagination for retrieving data. Additionally, consider using specifications to filter data efficiently.

1. For API Testing cd sem4Infras -> docker-compose up -d to run the db sever(using hibernate auto for auto create table)
2. Start the module



## Phrase 2
1. Run `mvn clean package -DskipTests` to build the applications and create the docker image locally.
2. Go to CMD and Run `cd sem4Infras`
3. Run `docker-compose up -d` to start the applications.

