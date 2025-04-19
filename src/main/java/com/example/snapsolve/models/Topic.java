package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    
    // Mối quan hệ nhiều-nhiều với Post
    @ManyToMany(mappedBy = "topics")
    private List<Post> posts;
}