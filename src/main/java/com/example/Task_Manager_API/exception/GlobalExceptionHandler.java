package com.example.Task_Manager_API.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(
            TaskNotFoundException ex,
            WebRequest request
    ) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND.value(), LocalDateTime.now(),
                request.getDescription(false)
                        .replace("uri=", ""));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException ex,
            WebRequest request
    ) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                request.getDescription(false)
                        .replace("uri=", ""));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException (
         UnauthorizedException ex,
         WebRequest request
    ) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                request.getDescription(false)
                        .replace("uri=", ""));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {

        List<ObjectError> ers = ex.getBindingResult().getAllErrors();
        List<String> errors = new ArrayList<>();
        for (ObjectError e: ers) {
            errors.add(e.getDefaultMessage());
        }

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                request.getDescription(false)
                        .replace("uri=", ""),
                errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
           Exception ex,
           WebRequest request
    ) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                request.getDescription(false)
                        .replace("uri=", ""));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
