package com.example.snapsolve.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.snapsolve.dto.auth.AuthResponseDTO;
import com.example.snapsolve.dto.user.UserDTO;
import com.example.snapsolve.models.User;
import com.example.snapsolve.repositories.UserRepository;
import com.example.snapsolve.services.AuthService;
import com.example.snapsolve.utils.JwtTokenUtil;

@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Override
    public AuthResponseDTO login(String username, String password) {
        // Try finding by username
        User user = userRepository.findByUsername(username)
                .orElse(null);
        
        // If not found by username, try by email
        if (user == null) {
            user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new BadCredentialsException("Invalid username/email or password"));
        }
        
        // Verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username/email or password");
        }
        
        // Generate JWT token
        String token = jwtTokenUtil.generateAccessToken(user);
        
        // Convert User to UserDTO
        UserDTO userDTO = convertToDTO(user);
        
        return new AuthResponseDTO(token, userDTO);
    }
    
    // Helper method to convert User to UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());


        userDTO.setStatusMessage(user.getStatusMessage());
        userDTO.setStudentInformation(user.getStudentInformation());
        userDTO.setSuid(user.getSuid());
        return userDTO;
    }
}