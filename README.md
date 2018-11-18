# Transfer Money API
## API Spec
XXXXXX
## Environment
### Database: 
Embeded H2 <br>
initialize data from <br>
/src/main/resources/data.sql<br>
###Authentication: 
Basic Authentication
## Test
> mvn test
Apart from running unit tests, it is also run the intergation test which is defined in
/src/test/java/net/leolee/transfermoneyapi/integrationtest/TransferMoneyApiIntegrationTest.java
## Build
> mvn package<br>
The artifact will be generated in<br> 
target/transfer-money-api-0.0.1-SNAPSHOT.jar
## Run
> java -jar target/transfer-money-api-0.0.1-SNAPSHOT.jar<br>
A port 8080 is open in localhost

### Examples
To submit authorized requests, add the below head in the request
> Authorization: Basic c3lzdGVtOmFiYzEyMw==

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
To make it more productive grade, the following can be improved.
* use a standalone database, like MySQL instead of embeded dB
* use a more secure authentication mechanism, like JWT 
* enable HTTPS
* encrypt application.properties file
