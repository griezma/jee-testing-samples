# JEE Integration Test Samples

Minimalish integration test samples using JEE 8 (Jakarta/Java EE), Arquillian, Wildfly and Gradle. 

Every package is dedicated to a JEE technology (like cdi, persistence, jms etc.) and consists of a simple use case and self-contained integration test.

## Running integration tests
- `./docker-build.sh` (once) -- container "wildfly" is started
- Use your IDE or command line `gradle test --tests *younameit*` to explore selected tests.
- Observe the deployed resources in the "./shrinkwrap/" directory.
- You can trace the Wildfly logs in a terminal by `docker logs wildfly --follow`.

## Deploy as webapp
You can deploy everything as a webapp and tinker about if you feel like it.
- `./docker-build.sh` (once)
- `./start-wad.sh` -- container "wildfly-wad" is started
- Open http://localhost:8080/jeeit
- You can trace the Wildfly logs in a terminal by `docker logs wildfly-wad --follow`.

_Note:_ Deployed war lands in "./wad-deploy/".

---

## Requirements
* JDK 11+
* Gradle 6+
* Docker Desktop

<small>_IDE:_ I was using VSCode with Java Extension Pack. Other IDEs should be fine as well but I did not try.</small>
