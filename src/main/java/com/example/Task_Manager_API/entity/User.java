package com.example.Task_Manager_API.entity;


import com.example.Task_Manager_API.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.atn.BlockEndState;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users", indexes = {
        @Index(name = "uq_username", columnList = "username", unique = true),
        @Index(name = "uq_email", columnList = "email", unique = true)
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(unique = true, nullable = false)
    @Length(min = 3, max = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    @Email
    private String email;

    @Column(nullable = false)
    @Length(min = 6)
    private String password;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true,
            cascade = CascadeType.ALL, mappedBy = "user")
     private List<Task> tasks;

    public void addTask(Task task) {
        tasks.add(task);
        task.setUser(this);
    }
    public void removeTask(Task task) {
        tasks.remove(task);
        task.setUser(null);
    }

    @Override
    public String toString() {
        return "User{username='" + username + "',email='" + email + "',firstName='" +
                firstName + "', lastName='" + lastName + "',role='" + role.name() +
                "',createdAt='" + createdAt + "',updatedAt='" + updatedAt + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(username, user.getUsername());
    }
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}
