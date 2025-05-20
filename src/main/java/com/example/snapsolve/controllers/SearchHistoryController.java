package com.example.snapsolve.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String query) {
            
        // Check if user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        // Tạo Pageable cho phân trang với sắp xếp theo createDate giảm dần
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createDate"));
        
        if (query == null || query.trim().isEmpty()) {
            // Lấy dữ liệu phân trang
            Page<Search> searchHistoryPage = searchRepository.findByUserIdPageable(userId, pageable);
            return ResponseEntity.ok(searchHistoryPage.getContent());
        } else {
            // Đối với tìm kiếm, chúng ta vẫn cần lọc trong bộ nhớ
            final String lowerCaseQuery = query.toLowerCase();
            
            // Lấy nhiều hơn để có đủ dữ liệu sau khi lọc
            Pageable largerPageable = PageRequest.of(page, limit * 5, Sort.by(Sort.Direction.DESC, "createDate"));
            Page<Search> searchHistoryPage = searchRepository.findByUserIdPageable(userId, largerPageable);
            
            // Lọc và giới hạn kết quả
            List<Search> filteredResults = searchHistoryPage.getContent().stream()
                    .filter(search -> search.getQuestion() != null && 
                                      search.getQuestion().toLowerCase().contains(lowerCaseQuery))
                    .limit(limit)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(filteredResults);
        }
    }
}