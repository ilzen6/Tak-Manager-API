package com.example.Task_Manager_API.dto.request;

import com.example.Task_Manager_API.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTaskRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 200)
    private String title;

    @NotNull
    @NotBlank
    @Size(max = 1000)
    private String description;

    private TaskPriority priority = TaskPriority.MEDIUM;
    private LocalDate dueDate;
}
