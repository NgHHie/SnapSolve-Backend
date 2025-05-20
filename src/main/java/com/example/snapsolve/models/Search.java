package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Data
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @Column(columnDefinition = "LONGTEXT")
    @Lob
    private String question;

    private String createDate;
    
    // Add fields for storing assignment IDs
    private Long assignmentId1;
    private Long assignmentId2;
    private Long assignmentId3;
    private Long assignmentId4;
    private Long assignmentId5;

    @ManyToOne
    private User user;
}