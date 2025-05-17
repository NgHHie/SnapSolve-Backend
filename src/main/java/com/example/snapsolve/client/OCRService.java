package com.example.snapsolve.client;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OCRService {
    
    @Value("${ocr.service.url:http://localhost:5000}")
    private String ocrServiceUrl;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public OCRService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    /**
     * Extract text from an image using the Python OCR service
     * @param imageFile The image file to process
     * @return A map with extracted text and other data
     */
    public Map<String, Object> extractTextFromImage(File imageFile) {
        try {
            String url = ocrServiceUrl + "/extract-text";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            // Debug info
            System.out.println("Calling OCR API at: " + url);
            System.out.println("File exists: " + imageFile.exists());
            System.out.println("File size: " + imageFile.length());
            
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new FileSystemResource(imageFile));
            
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            
            System.out.println("Response status: " + response.getStatusCode());
            
            return objectMapper.readValue(response.getBody(), Map.class);
        } catch (Exception e) {
            System.err.println("Error extracting text from image: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to extract text from image: " + e.getMessage(), e);
        }
    }
}