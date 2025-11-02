package com.example.Task_Manager_API.service;

import com.example.Task_Manager_API.dto.request.LoginRequest;
import com.example.Task_Manager_API.dto.request.RegisterRequest;
import com.example.Task_Manager_API.dto.response.AuthResponse;
import com.example.Task_Manager_API.entity.User;
import com.example.Task_Manager_API.exception.UnauthorizedException;
import com.example.Task_Manager_API.exception.UserNotFoundException;
import com.example.Task_Manager_API.mapper.UserMapper;
import com.example.Task_Manager_API.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: "
                    + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        User user = userMapper.toEntity(request);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser.getUsername());
        log.info("User registered successfully: {}", savedUser.getUsername());
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();

    }
    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("User attempting to login: {}",  request.getUsername());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword());
            authenticationManager.authenticate(authenticationToken);
        } catch(BadCredentialsException ex) {
            log.info("Failed login attempt for user: {}", request.getUsername());
            throw new UnauthorizedException("Invalid username or password");
        }
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow( () -> new UserNotFoundException("User not found: "
                + request.getUsername()));
        String token = jwtService.generateToken(request.getUsername());
        log.info("User logged in successfully: {}", request.getUsername());
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
