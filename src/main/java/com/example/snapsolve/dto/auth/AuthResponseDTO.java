package com.example.snapsolve.dto.auth;

import com.example.snapsolve.dto.user.UserDTO;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private UserDTO user;
    
    public AuthResponseDTO(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }
}