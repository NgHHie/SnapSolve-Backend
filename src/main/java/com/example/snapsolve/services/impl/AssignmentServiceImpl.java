package com.example.snapsolve.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snapsolve.dto.image.SimilarQuestion;
import com.example.snapsolve.models.Assignment;
import com.example.snapsolve.repositories.AssignmentRepository;
import com.example.snapsolve.services.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService{
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    public List<Assignment> getAssignments(List<SimilarQuestion> similarQuestions) {
        if (similarQuestions == null || similarQuestions.isEmpty()) {
            return List.of(); // Return empty list instead of NPE
        }
        
        // Extract IDs while maintaining the order
        List<Long> similarQuestionIds = similarQuestions.stream()
                .map(SimilarQuestion::getId)
                .collect(Collectors.toList());
        
        // Fetch all matching assignments
        List<Assignment> assignments = assignmentRepository.findByIdIn(similarQuestionIds);
        
        // Create a map for quick lookup by ID
        Map<Long, Assignment> assignmentMap = assignments.stream()
                .collect(Collectors.toMap(Assignment::getId, assignment -> assignment));
        
        // Create a new list that preserves the original order from similarQuestions
        List<Assignment> orderedAssignments = new ArrayList<>();
        for (Long id : similarQuestionIds) {
            Assignment assignment = assignmentMap.get(id);
            if (assignment != null) {
                orderedAssignments.add(assignment);
            }
        }
        
        return orderedAssignments;
    }

    @Override
    public List<Assignment> getAssignmentsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of(); // Return empty list instead of NPE
        }
        
        // Fetch all matching assignments
        List<Assignment> assignments = assignmentRepository.findByIdIn(ids);
        
        // Create a map for quick lookup by ID
        Map<Long, Assignment> assignmentMap = assignments.stream()
                .collect(Collectors.toMap(Assignment::getId, assignment -> assignment));
        
        // Create a new list that preserves the original order from similarQuestions
        List<Assignment> orderedAssignments = new ArrayList<>();
        for (Long id : ids) {
            Assignment assignment = assignmentMap.get(id);
            if (assignment != null) {
                orderedAssignments.add(assignment);
            }
        }
        
        return orderedAssignments;
    }
}
