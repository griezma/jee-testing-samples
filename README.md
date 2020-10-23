# JEE Integration Test Samples

A collection of integration test samples using JEE 8 (Jakarta/Java EE), Arquillian, Wildfly and Gradle. 

Each package is dedicated to a JEE technology (cdi, persistence, jms etc.) and contains simple integration tests.

## Running individual tests
- `./docker-build.sh` (once) -- container "wildfly" is started
- Explore selected tests using an IDE or command line `gradle test --tests *younameit*`
- Test deployments exposed in "./shrinkwrap/"
- Trace Wildfly logs by `docker logs wildfly --follow`.

---

## Requirements
* JDK 11+
* Gradle 6+
* Docker Desktop
