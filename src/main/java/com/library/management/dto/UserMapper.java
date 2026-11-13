package com.library.management.dto;

import com.library.management.entity.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {

        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
    }
}