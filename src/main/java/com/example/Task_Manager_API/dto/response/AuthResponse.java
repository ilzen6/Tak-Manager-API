package com.example.Task_Manager_API.dto.response;

import com.example.Task_Manager_API.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthResponse {
    private String token;
    private String type;
    private String username;
    private String email;
    private Role role;
}
