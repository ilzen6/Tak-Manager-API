package com.example.Task_Manager_API.service;

import com.example.Task_Manager_API.dto.request.CreateTaskRequest;
import com.example.Task_Manager_API.dto.request.UpdateTaskRequest;
import com.example.Task_Manager_API.dto.response.TaskDetailResponse;
import com.example.Task_Manager_API.dto.response.TaskResponse;
import com.example.Task_Manager_API.entity.Task;
import com.example.Task_Manager_API.entity.User;
import com.example.Task_Manager_API.enums.TaskPriority;
import com.example.Task_Manager_API.enums.TaskStatus;
import com.example.Task_Manager_API.exception.TaskNotFoundException;
import com.example.Task_Manager_API.exception.UnauthorizedException;
import com.example.Task_Manager_API.exception.UserNotFoundException;
import com.example.Task_Manager_API.mapper.TaskMapper;
import com.example.Task_Manager_API.repository.TaskRepository;
import com.example.Task_Manager_API.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Transactional
    public TaskResponse createTask(CreateTaskRequest request,
                                   String username) {
        log.info("Creating task for user: {}, title: {}", username,
                request.getTitle());
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found: " + username
                ));
        Task task = taskMapper.toEntity(request);
        user.addTask(task);
        userRepository.save(user);
        log.info( "Task created successfully with id: {}", task.getId());
        return taskMapper.toResponse(task);
    }

    public Page<TaskResponse> getAllTasks(String username,
                                          Pageable pageable) {
        log.info("Fetching tasks for user: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found: " + username
                ));
        Page<Task> pageTasks = taskRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId(), pageable);
        log.info("Found {} tasks for user: {}", pageTasks.getTotalElements(),
                username);
        return pageTasks.map(taskMapper::toResponse);
    }

    public TaskDetailResponse getTaskById(String username, Long taskId) {

        log.info("Fetching task by id: {} for user: {}", taskId, username);
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found: " + taskId));
        if (!(task.getUser().getUsername().equals(username))) {
            throw new UnauthorizedException("Access denied to task: " + taskId);
        }
        return taskMapper.toDetailResponse(task);
    }

    @Transactional
    public TaskResponse updateTask(Long taskId, String username,
                                   UpdateTaskRequest request) {
        log.info("Updating task id: {} for user: {}", taskId, username);
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException("Task not found: " + taskId)
        );
        if (!(task.getUser().getUsername().equals(username))) {
            throw new UnauthorizedException("Access denied to task: " + taskId);
        }
        taskMapper.updateEntityFromRequest(request, task);
        taskRepository.save(task);
        log.info( "Task updated successfully: {}", taskId);
        return taskMapper.toResponse(task);

    }

    @Transactional
    public void deleteTask(Long taskId, String username) {
        log.info("Deleting task id: {} for user: {}", taskId, username);
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException("Task not found: " + taskId)
        );

        if (!(task.getUser().getUsername().equals(username))) {
            throw new UnauthorizedException("Access denied to task: " + taskId);
        }
        User user = task.getUser();  // Используем загруженного user из task
        user.removeTask(task);
        taskRepository.delete(task);
        userRepository.save(user);
        log.info("Task deleted successfully: {}", taskId);
    }


    public List<TaskResponse> getTasksByStatus(String username, TaskStatus status) {
        log.info("Fetching tasks with status: {} for user: {}", status, username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found: " + username));

        List<Task> tasks = taskRepository.findByUserIdAndStatus(
                user.getId(), status);

        log.info("Found {} tasks with status {} for user: {}",
                tasks.size(), status, username);

        return taskMapper.toResponseList(tasks);
    }

    public List<TaskResponse> getTasksByPriority(String username, TaskPriority priority) {
        log.info("Fetching tasks with priority: {} for user: {}", priority, username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found: " + username));

        List<Task> tasks = taskRepository.findByUserIdAndPriority(
                user.getId(), priority);

        log.info("Found {} tasks with priority {} for user: {}",
                tasks.size(), priority, username);

        return taskMapper.toResponseList(tasks);
    }

    public List<TaskResponse> getOverdueTasks(String username) {
        log.info("Fetching overdue tasks for user: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found: " + username));

        List<Task> tasks = taskRepository.findByUserIdAndDueDateBefore(
                user.getId(), LocalDate.now());

        log.info("Found {} overdue tasks for user: {}", tasks.size(), username);

        return taskMapper.toResponseList(tasks);
    }

    public long getTasksCount(String username, TaskStatus status) {
        log.info("Counting tasks with status: {} for user: {}", status, username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found: " + username));

        long count = taskRepository.countByUserIdAndStatus(user.getId(), status);

        log.info("User {} has {} tasks with status {}", username, count, status);

        return count;
    }

}