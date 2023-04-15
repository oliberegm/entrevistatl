# Building, Run and Test API

## Building Container Microservices

### Build Spring boot
```bash
$ mvn clean test package
```
### Generate container docker
```bash
$ docker run -p 5000:5000 api-currency
```
### Postman Collection
```json
curl --location --request GET 'localhost:5000/allExchangeRate'
curl --location --request GET 'localhost:5000/exchangeRate/EUR/USD'
```






