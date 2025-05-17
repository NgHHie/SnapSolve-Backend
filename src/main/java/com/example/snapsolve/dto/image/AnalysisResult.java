package com.example.snapsolve.dto.image;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResult {
    private boolean success;
    private List<SimilarQuestion> assignments;
    private String errorMessage;
}