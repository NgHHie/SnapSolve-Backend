package com.example.snapsolve.services;

import org.springframework.stereotype.Service;
import java.io.File;

/**
 * Service to handle image analysis and processing
 */
@Service
public interface ImageAnalysisService {
    
    /**
     * Analyze an image and extract mathematical equations or text
     * @param imageId The ID of the image to analyze
     * @return AnalysisResult containing the extracted content and solution
     */
    AnalysisResult analyzeImage(String imageId);
    
    /**
     * Analyze an image file directly
     * @param imageFile The image file to analyze
     * @return AnalysisResult containing the extracted content and solution
     */
    AnalysisResult analyzeImageFile(File imageFile);
    
    /**
     * Class to hold the result of image analysis
     */
    class AnalysisResult {
        private boolean success;
        private String question;
        private String answer;
        private String errorMessage;
        
        public AnalysisResult(boolean success, String question, String answer) {
            this.success = success;
            this.question = question;
            this.answer = answer;
            this.errorMessage = null;
        }
        
        public AnalysisResult(boolean success, String errorMessage) {
            this.success = success;
            this.question = null;
            this.answer = null;
            this.errorMessage = errorMessage;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}