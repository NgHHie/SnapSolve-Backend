package com.example.snapsolve.dto;

import lombok.Data;
import java.util.List;

@Data
public class PostDTO {
    private String title;
    private String content;
    private String image;
    private Long userId;
    private List<Long> topicIds;
}