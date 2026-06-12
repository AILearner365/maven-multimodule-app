# Secure multi-module Spring Boot Java 21 sample

A runnable Maven multi-module Java 21 Spring Boot application that demonstrates a clean module structure and practical OSS vulnerability guardrails.

The application is intentionally simple: it exposes a Task REST API backed by an in-memory service. The focus is on a project layout that you can import into IntelliJ, run immediately, and extend safely.

## Modules

```text
secure-multimodule-springboot-java21
├── task-common   # shared API response records
├── task-domain   # domain model and enums, no Spring dependency
├── task-service  # business service, Spring component
└── task-web      # Spring Boot REST API application
```

## Requirements

- JDK 21
- Maven 3.6.3 or newer
- IntelliJ IDEA with Maven support

## Import into IntelliJ

1. Open IntelliJ IDEA.
2. Select **File -> Open**.
3. Choose the root folder: `secure-multimodule-springboot-java21`.
4. Let IntelliJ import it as a Maven project.
5. Set Project SDK to Java 21.
6. Run `com.example.secureapp.web.TaskApplication` from the `task-web` module.

## Run from terminal

From the project root:

```bash
mvn clean package
java -jar task-web/target/task-web-1.0.0-SNAPSHOT.jar
```

Or, after installing the modules once:

```bash
mvn clean install
mvn -pl task-web spring-boot:run
```

## Try the API

Health check:

```bash
curl http://localhost:8080/actuator/health
```

List seeded tasks:

```bash
curl http://localhost:8080/api/tasks
```

Create a task:

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Patch vulnerable dependency","description":"Upgrade the affected component and regenerate the SBOM."}'
```

Show project OSS security posture:

```bash
curl http://localhost:8080/api/oss/security-posture
```

## OSS vulnerability guardrails included

### 1. Spring Boot managed dependencies

The project inherits from `spring-boot-starter-parent` and avoids explicit versions for Spring Boot managed dependencies. This helps prevent accidental version drift between Spring, Jackson, Tomcat, Micrometer, Hibernate Validator, and related libraries.

### 2. Maven Enforcer checks by default

The default build validates:

- Java 21 or newer
- Maven 3.6.3 or newer
- dependency convergence
- duplicate dependency declarations in POM files
- selected banned legacy or historically risky dependencies

The banned dependency list is intentionally small and should be expanded based on your company's policy.

### 3. Optional strict dependency profile

```bash
mvn clean verify -Pstrict-deps
```

Adds upper-bound dependency checks and release-dependency checks.

### 4. Optional SBOM generation

```bash
mvn clean verify -Psbom
```

Generates an aggregate CycloneDX Software Bill of Materials under the root `target` folder.

### 5. Optional vulnerability scan

```bash
mvn clean verify -Psecurity-scan
```

Runs an aggregate OWASP Dependency-Check scan and generates an SBOM.

The first Dependency-Check run can take a long time because it downloads vulnerability data from NVD. For CI/CD, prefer an internal NVD mirror or cache.

### 6. Suppression file for false positives

The file `security/dependency-check-suppressions.xml` is present but empty. Add entries only for confirmed false positives, and always include evidence and an expiry date.

## Common commands for OSS investigation

Show all dependencies:

```bash
mvn dependency:tree
```

Find where a dependency came from:

```bash
mvn dependency:tree -Dincludes=groupId:artifactId
```

Run normal verification:

```bash
mvn clean verify
```

Run verification plus security scan:

```bash
mvn clean verify -Psecurity-scan
```

## Notes

- This sample does not intentionally include vulnerable dependencies.
- Do not add old vulnerable libraries just to test scanning in a project that may be reused in real work.
- For training purposes, create a separate isolated lab project if you want to see scanners report known CVEs.
