package com.example.snapsolve.services.impl;

import com.example.snapsolve.services.ImageAnalysisService;
import com.example.snapsolve.client.OCRService;
import com.example.snapsolve.dto.image.AnalysisResult;
import com.example.snapsolve.dto.image.SimilarQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ImageAnalysisServiceImpl implements ImageAnalysisService {
    
    @Value("${file.upload-dir:uploads/images}")
    private String uploadDir;
    
    @Autowired
    private OCRService ocrService;
    
    
    @Override
    public AnalysisResult analyzeImageFile(File imageFile) {
        try {
            // Previous validation code...
            
            Map<String, Object> extractionResult = ocrService.extractTextFromImage(imageFile);
            
            if (extractionResult == null) {
                return new AnalysisResult(false, null, "Extraction result is null");
            }
            
            if ((Boolean) extractionResult.getOrDefault("success", false)) {
                // Here's where we need to fix the casting issue
                List<SimilarQuestion> questions = new ArrayList<>();
                
                // Get the similar_questions array from the result
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> similarQuestionsMap = 
                    (List<Map<String, Object>>) extractionResult.get("similar_questions");
                
                if (similarQuestionsMap != null) {
                    // Properly convert each map to a SimilarQuestion object
                    for (Map<String, Object> questionMap : similarQuestionsMap) {
                        SimilarQuestion question = new SimilarQuestion();
                        
                        // Extract id (convert to Long if needed)
                        if (questionMap.containsKey("id")) {
                            Object idObj = questionMap.get("id");
                            if (idObj instanceof Number) {
                                question.setId(((Number) idObj).longValue());
                            } else if (idObj instanceof String) {
                                try {
                                    question.setId(Long.parseLong((String) idObj));
                                } catch (NumberFormatException e) {
                                    System.err.println("Could not parse id: " + idObj);
                                }
                            }
                        }
                        
                        // Extract similarity (convert to Float if needed)
                        if (questionMap.containsKey("similarity")) {
                            Object simObj = questionMap.get("similarity");
                            if (simObj instanceof Number) {
                                question.setSimilarity(((Number) simObj).floatValue());
                            } else if (simObj instanceof String) {
                                try {
                                    question.setSimilarity(Float.parseFloat((String) simObj));
                                } catch (NumberFormatException e) {
                                    System.err.println("Could not parse similarity: " + simObj);
                                }
                            }
                        }
                        
                        questions.add(question);
                    }
                }
                
                return new AnalysisResult(true, questions, "success");
            } else {
                String message = (String) extractionResult.getOrDefault("message", "Unknown error");
                return new AnalysisResult(false, null, "Failed to extract text from image: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AnalysisResult(false, null, "Error analyzing image: " + e.getMessage());
        }
    }

}