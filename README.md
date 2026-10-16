# Calculator Lab 5

[![Calculator CI](https://github.com/Kai-Junhan/calculator-lab5/actions/workflows/ci-calculator.yml/badge.svg?branch=main)](https://github.com/Kai-Junhan/calculator-lab5/actions/workflows/ci-calculator.yml)

A calculator project from Labs 3 and 4, developed locally with Java 21 and compiled against a Java 17 compatibility baseline. GitHub Actions compiles the project and runs the JUnit 5 test suite on every push to `main` and every pull request targeting `main`.

## Local verification

```text
mvn clean compile
mvn test
```

The Lab 4 test suite contains 10 JUnit 5 tests. The workflow uses a JDK 17 and JDK 21 matrix, Maven dependency caching, compilation, and tests.

The badge points to the `main` branch workflow in the GitHub repository.
