# Transfer Money API
## Environment
### Database: 
Embeded H2 <br>
initialize data from <br>
/src/main/resources/data.sql<br>
###Authentication: 
Basic Authentication
## API Spec
XXXXXXXX
## Test
> mvn test
Apart from running unit tests, it is also run the intergation test which is defined in
/src/test/java/net/leolee/transfermoneyapi/integrationtest/TransferMoneyApiIntegrationTest.java
## Build
> mvn package
The artifact will be placed in 
target/transfer-money-api-0.0.1-SNAPSHOT.jar
## Run
> java -jar target/transfer-money-api-0.0.1-SNAPSHOT.jar
A port 8080 is open in localhost

### Examples
To submit authorized requests, 
Authorization
Basic c3lzdGVtOmFiYzEyMw==

## To Do list
To make it more productive grade, the following can be improved.
* use a standalone database, like MySQL instead of embeded dB
* use a more secure authentication mechanism, like JWT 
* enable HTTPS
