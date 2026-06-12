package com.example.secureapp.service;

import com.example.secureapp.domain.Task;
import com.example.secureapp.domain.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryTaskServiceTest {
    @Test
    void createStoresTaskWithTodoStatus() {
        InMemoryTaskService service = new InMemoryTaskService();

        Task createdTask = service.create("Upgrade dependency", "Patch vulnerable component.");

        assertThat(createdTask.id()).isNotNull();
        assertThat(createdTask.status()).isEqualTo(TaskStatus.TODO);
        assertThat(service.findById(createdTask.id())).contains(createdTask);
    }

    @Test
    void updateStatusChangesTaskStatus() {
        InMemoryTaskService service = new InMemoryTaskService();
        Task createdTask = service.create("Generate SBOM", "Create CycloneDX output.");

        Task updatedTask = service.updateStatus(createdTask.id(), TaskStatus.DONE);

        assertThat(updatedTask.status()).isEqualTo(TaskStatus.DONE);
        assertThat(updatedTask.updatedAt()).isAfterOrEqualTo(createdTask.updatedAt());
    }

    @Test
    void updateMissingTaskThrowsException() {
        InMemoryTaskService service = new InMemoryTaskService();

        assertThatThrownBy(() -> service.updateStatus(java.util.UUID.randomUUID(), TaskStatus.DONE))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
