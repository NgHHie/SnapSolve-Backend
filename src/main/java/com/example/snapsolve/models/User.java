package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Search> searchList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Note> noteList;


    @OneToMany(mappedBy = "user")
    private List<Payment> payments;
}