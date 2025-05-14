package com.example.snapsolve.dto.user;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserCreateDTO {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String userRank;
    private LocalDate dob;
}