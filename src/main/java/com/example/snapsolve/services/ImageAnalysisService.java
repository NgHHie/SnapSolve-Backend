package com.example.snapsolve.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.snapsolve.dto.image.AnalysisResult;

import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * Service to handle image analysis and processing
 */
@Service
public interface ImageAnalysisService {
    
    /**
     * Analyze an image file directly
     * @param imageFile The image file to analyze
     * @return AnalysisResult containing the extracted content and solution
     */
    AnalysisResult analyzeImageFile(File file);


    
}