package com.example.snapsolve.dto.user;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String statusMessage;
    private String studentInformation;
    private String suid;
    private String phoneNumber;
    private String email;
    private String userRank;
    private String avatarUrl;
    private String password;

}