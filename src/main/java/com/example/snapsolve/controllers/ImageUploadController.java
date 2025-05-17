package com.example.snapsolve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.snapsolve.dto.image.AnalysisResult;
import com.example.snapsolve.models.Assignment;
import com.example.snapsolve.services.AssignmentService;
import com.example.snapsolve.services.ImageAnalysisService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Value("${file.upload-dir:uploads/images}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam(value = "description", required = false, defaultValue = "Uploaded image") String description) {

        Map<String, Object> response = new HashMap<>();

        try {
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

            System.out.println(savedFile.getPath());
            AnalysisResult result = imageAnalysisService.analyzeImageFile(savedFile);

            // Add null check before calling getAssignments
            if (result.getAssignments() != null) {
                List<Assignment> assignments = assignmentService.getAssignments(result.getAssignments());
                response.put("assignments", assignments);
            } else {
                // Handle the case where no similar questions were found
                response.put("assignments", List.of());
            }

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Failed to upload image: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}