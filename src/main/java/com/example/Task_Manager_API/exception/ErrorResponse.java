package com.example.Task_Manager_API.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse{
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
    private List<String> errors;

    public ErrorResponse(int status, LocalDateTime timestamp,
                         String path, List<String> errors) {
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
        this.errors = errors;
    }

    public ErrorResponse(String message, int status, LocalDateTime timestamp,
                         String path) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
    }
}
