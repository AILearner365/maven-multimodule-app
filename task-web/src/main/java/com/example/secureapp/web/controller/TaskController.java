package com.example.secureapp.web.controller;

import com.example.secureapp.web.dto.CreateTaskRequest;
import com.example.secureapp.web.dto.TaskResponse;
import com.example.secureapp.web.dto.UpdateTaskRequest;
import com.example.secureapp.web.dto.UpdateTaskStatusRequest;
import com.example.secureapp.service.TaskNotFoundException;
import com.example.secureapp.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> findAll() {
        return taskService.findAll().stream()
                .map(TaskResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public TaskResponse findById(@PathVariable UUID id) {
        return taskService.findById(id)
                .map(TaskResponse::from)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody CreateTaskRequest request) {
        return TaskResponse.from(taskService.create(request.title(), request.description()));
    }

    @PutMapping("/{id}")
    public TaskResponse updateDetails(@PathVariable UUID id, @Valid @RequestBody UpdateTaskRequest request) {
        return TaskResponse.from(taskService.updateDetails(id, request.title(), request.description()));
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(@PathVariable UUID id, @Valid @RequestBody UpdateTaskStatusRequest request) {
        return TaskResponse.from(taskService.updateStatus(id, request.status()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        taskService.delete(id);
    }
}
