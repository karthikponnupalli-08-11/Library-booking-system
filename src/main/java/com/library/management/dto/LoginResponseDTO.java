package com.library.management.dto;

public class LoginResponseDTO {

    private String message;
    private String username;

    public LoginResponseDTO(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}