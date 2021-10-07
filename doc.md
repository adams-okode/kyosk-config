# Kyosk Config Service 
---

## What's Available
- Kyosk Config Service - Build using Spring Boot 2.5.5
- DockerFile
- k8s manifest - arch.yaml
- docker-compose file for quick run and test
- samle data - sample.json

## How To Run (Linux/Mac)

---
#### Requirements
-  Mongo DB
-  Maven
-  Java 11

After Cloning the project navigate to the project folder and
export the required environment variables

```shell
export SERVE_PORT={YOUR_PORT}
export AUTH_DB=admin
export DB_HOST=localhost
export DB_PASS=example
export DB_USER=root
export DB_PORT=27017
```

and run 

```shell
mvn spring-boot:run 
```
> Swagger is available at http://localhost:{your-port}/swagger-ui.html

### Run on k8s (minikube)

---
### Requirements
- Minikube
- Docker
---
Find the run.sh in the root of the project
give executable permissions
i.e.

``` chmod +x ./run.sh```

and finally 

```./run.sh```

You should see the output belo copy the url and open on the browser
```
deployment.apps/kyosk-deployment created
statefulset.apps/kyosk-mongodb-standalone created
service/kyosk-mongodb-standalone-database created
service/kyosk-deployment-service created
😿  service default/kyosk-deployment-service has no node port
🏃  Starting tunnel for service kyosk-deployment-service.
|-----------|--------------------------|-------------|------------------------|
| NAMESPACE |           NAME           | TARGET PORT |          URL           |
|-----------|--------------------------|-------------|------------------------|
| default   | kyosk-deployment-service |             | http://{host}:{port} |
|-----------|--------------------------|-------------|------------------------|
``````

> A docker compose is also available to quicky run the project 


