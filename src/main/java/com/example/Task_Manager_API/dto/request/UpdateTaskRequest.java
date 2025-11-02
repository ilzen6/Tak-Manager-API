package com.example.Task_Manager_API.dto.request;

import com.example.Task_Manager_API.enums.TaskPriority;
import com.example.Task_Manager_API.enums.TaskStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateTaskRequest {
    @Size(min = 1, max = 200)
    private String title;

    @Size(max = 2000)
    private String description;

    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
}
