package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.Search;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long>{
    
}
