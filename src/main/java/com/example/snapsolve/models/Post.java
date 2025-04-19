package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String image;
    private LocalDate createDate;
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<React> react;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comment;

    @ManyToMany
    @JoinTable(
        name = "post_topic",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<Topic> topics;
}