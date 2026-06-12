# OSS dependency policy used by this sample

This sample is intentionally safe by default. It does not include intentionally vulnerable dependencies. The project demonstrates how to structure a Spring Boot Maven build so common OSS vulnerability scenarios are easier to prevent, detect, and fix.

## Default rules

1. Prefer Spring Boot managed dependencies. Do not add versions for Spring libraries, Jackson, Tomcat, Micrometer, Hibernate Validator, or other libraries already managed by the Spring Boot parent BOM.
2. Avoid adding a direct dependency when a Spring Boot starter already provides a tested set of transitive dependencies.
3. Keep the root build free of custom repositories unless there is a documented business reason.
4. Keep `security/dependency-check-suppressions.xml` empty unless a finding is confirmed as a false positive.
5. Do not suppress a real CVE. Upgrade, remove, or replace the component.

## When an OSS vulnerability is found

1. Run `mvn dependency:tree -Dincludes=groupId:artifactId` to identify whether the vulnerable component is direct or transitive.
2. Prefer upgrading Spring Boot first, because the Boot BOM manages a compatible dependency set.
3. If Spring Boot has not released a fix yet, add a temporary dependency override in the root `dependencyManagement` section and test carefully.
4. If the vulnerable library comes through a dependency that you do not need, exclude it at the dependency that introduced it.
5. Regenerate the SBOM with `mvn verify -Psbom` and rerun `mvn verify -Psecurity-scan`.
