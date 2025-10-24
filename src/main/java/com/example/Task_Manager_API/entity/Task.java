package com.example.Task_Manager_API.entity;

import com.example.Task_Manager_API.enums.TaskPriority;
import com.example.Task_Manager_API.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tasks", indexes = {
        @Index(name = "idx_user_id", columnList = "user"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_user_status", columnList = "user,status")
})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority = TaskPriority.MEDIUM;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @PreUpdate
    public void isCompleted() {
        if (this.status == TaskStatus.DONE && completedAt == null) {
            this.completedAt = LocalDateTime.now();
        }
        if (this.status != TaskStatus.DONE && completedAt != null) {
            completedAt = null;
        }
    }

    @Override
    public String toString() {
        return "Task{title='" + title + "',description='" + description +
                "',status='" + status.name() + "',priority='" + priority.name()
                + "',dueDate='" + dueDate + "',createdAt='" + createdAt +
                "',updatedAt='" + updatedAt + "',completedAt='" + completedAt
                + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equals(task.getId(), id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



}
