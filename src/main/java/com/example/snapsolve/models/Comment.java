package com.example.snapsolve.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    
    // Thêm trường để lưu các ảnh của comment
    @ElementCollection
    @CollectionTable(name = "comment_images", joinColumns = @JoinColumn(name = "comment_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();
    
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"password", "email", "phoneNumber"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnoreProperties({"comment", "react"}) // Tránh circular reference với Post
    private Post post;
    
    // Thêm mối quan hệ self-referencing cho reply comments
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    @JsonIgnore 
    private Comment parentComment;
    
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("parentComment") 
    private List<Comment> replies = new ArrayList<>();
    
    // Getter để lấy tổng số reply
    @Transient
    public int getReplyCount() {
        return replies != null ? replies.size() : 0;
    }
    
    // Getter để lấy tất cả ảnh của comment
    @Transient
    public List<String> getAllImages() {
        return images != null ? images : new ArrayList<>();
    }
}