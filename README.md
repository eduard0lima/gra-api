# Golden Raspberry Awards API

API REST em Java 25 e Spring Boot 3 para importar o CSV do Golden Raspberry Awards e retornar os produtores com menor e maior intervalo entre prêmios consecutivos.

O arquivo `src/main/resources/Movielist.csv` é carregado automaticamente na inicialização e persistido em um banco H2 em memória.

## Requisitos

- Java 25
- Maven 3.9+

## Executar a aplicação

```bash
mvn spring-boot:run
```

## Executar os testes

```bash
mvn clean test
```

## Endpoint

```http
GET /api/v1/producers/award-intervals
```

## Exemplo

```bash
curl http://localhost:8080/api/v1/producers/award-intervals
```

Resposta:

```json
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    }
  ]
}
```
