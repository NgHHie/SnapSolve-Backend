package com.example.snapsolve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.snapsolve.models.Assignment;
import com.example.snapsolve.services.AssignmentService;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    // Lấy assignments theo danh sách ID
    @GetMapping("/byIds")
    public ResponseEntity<List<Assignment>> getAssignmentsByIds(@RequestParam List<Long> ids) {
        List<Assignment> assignments = assignmentService.getAssignmentsByIds(ids);
        return ResponseEntity.ok(assignments);
    }
}