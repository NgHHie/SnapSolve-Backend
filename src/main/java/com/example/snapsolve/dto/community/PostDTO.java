package com.example.snapsolve.dto.community;

import lombok.Data;
import java.util.List;

@Data
public class PostDTO {
    private String title;
    private String content;
    private List<String> images;
    private Long userId;
    private List<Long> topicIds;
}