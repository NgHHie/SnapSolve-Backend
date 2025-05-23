package com.example.snapsolve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.snapsolve.exception.ResourceNotFoundException;
import com.example.snapsolve.models.User;
import com.example.snapsolve.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AISolutionController {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserRepository userRepository;

    @Value("${ocr.service.url:http://localhost:5000}")
    private String pythonServiceUrl;
    
    @Value("${ai.prompt.solution:}")
    private String aiSolutionPrompt;

    @PostMapping("/solution")
    public ResponseEntity<Map<String, Object>> getAISolution(
            @RequestParam String query,
            @RequestParam Long userId) {
            
        Map<String, Object> response = new HashMap<>();

        try {
            // Check if user exists
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
                    
            // Check if user is premium/VIP
            if (!"premium".equals(user.getUserRank()) && !"vip".equals(user.getUserRank())) {
                response.put("success", false);
                response.put("message", "Premium account required");
                response.put("requirePremium", true);
                return ResponseEntity.ok(response);
            }
            
            // Validate input
            if (query == null || query.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Query cannot be empty");
                return ResponseEntity.badRequest().body(response);
            }

            // Call Python service to get AI solution
            Map<String, Object> aiResponse = callPythonAISolution(query);
            
            if (aiResponse != null && aiResponse.containsKey("success") && (Boolean)aiResponse.get("success")) {
                response.put("success", true);
                response.put("solution", aiResponse.get("solution"));
                response.put("query", query);
            } else {
                response.put("success", false);
                response.put("message", "Failed to get AI solution");
                return ResponseEntity.ok(response);
            }
            
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error processing AI solution request: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    private Map<String, Object> callPythonAISolution(String query) {
        try {
            String url = pythonServiceUrl + "/ai-solution";
            
            // Prepare request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("query", query);
            requestBody.put("prompt", aiSolutionPrompt);
            
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            
            // Call Python service
            ResponseEntity<String> pythonResponse = restTemplate.postForEntity(url, requestEntity, String.class);
            
            if (pythonResponse.getStatusCode().is2xxSuccessful()) {
                return objectMapper.readValue(pythonResponse.getBody(), Map.class);
            }
            
            return null;
            
        } catch (Exception e) {
            System.err.println("Error calling Python AI solution service: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}