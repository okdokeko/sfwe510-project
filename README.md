# sfwe510-project
Course project for SFWE 510.

# Installation Requirements
* Docker
* Maven

# Config repo:
https://github.com/okdokeko/config

# Setup
add keycloak to your hosts file:
```bash
echo "127.0.0.1 keycloak" | sudo tee -a /etc/hosts
```

# How to run:
```bash
$ cd sfwe510-project
```

Run in the dev profile:
```bash
$ docker compose up --build
```

Run replicas:
```bash
$ docker compose up -d --build --scale productservice=3
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
docker compose logs -f productservice orderservice
```

To check profile (from products service for example):
```bash
docker compose logs productservice | grep "profiles are active"
```

# Security
Everything goes through the gateway and use bearer token.

## Users
ds_admin - admin123 - ADMIN - all operations
ds_user - user123 - USER - read operations create orders, default

Expected: no token 401, USER writing a product 403, ADMIN writing a product 201.

## Registration/Password reminder
1. Use: http://keycloak:8080/auth/realms/samazon-realm/account
2. Click forgot password
3. Check mailpit: http://localhost:8025

## Bearer token propagation
The gateway forwards the `Authorization` header.
### To get a token
Use postman, there are calls for tokens.

# Service discovery
Eureka tracks and registers all instances.

# Resilience
* Circuit breaker used on order service calls to product service.
* Gateway retries failed requests on another instance.

# Stress testing
Run stress test:
```bash
cd gatling
docker run --rm --network host -v "$PWD":/src -v "$HOME/.m2":/root/.m2 -w /src \
  maven:3.8-eclipse-temurin-11 mvn -q gatling:test
```

Check call distribution:
```bash
for c in $(docker compose ps -q productservice); do
  echo "$c: $(docker logs $c 2>&1 | grep -ci 'select')"
done
```

Check resilience (kill instance mid stress test):
```bash
docker kill $(docker compose ps -q productservice | head -1)
```

# Spec
## Ports
* 8072 - Gateway (only entry point for the API)
* 8080 - Keycloak
* 8070 - Eureka
* 8089 - Config Server
* 8025 - Mailpit

# Endpoints
* Orders Service - localhost:8072/samazon/orders/{ids}
* Products Service - localhost:8072/samazon/products/{ids}

# MD
Maintainer: @okdokeko (Samuel Moreno)