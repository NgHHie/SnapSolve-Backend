package com.example.snapsolve.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties("user")
    private List<Search> searchList;



    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Payment> payments;
   
}