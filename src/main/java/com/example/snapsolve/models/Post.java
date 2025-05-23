package com.example.snapsolve.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String image;

    // Thêm trường để lưu các ảnh khác
    @ElementCollection
    @CollectionTable(name = "post_images", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image_url")
    private List<String> additionalImages = new ArrayList<>();

    private LocalDate createDate;
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"searchList", "noteList", "password", "email", "phoneNumber"})
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("post")
    private List<React> react;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("post")
    private List<Comment> comment;
    
    // Thêm mối quan hệ nhiều-nhiều với Topic
    @ManyToMany
    @JoinTable(
        name = "post_topic",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    @JsonIgnoreProperties("posts")
    private List<Topic> topics;

    @Transient
    public List<String> getAllImages() {
        List<String> allImages = new ArrayList<>();
        if (image != null && !image.isEmpty()) {
            allImages.add(image);
        }
        if (additionalImages != null) {
            allImages.addAll(additionalImages);
        }
        return allImages;
    }
}