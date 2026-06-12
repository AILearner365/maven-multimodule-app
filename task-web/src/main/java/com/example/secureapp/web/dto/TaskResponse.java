package com.example.secureapp.web.dto;

import com.example.secureapp.domain.Task;
import com.example.secureapp.domain.TaskStatus;

import java.time.Instant;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.id(),
                task.title(),
                task.description(),
                task.status(),
                task.createdAt(),
                task.updatedAt()
        );
    }
}
