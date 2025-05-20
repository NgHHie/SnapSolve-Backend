package com.example.snapsolve.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.snapsolve.dto.image.SimilarQuestion;
import com.example.snapsolve.models.Assignment;
import com.example.snapsolve.services.AssignmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/search")
public class TextSearchController {

    @Autowired
    private AssignmentService assignmentService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${ocr.service.url:http://localhost:5000}")
    private String pythonServiceUrl;

    @PostMapping("/text")
    public ResponseEntity<Map<String, Object>> searchByText(@RequestParam String query) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate input
            if (query == null || query.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Query cannot be empty");
                return ResponseEntity.badRequest().body(response);
            }

            // Call Python service to get similar questions
            List<SimilarQuestion> similarQuestions = callPythonTextSearch(query);
            
            if (similarQuestions == null || similarQuestions.isEmpty()) {
                response.put("success", true);
                response.put("message", "No similar questions found");
                response.put("query", query);
                response.put("assignments", List.of());
                return ResponseEntity.ok(response);
            }

            // Get assignments from the database
            List<Assignment> assignments = assignmentService.getAssignments(similarQuestions);

            // Build response
            response.put("success", true);
            response.put("message", "Text search completed successfully");
            response.put("query", query);
            response.put("assignments", assignments);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error processing text search: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @SuppressWarnings("unchecked")
    private List<SimilarQuestion> callPythonTextSearch(String query) {
        try {
            String url = pythonServiceUrl + "/search-by-text";
            
            // Prepare request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("query", query);
            
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            
            // Call Python service
            ResponseEntity<String> pythonResponse = restTemplate.postForEntity(url, requestEntity, String.class);
            
            if (pythonResponse.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseMap = objectMapper.readValue(pythonResponse.getBody(), Map.class);
                
                if ((Boolean) responseMap.get("success")) {
                    List<Map<String, Object>> similarQuestionsMap = 
                        (List<Map<String, Object>>) responseMap.get("similar_questions");
                    
                    if (similarQuestionsMap != null) {
                        return similarQuestionsMap.stream()
                            .map(questionMap -> {
                                SimilarQuestion question = new SimilarQuestion();
                                
                                // Handle id
                                Object idObj = questionMap.get("id");
                                if (idObj instanceof Number) {
                                    question.setId(((Number) idObj).longValue());
                                }
                                
                                // Handle similarity
                                Object simObj = questionMap.get("similarity");
                                if (simObj instanceof Number) {
                                    question.setSimilarity(((Number) simObj).floatValue());
                                }
                                
                                return question;
                            })
                            .toList();
                    }
                }
            }
            
            return List.of();
            
        } catch (Exception e) {
            System.err.println("Error calling Python text search service: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}