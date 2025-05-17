package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String vector;
}