# Game Marketplace System

The Game Marketplace System is a web application built using Spring Boot, Maven, Vaadin, and H2 Database. This project aims to create a robust and user-friendly platform for buying video games.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Build and Run](#build-the-project)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Database](#database)

<a id="prerequisites"></a>
## Prerequisites

Make sure you have the following installed on your system:

- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

<a id="getting-started"></a>
## Getting Started

<a id="getting-started"></a>
### Clone the Repository

```bash
git clone https://github.com/SashaKur/Game-Marketplace-System.git
cd Game-Marketplace-System
```

<a id="build-the-project"></a>
### Build and Run

Build the project using Maven

```bash
mvn clean unstall
```

Run the application

```bash
mvn spring-boot
```
Access the application at http://localhost:8080.

<a id="technologies-used"></a>
## Technologies used

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [H2 Database](https://www.h2database.com/html/main.html)

<a id="project-structure"></a>
## Project structure

```bash
├───frontend/
│   └───themes/
├───src/
|   └───main/
|       ├───java/
|       │   └───com/
|       │       └───oleksandr/
|       │           └───application/
|       │               ├───data/
|       │               │   ├───entity/
|       │               │   │   ├───employee/
|       │               │   │   └───enums/
|       │               │   ├───repository/
|       │               │   └───service/
|       │               ├───views/
|       |               ├───Application.java
|       |               └───DataInitializer.java
|       └───resources/
|           ├───application.properties
|           └───banner.txt
├──.gitignore
└───pom.xml
```
<a id="configuration"></a>
## Configuration
The application can be configured using the application.properties file located in the src/main/resources directory. Modify the properties to suit your needs.

<a id="database"></a>
## Database
The project uses H2 Database for development. You can access the H2 console at http://localhost:8080/h2-console with the following credentials:

- JDBC URL: jdbc:h2:mem:mp5
- Username: sa
- Password: (blank)



