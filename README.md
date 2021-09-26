# wealth-management-service

This is a Kotlin + Spring Boot service that expose a REST API to upload customer and strategy csv file and also to trigger the re-balance customer portfolio process.

## Compromises
Due to the time limitation of 4 hours this of course is not a production ready service. Below you can find the compromises made:
- No tests at all.
- The current implementation of parsing the csv is not optimal for big files. The entry point for process the files is a blocking REST endpoint, but it shouldn't be the case for a real production application.
- No transactions.
- No parallel calls to get customer portfolio data.
- DB credentials management.
- Re-balance process is triggered from a blocking REST Endpoint.
- No Swagger documentation for REST API.

I'll be more than happy to discuss further improvements during the technical interview.

## How to run the service?

It's necessary to have docker and java 11 installed.

1. Checkout the project.
2. Access the project folder
3. There is a docker-compose.yml file to lunch the required DB. Run `docker-compose up -d` to run it detached from the console.
4. Update inside the `application.yaml` portfolio-service.url property value with the proper URL for portfolio service.
5. Execute `mvn spring-boot:run` after the docker image is running.

This service use flyway to set up the database structure required.

Once the service is up and running you can check the health endpoint exposed by Actuator `curl -X GET http://localhost:8080/actuator/health`

### Process customer.csv file

- Send a POST request to `/customer` endpoint with the following command `curl -X POST -F 'customerCsv=@customers.csv' http://localhost:8080/customer`. You can replace the csv file with a different file in another location

### Process strategy.csv file

- Send a POST request to `/strategy` endpoint with the following command `curl -X POST -F 'strategyCsv=@strategy.csv' http://localhost:8080/strategy`. You can replace the csv file with a different file in another location

### Re-balance customers portfolio

- Send a PUT request to `/customer/portfolio` endpoint with the following command `curl -X PUT http://localhost:8080/strategy`

