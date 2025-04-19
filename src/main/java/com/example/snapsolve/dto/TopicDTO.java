package com.example.snapsolve.dto;

import lombok.Data;

@Data
public class TopicDTO {
    private Long id;
    private String name;
    private String description;
    private int postCount;
    
    // Constructor
    public TopicDTO(Long id, String name, String description, int postCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.postCount = postCount;
    }
}