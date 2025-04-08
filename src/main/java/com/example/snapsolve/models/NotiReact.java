package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class NotiReact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private React react;

    @OneToOne
    private Notification notification;
}