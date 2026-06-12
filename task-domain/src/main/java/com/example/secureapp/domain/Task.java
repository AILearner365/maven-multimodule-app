package com.example.secureapp.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Task(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public Task {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(status, "status must not be null");
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        Objects.requireNonNull(updatedAt, "updatedAt must not be null");
        title = title.trim();
        description = description == null ? "" : description.trim();
    }

    public static Task newTask(String title, String description) {
        Instant now = Instant.now();
        return new Task(UUID.randomUUID(), title, description, TaskStatus.TODO, now, now);
    }

    public Task withStatus(TaskStatus newStatus) {
        return new Task(id, title, description, newStatus, createdAt, Instant.now());
    }

    public Task withDetails(String newTitle, String newDescription) {
        return new Task(id, newTitle, newDescription, status, createdAt, Instant.now());
    }
}
