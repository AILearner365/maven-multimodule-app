package com.example.secureapp.web.controller;

import com.example.secureapp.web.dto.SecurityPostureResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/oss")
public class SecurityPostureController {
    @GetMapping("/security-posture")
    public SecurityPostureResponse securityPosture() {
        return new SecurityPostureResponse(
                Runtime.version().toString(),
                "Spring Boot parent BOM manages supported direct and transitive dependency versions.",
                List.of(
                        "No intentionally vulnerable runtime dependencies are included.",
                        "Maven Enforcer requires Java 21 and Maven 3.6.3 or newer.",
                        "Maven Enforcer blocks selected legacy or historically risky libraries.",
                        "The application exposes only health and info actuator endpoints by default."
                ),
                List.of(
                        "-Psbom generates an aggregate CycloneDX SBOM.",
                        "-Psecurity-scan runs an aggregate OWASP Dependency-Check scan and SBOM generation.",
                        "-Pstrict-deps adds upper-bound and release-dependency checks."
                )
        );
    }
}
