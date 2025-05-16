package com.example.snapsolve.services.impl;

import com.example.snapsolve.services.ImageAnalysisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ImageAnalysisServiceImpl implements ImageAnalysisService {
    
    @Value("${file.upload-dir:uploads/images}")
    private String uploadDir;
    
    private static final String[] SAMPLE_QUESTIONS = {
        "Tìm hệ số a,b,c để y sau có cực trị: y = a·x² + b·x + c·y + x²",
        "Find the values of x for which f(x) = x³ - 6x² + 9x - 2 has a local minimum.",
        "Consider f(x) = ln(x). Calculate ∫[1,e] f(x) dx.",
        "Solve the equation: sin(x) + cos(x) = 1 for x in [0, 2π]",
        "Evaluate the limit: lim(x→0) (sin(3x)/x)"
    };
    
    private static final String[] SAMPLE_ANSWERS = {
        "Để có cực trị, y' = 0:\n2a·x + b + 2x = 0\nSắp xếp: (2a+2)x + b = 0\nVì phương trình này có nghiệm với mọi x, nên:\n2a+2 = 0 → a = -1\nb = 0\nDo đó (a,b,c) = (-1, 0, c) với c bất kỳ.",
        "To find local minima, set f'(x) = 0 and check f''(x) > 0.\nf'(x) = 3x² - 12x + 9\nSolving 3x² - 12x + 9 = 0:\n3(x² - 4x + 3) = 0\n3(x - 1)(x - 3) = 0\nx = 1 or x = 3\nChecking f''(x) = 6x - 12\nf''(1) = 6 - 12 = -6 < 0, so x = 1 is a local maximum\nf''(3) = 18 - 12 = 6 > 0, so x = 3 is a local minimum\nTherefore, f(x) has a local minimum at x = 3.",
        "∫[1,e] ln(x) dx = [x·ln(x) - x][1,e]\n= e·ln(e) - e - (1·ln(1) - 1)\n= e·1 - e - (0 - 1)\n= e - e + 1 = 1",
        "sin(x) + cos(x) = 1\nSquaring both sides:\nsin²(x) + 2sin(x)cos(x) + cos²(x) = 1\n1 + 2sin(x)cos(x) = 1\n2sin(x)cos(x) = 0\nsin(2x) = 0\n2x = nπ where n is an integer\nx = nπ/2\nFor x in [0, 2π]: x = 0, π/2, π, 3π/2, 2π\nChecking each value:\nAt x = 0: sin(0) + cos(0) = 0 + 1 = 1 ✓\nAt x = π/2: sin(π/2) + cos(π/2) = 1 + 0 = 1 ✓\nAt x = π: sin(π) + cos(π) = 0 + (-1) = -1 ✗\nAt x = 3π/2: sin(3π/2) + cos(3π/2) = -1 + 0 = -1 ✗\nAt x = 2π: sin(2π) + cos(2π) = 0 + 1 = 1 ✓\nSolution set: x = 0, π/2, 2π",
        "lim(x→0) (sin(3x)/x)\n= lim(x→0) (3·sin(3x)/3x)\n= 3·lim(x→0) (sin(3x)/3x)\nLet u = 3x, as x→0, u→0\n= 3·lim(u→0) (sin(u)/u)\n= 3·1 = 3"
    };
    
    // Cache to store analysis results by imageId
    private final Map<String, AnalysisResult> analysisCache = new HashMap<>();
    
    @Override
    public AnalysisResult analyzeImage(String imageId) {
        // Check if we already analyzed this image
        if (analysisCache.containsKey(imageId)) {
            return analysisCache.get(imageId);
        }
        
        try {
            // In a real implementation, you would:
            // 1. Find the image file using imageId
            // 2. Use OCR or ML to extract text/equations
            // 3. Process the extracted content
            
            // For demo, we'll return random mock data
            int index = new Random().nextInt(SAMPLE_QUESTIONS.length);
            AnalysisResult result = new AnalysisResult(
                true,
                SAMPLE_QUESTIONS[index],
                SAMPLE_ANSWERS[index]
            );
            
            // Cache the result
            analysisCache.put(imageId, result);
            return result;
            
        } catch (Exception e) {
            return new AnalysisResult(false, "Error analyzing image: " + e.getMessage());
        }
    }
    
    @Override
    public AnalysisResult analyzeImageFile(File imageFile) {
        try {
            if (!imageFile.exists()) {
                return new AnalysisResult(false, "Image file not found");
            }
            
            // In a real implementation, you would:
            // 1. Use OCR like Tesseract or ML models to extract text/equations
            // 2. Process the extracted content
            // 3. Use a math solver or LLM to solve the equation
            
            // For demo, we'll return random mock data
            int index = new Random().nextInt(SAMPLE_QUESTIONS.length);
            return new AnalysisResult(
                true,
                SAMPLE_QUESTIONS[index],
                SAMPLE_ANSWERS[index]
            );
            
        } catch (Exception e) {
            return new AnalysisResult(false, "Error analyzing image: " + e.getMessage());
        }
    }
}