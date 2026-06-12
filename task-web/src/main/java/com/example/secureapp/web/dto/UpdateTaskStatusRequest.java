package com.example.secureapp.web.dto;

import com.example.secureapp.domain.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(
        @NotNull(message = "status is required")
        TaskStatus status
) {
}
