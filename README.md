# Store Stoom - Test CRUD

This is a CRUD application project developed in Java with Spring Boot, designed to manage products with category, brand, and price information. The application provides endpoints for creating, reading, updating, and deleting products.

## Prerequisites

Before you begin, make sure you have the following prerequisites installed:

- [Docker](https://www.docker.com/) 
- [Java](https://www.oracle.com/java/technologies/javase-downloads.html): Ensure that Java is installed on your machine.
- [Maven](https://maven.apache.org/): You will need Maven to compile and run the project.
- [Swagger](http://localhost:8080/swagger-ui/): The application uses Swagger to provide interactive documentation of endpoints, which can be accessed via the link: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/).

## Database

The application uses the H2 database, an in-memory database, to store data. You can access the H2 administration console using the following information:

- Console URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:test
- User: sa
- Password: sa

## Usage

To use the application, follow the setup and execution instructions below:

1. Clone this repository:

   ```bash
   git clone git@github.com:ricardossbr/teste-stoom.git


2. Navigate to the project directory:

   ```bash
   docker network create --driver=bridge stoom-store-network



3. Navigate to the project directory:

   ```bash
   cd /teste-stoom

4. Compile and run the project using Maven:

   ```bash
    docker compose up --build

5. Access the Swagger interactive documentation to explore the API endpoints:
http://localhost:8080/swagger-ui/
6. A `stoom-store-collection-postman.json` file is available in the root directory of the project, which is a Postman collection for testing the API endpoints.

Now, you are ready to start using the CRUD application to manage products with category, brand, and price.

## Contribution
If you wish to contribute to this project, feel free to submit pull requests or report issues. Your contributions are welcome!

## License
Distributed under the MIT License. See `LICENSE.txt` for more information.



