package com.example.snapsolve.services;

import org.springframework.stereotype.Service;

import com.example.snapsolve.dto.auth.AuthResponseDTO;

@Service
public interface AuthService {
    AuthResponseDTO login(String username, String password);
}