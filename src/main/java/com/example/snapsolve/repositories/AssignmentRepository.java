package com.example.snapsolve.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long>{
    List<Assignment> findByIdIn(List<Long> ids);
}
