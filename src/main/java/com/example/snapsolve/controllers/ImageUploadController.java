package com.example.snapsolve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.snapsolve.dto.image.AnalysisResult;
import com.example.snapsolve.models.Assignment;
import com.example.snapsolve.models.Search;
import com.example.snapsolve.models.User;
import com.example.snapsolve.repositories.SearchRepository;
import com.example.snapsolve.repositories.UserRepository;
import com.example.snapsolve.services.AssignmentService;
import com.example.snapsolve.services.ImageAnalysisService;
import com.example.snapsolve.exception.ResourceNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    @Autowired
    private ImageAnalysisService imageAnalysisService;

    @Autowired
    private AssignmentService assignmentService;
    
    @Autowired
    private SearchRepository searchRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir:uploads/images}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam(value = "description", required = false, defaultValue = "Uploaded image") String description,
            @RequestParam(value = "userId", required = true) Long userId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Check if user exists
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
            
            // Check if file is empty
            if (file.isEmpty()) {
                response.put("success", false);
                response.put("message", "Please select a file to upload");
                return ResponseEntity.badRequest().body(response);
            }

            // Create directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate a unique filename
            String timestamp = LocalDateTime.now()
                                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : ".jpg";
            String fileName = "img_" + timestamp + "_" + UUID.randomUUID().toString().substring(0, 8) + fileExtension;
            String imageId = UUID.randomUUID().toString();

            // Save the file
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());
            File savedFile = filePath.toFile();
            
            // Generate relative URL for accessing the image
            String imageUrl = "/images/" + fileName;

            // Return success response
            response.put("success", true);
            response.put("message", "Image uploaded successfully");
            response.put("imageUrl", imageUrl);
            response.put("imageId", imageId);
            response.put("fileName", fileName);
            response.put("fileSize", file.getSize());
            response.put("contentType", file.getContentType());
            response.put("description", description);

            // Process the image
            AnalysisResult result = imageAnalysisService.analyzeImageFile(savedFile);
            
            // Create a new search record
            Search search = new Search();
            search.setImage(imageUrl);
            search.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            search.setUser(user);
            
            // Create a descriptive question based on the image upload
            search.setQuestion(result.getQuestion());

            if (result.getAssignments() != null) {
                List<Assignment> assignments = assignmentService.getAssignments(result.getAssignments());
                response.put("assignments", assignments);
                
                // Save the top 5 assignment IDs
                if (!assignments.isEmpty()) {
                    int count = Math.min(assignments.size(), 5);
                    for (int i = 0; i < count; i++) {
                        switch (i) {
                            case 0: search.setAssignmentId1(assignments.get(i).getId()); break;
                            case 1: search.setAssignmentId2(assignments.get(i).getId()); break;
                            case 2: search.setAssignmentId3(assignments.get(i).getId()); break;
                            case 3: search.setAssignmentId4(assignments.get(i).getId()); break;
                            case 4: search.setAssignmentId5(assignments.get(i).getId()); break;
                        }
                    }
                }
            } else {
                // Handle the case where no similar questions were found
                response.put("assignments", List.of());
            }
            
            // Save the search record
            searchRepository.save(search);

            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Failed to upload image: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}