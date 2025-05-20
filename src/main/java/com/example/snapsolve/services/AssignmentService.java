package com.example.snapsolve.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.snapsolve.dto.image.SimilarQuestion;
import com.example.snapsolve.models.Assignment;

@Service
public interface AssignmentService {
    public List<Assignment> getAssignments(List<SimilarQuestion> questions);
    public List<Assignment> getAssignmentsByIds(List<Long> ids);
} 
