package com.example.Task_Manager_API.controller;

import com.example.Task_Manager_API.dto.request.CreateTaskRequest;
import com.example.Task_Manager_API.dto.request.UpdateTaskRequest;
import com.example.Task_Manager_API.dto.response.TaskDetailResponse;
import com.example.Task_Manager_API.dto.response.TaskResponse;
import com.example.Task_Manager_API.enums.TaskPriority;
import com.example.Task_Manager_API.enums.TaskStatus;
import com.example.Task_Manager_API.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid CreateTaskRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(request, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getTasksByCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 20, page = 0, sort = "createdAt,DESC") Pageable pageable
    ) {
        return ResponseEntity.ok(taskService.getAllTasks(
                userDetails.getUsername(),
                pageable));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDetailResponse> getTaskById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId
    ) {
        return ResponseEntity.ok(taskService.getTaskById(
                userDetails.getUsername(),
                taskId));
    }

    @PutMapping("/{taskId}")
    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid UpdateTaskRequest request
    ) {
        return ResponseEntity.ok(taskService.updateTask(
                taskId,
                userDetails.getUsername(),
                request));
    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        taskService.deleteTask(taskId, userDetails.getUsername());
        // ИСПРАВЛЕНО: 204 вместо 200
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(
            @PathVariable TaskStatus status,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(taskService.getTasksByStatus(
                userDetails.getUsername(),
                status));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskResponse>> getTasksByPriority(
            @PathVariable TaskPriority priority,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(taskService.getTasksByPriority(
                userDetails.getUsername(),
                priority));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<TaskResponse>> getOverdueTasks(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(taskService.getOverdueTasks(
                userDetails.getUsername()));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTasksCount(
            @RequestParam TaskStatus status,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(taskService.getTasksCount(
                userDetails.getUsername(),
                status));
    }
}