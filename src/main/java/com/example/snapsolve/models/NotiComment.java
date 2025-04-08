package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class NotiComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Comment comment;

    @OneToOne
    private Notification notification;
}