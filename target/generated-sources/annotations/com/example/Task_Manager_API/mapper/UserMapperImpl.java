package com.example.Task_Manager_API.mapper;

import com.example.Task_Manager_API.dto.request.RegisterRequest;
import com.example.Task_Manager_API.dto.response.UserResponse;
import com.example.Task_Manager_API.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-02T19:36:31+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( user.getId() );
        userResponse.setUsername( user.getUsername() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setFirstName( user.getFirstName() );
        userResponse.setLastName( user.getLastName() );

        userResponse.setFullName( user.getFirstName() + " " + user.getLastName() );

        return userResponse;
    }

    @Override
    public User toEntity(RegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( request.getUsername() );
        user.setEmail( request.getEmail() );
        user.setPassword( request.getPassword() );
        user.setFirstName( request.getFirstName() );
        user.setLastName( request.getLastName() );

        user.setRole( com.example.Task_Manager_API.enums.Role.USER );

        return user;
    }
}
