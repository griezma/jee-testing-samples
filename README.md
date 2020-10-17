# JEE Integration Test Samples

Minimalish integration test samples using JEE 8 (Jakarta/Java EE), Arquillian, Wildfly and Gradle. 

Every package is dedicated to a JEE technology (like cdi, persistence, jms etc.) and consists of a simple use case and self-contained integration test.

## Running integration tests
- `./docker-build.sh` (once) ... container "wildfly" is started
- Use your IDE or command line `gradle test --tests *younameit*` to explore selected tests.
- Observe the deployed resources in the "./shrinkwrap/" directory.
- You can trace the Wildfly logs in a terminal by `docker logs wildfly --follow`.

_IDE Note:_ I was using VSCode, Java Extension Pack and WSL 2 Remote Server.


## Requirements
* JDK 11+
* Gradle 6+
* Docker Desktop


