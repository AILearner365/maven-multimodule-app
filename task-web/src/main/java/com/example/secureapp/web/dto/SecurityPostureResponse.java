package com.example.secureapp.web.dto;

import java.util.List;

public record SecurityPostureResponse(
        String javaVersion,
        String dependencyManagement,
        List<String> defaultGuardrails,
        List<String> optionalProfiles
) {
}
