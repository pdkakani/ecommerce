# Spring boot sample 'Ecommerce' project

This is a simple Java/Maven/Spring boot application with one endpoint simulating the checkout functionality of a small scale
ecommerce application

## How to Run

This application is packaged as a jar which has Tomcat embedded. You run it using the ```java -jar``` command.

* Clone this repository
* Make sure you are using at least JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```

## About the service

The service is a simple POST endpoint where you can pass a list of watch ids you want to purchase and it returns the
total final price with discount. It uses an in-memory database (H2) as a source of data. To check if the application is
up for the requests to be served, you can do a health check by calling ```localhost:8080/actuator/health```

## Approach

* Considering the nature of the task, the very first thought was about the data source for the application. H2 was the
first choice because of following reasons,

  * No extra setup as it comes embedded with spring boot
  * Easy initial loading of data


* Second step, was to set up the database with some sample data and outline the skeleton of application. Here outlining
refers to structure creation (packages, classes etc) and creation of POJO's.


* Third step, implementation of business logic along with creation of endpoint in contoller


* Lastly, adding unit and integration tests

## What can be improved

* Spring Profiles: Profiles can be used to separate out PROD and TEST env configurations


* Monitoring: Spring actuator can provide much more than just health check. Can use other info and metrics for monitoring


* Security: Spring security can provide security for the application. At least, a simple login functionality can be implemented for a small scale application.


* Logging: Currently, logs are available on ```stdout```. It can be improved to be either logged in a file structure OR better to be pushed on to the cloud (ex: AWS cloudwatch)

