
package com.library.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.library.management.dto.UserMapper;
import com.library.management.dto.UserResponseDTO;
import com.library.management.entity.Role;
import com.library.management.entity.User;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register User
    @Override
    public User registerUser(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Public registration always creates CUSTOMER
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    // Get All Users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User By ID
    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("User is not Available"));
    }

    @Override
    public UserResponseDTO getUserByIda(Long id) {

        User user = userRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUserss() {

        return userRepository.findAll()
            .stream()
            .map(UserMapper::toDTO)
            .toList();
    }

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}
}
