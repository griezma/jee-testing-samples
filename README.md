# JEE Integration Test Samples

A collection of integration test samples using JEE (Jakarta/Java EE), Arquillian, Wildfly and Gradle.

Each package is dedicated to a JEE technology (cdi, persistence, jms etc.) and contains of a sample with integration tests.

## Running individual tests
- `./docker-build.sh` (once) -- creates and starts docker container "wildfly"
- Explore selected tests using an IDE or command line `gradle test --tests *younameit*`
- Test deployments exposed in "./shrinkwrap/"
- Follow Wildfly logs in a terminal: `docker logs wildfly --follow`
- Watch and Deploy development: `./start-wad.sh`

---

## Requirements
* JDK 11+
* Gradle 6+
* Docker Desktop
