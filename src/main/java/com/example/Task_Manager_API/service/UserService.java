package com.example.Task_Manager_API.service;

import com.example.Task_Manager_API.dto.response.UserResponse;
import com.example.Task_Manager_API.entity.User;
import com.example.Task_Manager_API.exception.UserNotFoundException;
import com.example.Task_Manager_API.mapper.UserMapper;
import com.example.Task_Manager_API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getCurrentUser(String username) {
        log.info("Fetching current user: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("User not found: " +
                        username));
        return userMapper.toResponse(user);
    }

    public UserResponse getUserById(Long userId) {
        log.info("Fetching user by id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found: " + userId
                ));
        return userMapper.toResponse(user);
    }


}
