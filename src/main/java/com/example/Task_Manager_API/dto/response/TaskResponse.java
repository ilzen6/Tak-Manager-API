package com.example.Task_Manager_API.dto.response;

import com.example.Task_Manager_API.enums.TaskPriority;
import com.example.Task_Manager_API.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskResponse {
    private Long id;
    private String title;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;

}
