package com.example.snapsolve.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.snapsolve.models.Search;
import com.example.snapsolve.repositories.SearchRepository;
import com.example.snapsolve.repositories.UserRepository;
import com.example.snapsolve.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/search")
public class SearchHistoryController {

    @Autowired
    private SearchRepository searchRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Search>> getSearchHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int limit) {
            
        // Check if user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        // Get search history for user, ordered by createDate descending
        List<Search> searchHistory = searchRepository.findByUserIdOrderByCreateDateDesc(userId);
        
        // Apply limit (in case the query method with limit doesn't work properly)
        if (searchHistory.size() > limit) {
            searchHistory = searchHistory.stream()
                    .limit(limit)
                    .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(searchHistory);
    }
}