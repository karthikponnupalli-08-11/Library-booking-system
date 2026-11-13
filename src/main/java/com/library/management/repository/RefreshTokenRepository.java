
package com.library.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.management.entity.RefreshToken;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    void deleteByUsername(String username);
}
