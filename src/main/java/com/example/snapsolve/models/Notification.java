package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate notiDate;
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}