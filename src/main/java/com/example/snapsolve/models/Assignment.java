package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String question;
    
    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String answer;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String vector;
}