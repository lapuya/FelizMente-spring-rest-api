## FelizMente - REST API 🚀
This project is the REST API that will allow the interaction between our different services and the database.
It was developed with Spring/Spring Boot using IntelliJ Idea

### Requirements 📋

* Internet connection
* XAMPP
* Java 11
* Postman (optional)

### Deployment 🔧
Steps:
* Turn on Apache and MySQL modules from XAMPP
* Click the admin button and create a schema called "felizmente"
* Running the project will create two tables: users and admins (users will be empty and admins will have default values)

### The service ✅
There are two types of endpoints that can be consumed with, for example, Postman.

#### Users
* GET:
  * http://localhost:8080/felizmente/users/{email} -> get a user by email
  * http://localhost:8080/felizmente/users/login/{token} -> get a user based on a token
  * http://localhost:8080/felizmente/users -> list all Users
* POST:
  * http://localhost:8080/felizmente/users -> register a user
* PUT:
  * http://localhost:8080/felizmente/users/{id} -> modify by ID
* DELETE:
  * http://localhost:8080/felizmente/users/{email} -> delete a user by email

#### Admin
* GET:
  * http://localhost:8080/felizmente/users/login/{token} -> get an admin based on a token

### References 🛠️
* [REST API](https://www.redhat.com/en/topics/api/what-is-a-rest-api)
* [Postman](https://www.blazemeter.com/blog/how-use-postman-manage-and-execute-your-apis)
* [JPA](https://www.ibm.com/docs/en/was-liberty/base?topic=overview-java-persistence-api-jpa)
* [XAMPP](https://www.educba.com/what-is-xampp/)
