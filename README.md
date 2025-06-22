# JUtils

JUtils is a small Spring Boot based library that wraps common tasks when interacting with REST style HTTP APIs using `WebClient`.

## Features

- **`ApiService`** – provides convenience methods for performing `GET`, `POST` and `PUT` calls. Each call returns an [`ApiCallResult`](src/main/java/com/baerchen/jutils/runtime/entity/ApiCallResult.java) containing the deserialised response wrapped in a [`ValidationReport`](src/main/java/com/baerchen/jutils/runtime/control/ValidationReport.java).
- **Query parameter handling** via [`ApiQueryParams`](src/main/java/com/baerchen/jutils/runtime/control/ApiQueryParams.java) with optional validation of required keys.
- **Exception mapping** using [`WebClientErrorBuilder`](src/main/java/com/baerchen/jutils/runtime/control/WebClientErrorBuilder.java) that turns HTTP 4xx and 5xx responses into custom exceptions.
- **Aspect based logging** with [`@Loggable`](src/main/java/com/baerchen/jutils/runtime/control/Loggable.java) and [`LoggingAspect`](src/main/java/com/baerchen/jutils/runtime/control/LoggingAspect.java). When enabled it logs parameters, result and execution time and puts a `step` value into the MDC for better traceability.
- **Credential helpers** like [`ApiCredentials`](src/main/java/com/baerchen/jutils/runtime/entity/ApiCredentials.java) and [`WebClientProviderWithApiKey`](src/main/java/com/baerchen/jutils/runtime/control/WebClientProviderWithApiKey.java) to validate the presence of required API keys.
- Utility interfaces such as [`Validable`](src/main/java/com/baerchen/jutils/runtime/control/Validable.java), [`Mappable`](src/main/java/com/baerchen/jutils/runtime/entity/Mappable.java) and [`RequestBuilder`](src/main/java/com/baerchen/jutils/runtime/boundary/RequestBuilder.java).

## Building and Testing

The project uses Maven with the wrapper scripts provided in this repository. To build and execute the (minimal) test suite run:

```bash
./mvnw test
```

Ensure you are using Java 21 as defined in the [`pom.xml`](pom.xml).

## Running

As this module mainly provides utilities it does not expose a REST API of its own. The main entry point [`JUtilsApplication`](src/main/java/com/baerchen/jutils/JUtilsApplication.java) starts a Spring Boot context so the utilities can be wired and tested inside a Spring environment:

```bash
./mvnw spring-boot:run
```

The application contains no controllers; starting it will simply initialise the Spring context.

## License

This project is provided under the MIT License. See the [LICENSE](LICENSE) file for details if present.
