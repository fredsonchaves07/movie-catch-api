# movie-catch-api

[![CI/CD for Prod Heroku](https://github.com/fredsonchaves07/movie-catch-api/actions/workflows/prod.yml/badge.svg?branch=main)](https://github.com/fredsonchaves07/movie-catch-api/actions/workflows/prod.yml)

🍿 create, manage and rate movies and series api


## 📌 Content

- [About](#-about)
- [Technology](#-technology)
- [Installation and Configuration](#%EF%B8%8F-installation-and-configuration)
    - [Running with Docker](#running-with-docker)
    - [Installation of Dependencies](#installation-of-dependencies)
    - [Running application tests](#running-application-tests)
- [Running the Application](#%EF%B8%8F-running-the-application)
- [Issues](#-issues)
- [Contribution](#-contribution)
- [License](#%EF%B8%8F-license)

## 🚀 About

This repository contains the source code of the api that manages lists of movies and series. The technologies used are
described in Technology. The implementation project of this api can be
consulted [here](https://github.com/fredsonchaves07/movie-catch-api/projects/1)

## 💻 Technology

- [Java 17](https://www.java.com/en/)
- [Maven](https://maven.apache.org/)
- [Spring Framework](https://spring.io/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate](https://hibernate.org/)
- [H2Database](https://www.h2database.com/html/main.html)
- [Postgres](https://www.postgresql.org/)
- [Junit5](https://junit.org/junit5/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Springfox](https://springfox.github.io/springfox/docs/current/)
- [Flyway](https://flywaydb.org/)

## 🛠️ Installation and Configuration

To execute the project in a development environment, it is necessary to have the tools installed. Can be consulted in
the technology section

### Running with Docker

This project can also be run by docker. You must have docker installed.
Run the command

```bash
docker-compose up --build
```

Access the api with the url [localhost:8080/api/v1](localhost:8080/api/v1)

### Installation of Dependencies

Run the command to perform the dependency installation

```bash
mvn clean install
```

### Running application tests

To run the application tests it is necessary to change the profile to 'test'. Create an .env file based
on [.env-example](https://github.com/fredsonchaves07/movie-catch-api/blob/main/.env-example)

```properties
PROFILE_ACTIVE=test
DB_USERNAME=postgres
DB_PASSWORD=1234
```

Run the command

```bash
mvn test
```

## ⚙️ Running the Application

Create the database `moviecatchdb` and then change the environment variable database credentials. Check
example [.env-example](https://github.com/fredsonchaves07/movie-catch-api/blob/main/.env-example)

```properties
PROFILE_ACTIVE=dev
DB_USERNAME=postgres
DB_PASSWORD=1234
```

This application uses flyway as a database migration tool.
Scripts are located [here](https://github.com/fredsonchaves07/movie-catch-api/tree/main/src/main/resources/db/migration)

Run Application in development mode after installation and configuration

```bash
mvn spring-boot:run
```

Access the api with the url [localhost:8080/api/v1](localhost:8080/api/v1)

## 🐛 Issues

I would love to review your pull request! Open a new [issue](https://github.com/fredsonchaves07/movie-catch-api/issues)

## 🤝 Contribution

Feel free to contribute to the project. I am open for suggestions.
Click [here](https://github.com/fredsonchaves07/movie-catch-api/issues) to open a new issue or take part in the
development [project](https://github.com/fredsonchaves07/movie-catch-api/projects/1) 😄

## ⚖️ License

This project uses MIT License. Click [here](https://github.com/fredsonchaves07/movie-catch-api/blob/main/LICENSE) to
access

---
Developed 💙 by Fredson Chaves
