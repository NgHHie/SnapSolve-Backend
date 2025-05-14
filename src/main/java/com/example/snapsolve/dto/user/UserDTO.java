package com.example.snapsolve.dto.user;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;

    private LocalDate dob;
    private String firstName;
    private String lastName;
    // Không bao gồm password vì lý do bảo mật
}