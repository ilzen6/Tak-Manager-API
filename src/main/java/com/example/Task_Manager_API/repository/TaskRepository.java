package com.example.Task_Manager_API.repository;

import com.example.Task_Manager_API.entity.Task;
import com.example.Task_Manager_API.enums.TaskPriority;
import com.example.Task_Manager_API.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndStatus(Long userId, TaskStatus status);
    List<Task> findByUserIdAndPriority(Long userId, TaskPriority priority);
    Page<Task> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    List<Task> findByUserIdAndStatusAndOrderByPriorityDescDueDateAsc(Long userId,
                                                                     TaskStatus status);
    long countByUserIdAndStatus(Long userId, TaskStatus status);

    List<Task> findByUserIdAndDueDateBefore(Long userId, LocalDate date);
    boolean existByIdAndUserId(Long id, Long userId);


}
