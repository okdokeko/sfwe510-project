# sfwe510-project
Course project for SFWE 510.

# Installation Requirements
* Docker
* Maven

# Config repo:
https://github.com/okdokeko/config

# How to run:
```bash
$ cd sfwe510-project
```

Run in the dev profile:
```bash
$ docker compose up --build
```

For logs
```bash
docker compose logs -f productservice orderservice
```
Run in prod profile:
```bash
$ PROFILE=prod docker compose up --build
```

To terminate:
```bash
ctrl + c
```

If running in the background:
```bash
docker compose down
# or if that doesnt work, force quit
docker kill $(docker ps -q)
```
Get logs;
```bash
docker compose logs -f productservice
```

To check profile (from products service for example):
```bash
curl -s localhost:8088/actuator/env | grep -o '"activeProfiles":\[[^]]*\]'
```

# Spec
## Ports
* 8089 - Config Server
* 8088 - Product Service
* 8087 - Order Service
* 5432 - Database Port

# Endpoints
* Orders Service - localhost:8087/samazon/orders/{ids}
* Products Service - localhost:8088/samazon/products/{ids}

# MD
Maintainer: @okdokeko (Samuel Moreno)