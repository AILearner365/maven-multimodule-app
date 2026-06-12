package com.example.secureapp.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
        @NotBlank(message = "title is required")
        @Size(max = 120, message = "title must be 120 characters or fewer")
        String title,

        @Size(max = 1000, message = "description must be 1000 characters or fewer")
        String description
) {
}
