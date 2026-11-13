
package com.library.management.service;

import java.util.List;

import com.library.management.dto.UserResponseDTO;
import com.library.management.entity.User;

public interface UserService {

    // Register a new user
    User registerUser(User user);

    // Get all users
    List<User> getAllUsers();

    // Get user by ID
    User getUserById(Long id);
    
    UserResponseDTO getUserByIda(Long id);

    List<UserResponseDTO> getAllUserss();
    User findByUsername(String username);
}
