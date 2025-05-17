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
            // Extract text from the image using OCR service
            Map<String, Object> extractionResult = ocrService.extractTextFromImage(imageFile);
            
            if ((Boolean) extractionResult.get("success")) {
                List<SimilarQuestion> question = (List<SimilarQuestion>) extractionResult.get("similar_questions");
            
                return new AnalysisResult(true, question, "success");
            } else {
                return new AnalysisResult(false, null, "Failed to extract text from image: " + extractionResult.get("message"));
            }
        } catch (Exception e) {
            return new AnalysisResult(false, null, "Error analyzing image: " + e.getMessage());
        }
    }

}