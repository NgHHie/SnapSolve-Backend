package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String title;
    private LocalDate notiDate;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}