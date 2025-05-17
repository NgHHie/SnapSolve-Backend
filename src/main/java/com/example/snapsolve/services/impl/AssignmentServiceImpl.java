package com.example.snapsolve.services.impl;

import java.util.List;
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
        List<Long> similarQuestionIds = similarQuestions.stream()
                .map(SimilarQuestion::getId)
                .collect(Collectors.toList());
        
        return assignmentRepository.findByIdIn(similarQuestionIds);
    }
}
