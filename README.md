# Calculator Labs 5 and 6

[![Calculator CI](https://github.com/Kai-Junhan/calculator-lab5/actions/workflows/ci-calculator.yml/badge.svg?branch=main)](https://github.com/Kai-Junhan/calculator-lab5/actions/workflows/ci-calculator.yml)

A calculator project from Labs 3 and 4, developed locally with Java 21 and compiled against a Java 17 compatibility baseline. GitHub Actions compiles the project and runs the JUnit 5 test suite on every push to `main` and every pull request targeting `main`.

## Local verification

```text
mvn clean compile
mvn test
```

The Lab 4 test suite contains 10 JUnit 5 tests. The workflow uses a JDK 17 and JDK 21 matrix, Maven dependency caching, compilation, and tests.

The badge points to the `main` branch workflow in the GitHub repository.

## Lab 6 - executable JAR and Docker

The original calculator and its 10 tests are retained. `CalculatorCli` adds a
four-operation demo when no arguments are supplied, or evaluates three arguments.
Five CLI tests cover the demo, arguments, and error handling.

```powershell
mvn -B clean package
java -jar target/calculator.jar
docker build -t calculator:v1 .
docker images calculator
docker run --rm calculator:v1
docker run --rm calculator:v1 12 + 3
docker run --rm calculator:v1 12 - 3
docker run --rm calculator:v1 12 '*' 3
docker run --rm calculator:v1 12 / 3
```

Quote `'*'` to prevent wildcard expansion in shells. Invalid expressions return
exit code 1; the wrong number of arguments returns 2. This is a short-lived CLI
container, so successful completion exits immediately; `docker ps` does not show
it after exit and `--rm` removes it.

The multi-stage Dockerfile uses official Maven/OpenJDK 17 to run `mvn package`
(including tests), then copies only `calculator.jar` into an Eclipse Temurin 17
JRE image. Temurin is an OpenJDK distribution. The runtime runs as UID 10001.
The build tools and Maven cache stay out of the final image.

Run automated container checks in PowerShell:

```powershell
./scripts/Verify-Container.ps1
```

### Docker Hub

Create a **public** repository called `calculator` in your Docker Hub account.
Replace `YOUR_DOCKERHUB_NAME` below with your actual username. Enter credentials
only through Docker's login prompt, never in this repository.

```powershell
docker login
docker tag calculator:v1 YOUR_DOCKERHUB_NAME/calculator:lab6
docker push YOUR_DOCKERHUB_NAME/calculator:lab6
docker pull YOUR_DOCKERHUB_NAME/calculator:lab6
docker run --rm YOUR_DOCKERHUB_NAME/calculator:lab6
```

The image page is `https://hub.docker.com/r/YOUR_DOCKERHUB_NAME/calculator/tags`.
Capture the page showing the `lab6` tag for the report.

### Continuous integration

The existing `.github/workflows/ci-calculator.yml` filename is retained to preserve
the Lab 5 badge and workflow history. Both Java 17 and Java 21 compile, dependency
analysis, and JUnit steps remain. A new `docker-build` job waits for both Java jobs
to pass, builds `calculator:v1`, checks all four operations, and verifies the
division-by-zero message and exit code. Automatic Docker Hub push is optional
and is not enabled, so no Docker Hub secrets are needed for CI.

### Local Docker Hub connectivity workaround

If Docker Hub image pulls time out, these same official images are available
through the Docker Official Images mirror on Amazon ECR Public:

```powershell
docker pull public.ecr.aws/docker/library/maven:3.9.11-eclipse-temurin-17
docker pull public.ecr.aws/docker/library/eclipse-temurin:17-jre-jammy
docker tag public.ecr.aws/docker/library/maven:3.9.11-eclipse-temurin-17 maven:3.9.11-eclipse-temurin-17
docker tag public.ecr.aws/docker/library/eclipse-temurin:17-jre-jammy eclipse-temurin:17-jre-jammy
docker build -t calculator:v1 .
```

This seeds the local image cache and keeps the Dockerfile usable on GitHub-hosted
runners. Pushing your own image still requires access to Docker Hub.
