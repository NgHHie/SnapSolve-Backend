package com.example.snapsolve.dto.community;

import java.time.LocalDate;
import java.util.List;

import com.example.snapsolve.models.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String content;
    private List<String> images;
    private LocalDate createDate;
    private UserResponseDTO user;
    private int replyCount;
    
    // Constructor để convert từ Comment entity
    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.images = comment.getImages();
        this.createDate = comment.getCreateDate();
        this.user = new UserResponseDTO(comment.getUser());
        // Tính toán replyCount từ replies actual
        this.replyCount = comment.getReplyCount();
    }
}
