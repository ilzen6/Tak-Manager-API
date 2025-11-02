package com.example.Task_Manager_API.mapper;

import com.example.Task_Manager_API.dto.request.RegisterRequest;
import com.example.Task_Manager_API.dto.response.UserResponse;
import com.example.Task_Manager_API.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.example.Task_Manager_API.enums.Role;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {
    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + " +
            "user.getLastName())")
    public UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "role", expression = "java(Role.USER)")
    public User toEntity(RegisterRequest request);
}
