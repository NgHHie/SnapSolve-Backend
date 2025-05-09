package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    
}
