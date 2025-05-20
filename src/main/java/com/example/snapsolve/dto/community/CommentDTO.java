package com.example.snapsolve.dto.community;

import lombok.Data;
import java.util.List;


@Data
public class CommentDTO {
    private String content;
    private List<String> images;
    private Long userId;
    private Long postId;
    private Long parentCommentId; // Null nếu là comment gốc, có giá trị nếu là reply
}