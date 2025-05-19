package com.example.snapsolve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.snapsolve.dto.user.UserDTO;
import com.example.snapsolve.services.UserService;
import com.example.snapsolve.services.FileStorageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    @Qualifier("serverBaseUrl")
    private String serverBaseUrl;

    @PostMapping("/files/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.storeFile(file);
            String fileUrl = "/images/" + fileName;
            
            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("fileUrl", fileUrl);
            response.put("fullUrl", serverBaseUrl + fileUrl);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/files/upload-multiple")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            List<Map<String, String>> responseList = new ArrayList<>();
            
            for (MultipartFile file : files) {
                String fileName = fileStorageService.storeFile(file);
                String fileUrl = "/images/" + fileName;
                
                Map<String, String> fileInfo = new HashMap<>();
                fileInfo.put("fileName", fileName);
                fileInfo.put("fileUrl", fileUrl);
                fileInfo.put("fullUrl", serverBaseUrl + fileUrl);
                
                responseList.add(fileInfo);
            }
            
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @PostMapping("/users/{id}/avatar")
    public ResponseEntity<?> uploadUserAvatar(
            @PathVariable("id") Long userId,
            @RequestParam("avatar") MultipartFile file) {
        try {
            
            String fileName = fileStorageService.storeFile(file);
            String fileUrl = "/images/" + fileName;
            
           
            UserDTO updatedUser = userService.updateUserAvatar(userId, fileUrl);
            
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}