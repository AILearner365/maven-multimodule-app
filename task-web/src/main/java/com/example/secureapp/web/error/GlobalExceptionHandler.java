package com.example.secureapp.web.error;

import com.example.secureapp.common.api.ApiErrorResponse;
import com.example.secureapp.service.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleTaskNotFound(TaskNotFoundException exception) {
        ApiErrorResponse error = ApiErrorResponse.of(
                "TASK_NOT_FOUND",
                exception.getMessage(),
                List.of("taskId=" + exception.getTaskId())
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        List<String> details = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ApiErrorResponse error = ApiErrorResponse.of(
                "VALIDATION_FAILED",
                "Request validation failed.",
                details
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(IllegalArgumentException exception) {
        ApiErrorResponse error = ApiErrorResponse.of(
                "BAD_REQUEST",
                exception.getMessage(),
                List.of()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception exception) {
        ApiErrorResponse error = ApiErrorResponse.of(
                "INTERNAL_ERROR",
                "An unexpected error occurred.",
                List.of(exception.getClass().getSimpleName())
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
