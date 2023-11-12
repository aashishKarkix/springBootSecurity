package com.example.security.mapper;

import com.example.security.dao.RegisterRequest;
import com.example.security.entity.UserEntity;
import com.example.security.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserEntityMapper {
    private final PasswordEncoder passwordEncoder;

    public UserEntityMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUserFromRequest(RegisterRequest request) {
        return UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.get(request.getRole()).orElse(Role.USER)) // Set the user's role
                .build();
    }
}