package com.example.snapsolve.dto.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.snapsolve.dto.user.UserDTO;
import com.example.snapsolve.models.User;

@Component
public class UserMapper {
    
    private final String serverBaseUrl;
    
    @Autowired
    public UserMapper(@Qualifier("serverBaseUrl") String serverBaseUrl) {
        this.serverBaseUrl = serverBaseUrl;
        System.out.println("UserMapper initialized with server URL: " + serverBaseUrl);
    }
    
    public UserDTO convertToDTO(User user) {
        if (user == null) return null;
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setUserRank(user.getUserRank());
        userDTO.setStatusMessage(user.getStatusMessage());
        userDTO.setStudentInformation(user.getStudentInformation());
        userDTO.setSuid(user.getSuid());
        
        
        if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
        
            if (user.getAvatarUrl().startsWith("http")) {
                userDTO.setAvatarUrl(user.getAvatarUrl());
            } else {
        
                userDTO.setAvatarUrl(serverBaseUrl + user.getAvatarUrl());
            }
        } else {
            userDTO.setAvatarUrl(null);
        }
        
        return userDTO;
    }
    
    public User convertToEntity(UserDTO userDTO) {
        if (userDTO == null) return null;
        
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUserRank(userDTO.getUserRank());
        user.setStatusMessage(userDTO.getStatusMessage());
        user.setStudentInformation(userDTO.getStudentInformation());
        user.setSuid(userDTO.getSuid());
        
        
        if (userDTO.getAvatarUrl() != null && !userDTO.getAvatarUrl().isEmpty()) {
            
            if (userDTO.getAvatarUrl().startsWith(serverBaseUrl)) {
                user.setAvatarUrl(userDTO.getAvatarUrl().substring(serverBaseUrl.length()));
            } else {
                user.setAvatarUrl(userDTO.getAvatarUrl());
            }
        } else {
            user.setAvatarUrl(null);
        }
        
        return user;
    }

  
    public java.util.List<UserDTO> convertToDTOList(java.util.List<User> users) {
        if (users == null) return java.util.Collections.emptyList();
        return users.stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}