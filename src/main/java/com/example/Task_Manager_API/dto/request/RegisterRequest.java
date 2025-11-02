package com.example.Task_Manager_API.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNullApi;

import javax.swing.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String firstName;
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String lastName;

}
