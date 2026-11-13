package com.library.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.management.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}