package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.React;

@Repository
public interface ReactRepository extends JpaRepository<React, Long>{
    
}
