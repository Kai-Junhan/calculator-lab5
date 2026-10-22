# FROM selects the official Maven image with an OpenJDK 17 build environment.
FROM maven:3.9.11-eclipse-temurin-17 AS build
# WORKDIR sets the directory for subsequent build instructions.
WORKDIR /build
# COPY adds the Maven project descriptor to the build stage.
COPY pom.xml .
# COPY adds the application and JUnit test source files.
COPY src ./src
# RUN compiles the code, runs the tests, and packages the executable JAR.
RUN mvn -B --no-transfer-progress package

# FROM starts a smaller runtime stage containing the JRE instead of Maven/JDK.
FROM eclipse-temurin:17-jre-jammy
# WORKDIR sets the runtime directory.
WORKDIR /app
# COPY transfers only the packaged application from the build stage.
COPY --from=build /build/target/calculator.jar ./calculator.jar
# USER runs the application without root privileges.
USER 10001:10001
# ENTRYPOINT always launches the calculator; docker run arguments follow it.
# CMD would supply default arguments; it is omitted because no arguments run a demo.
ENTRYPOINT ["java", "-jar", "/app/calculator.jar"]
