package com.example.snapsolve.dto.community;

import com.example.snapsolve.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class UserResponseDTO {
    private Long id;
    private String username;
    private String avatarUrl;
    
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatarUrl = user.getAvatarUrl();
    }
}
