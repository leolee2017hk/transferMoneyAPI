# Transfer Money API
## API Specification
[/api/api.raml](/api/api.raml)
### Error Code
| Error Code | Desc                                       |
|------------|--------------------------------------------|
| E000       | Bad Request                                |
| E001       | Account not found                          |
| E002       | Transferred Amount is not valid            |
| E003       | Insufficient Balance                       |
| E004       | Same Account Transfer                      |
| E101       | Access denied                              |
| E901       | Media type is not supported                |
| E902       | No handler is found to support the request |
| E999       | System Exception                           |

## Environment
### Database: 
Embeded H2 <br>
initialize data from <br>
[/src/main/resources/data.sql](/src/main/resources/data.sql)
<br>
### Required Software to build and run the examples
* jdk 1.8
* Apache Maven 3.5
* Curl

### Authentication: 
Basic Authentication

### Third Party Libraries being used
Specified in the dependency section in [pom.xml](pom.xml)

## Test
```
mvn test 
```
Apart from running unit tests, the command also runs the intergation test which is defined in <br>
[/src/test/java/net/leolee/transfermoneyapi/integrationtest/TransferMoneyApiIntegrationTest.java](/src/test/java/net/leolee/transfermoneyapi/integrationtest/TransferMoneyApiIntegrationTest.java)
## Build
```
mvn package
```
The artifact will be generated in 
*target/transfer-money-api-0.0.1-SNAPSHOT.jar*

## Run
```
java -jar target/transfer-money-api-0.0.1-SNAPSHOT.jar
```
A port 8080 is open in localhost

### Examples
To submit authorized requests, add the below header in the http request
```
Authorization: Basic c3lzdGVtOmFiYzEyMw==
```

1. Show the balance of account <A10001>
```
curl -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Basic c3lzdGVtOmFiYzEyMw==" http://localhost:8080/transferMoneyApi/v1/account/A10001/balance
```
result:
```
{
  "accountNo" : "A10001",
  "balanceAmt" : 10000.00,
  "retrievalDateTime" : "2018-11-18 21:17"
}
```
2. Show the balance of account <A10002>
```
curl -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Basic c3lzdGVtOmFiYzEyMw==" http://localhost:8080/transferMoneyApi/v1/account/A10002/balance
```
result:
```
{
  "accountNo" : "A10002",
  "balanceAmt" : 20000.00,
  "retrievalDateTime" : "2018-11-18 21:17"
}
```
3. Transfer $300 from account <A10001> to account <A10002>
```
curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Basic c3lzdGVtOmFiYzEyMw==" -d "{\"toAccountNumber\":\"A10002\",\"amount\": 3000}" http://localhost:8080/transferMoneyApi/v1/account/A10001/transferMoney
```
result: balance of account <A10001>
```
{
  "accountNo" : "A10001",
  "balanceAmt" : 7000.00,
  "retrievalDateTime" : "2018-11-18 21:22"
}
```
4. Show the balance of account <A10002>
```
curl -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Basic c3lzdGVtOmFiYzEyMw==" http://localhost:8080/transferMoneyApi/v1/account/A10002/balance
```
result:
```
{
  "accountNo" : "A10002",
  "balanceAmt" : 23000.00,
  "retrievalDateTime" : "2018-11-18 21:22"
}
```
## To Do list
To make it more production-grade, the followings can be improved.
* use a standalone database, like MySQL instead of embeded dB
* use a more secure authentication mechanism, like JWT 
* enable HTTPS
* encrypt application.properties file
