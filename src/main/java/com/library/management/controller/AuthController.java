package com.library.management.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.library.management.config.JwtService;
import com.library.management.dto.LoginRequestDTO;
import com.library.management.dto.RefreshTokenRequestDTO;
import com.library.management.entity.RefreshToken;
import com.library.management.service.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
  
    private final RefreshTokenService refreshTokenService;
    
    /*~~(Unable to determine parameter type)~~>*//*~~(Unable to determine parameter type)~~>*//*~~(Unable to determine parameter type)~~>*/@Autowired
    private JwtService jwtService;

    public AuthController(RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager) {
        this.refreshTokenService = refreshTokenService;
      this.authenticationManager = authenticationManager;
    }
    
@PostMapping("/login")
public ResponseEntity<?> login(
        @RequestBody LoginRequestDTO request) {

    Authentication authentication =
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

    String username = authentication.getName();

    // Generate access token
    String accessToken =
            jwtService.generateToken(username);

    // Generate refresh token
    String refreshToken =
            refreshTokenService.createRefreshToken(username);

    Map<String, Object> response = new HashMap<>();

    response.put("message", "Login successful");
    response.put("username", username);
    response.put("accessToken", accessToken);
    response.put("refreshToken", refreshToken);
    response.put("tokenType", "Bearer");

    return ResponseEntity.ok(response);
}

@PostMapping("/refresh")
public ResponseEntity<?> refreshToken(
        @RequestBody RefreshTokenRequestDTO request) {

    try {

        // Validate old refresh token
        RefreshToken oldToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken());

        String username = oldToken.getUsername();

        // Rotate refresh token
        String newRefreshToken =
                refreshTokenService.rotateRefreshToken(
                        request.getRefreshToken());

        // Generate new access token
        String newAccessToken =
                jwtService.generateToken(username);

        return ResponseEntity.ok(
                Map.of(
                    "accessToken", newAccessToken,
                    "refreshToken", newRefreshToken,
                    "tokenType", "Bearer"
                )
        );

    } catch (IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", ex.getMessage()));
    }
}

@PostMapping("/logout")
public ResponseEntity<?> logout(
        @RequestBody RefreshTokenRequestDTO request) {

    try {

        refreshTokenService.revokeRefreshToken(
                request.getRefreshToken());

        return ResponseEntity.ok(
                Map.of("message", "Logout successful")
        );

    } catch (IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", ex.getMessage()));
    }
}
    }
