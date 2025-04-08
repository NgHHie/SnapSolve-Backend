package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.NotiPost;

@Repository
public interface NotiPostRepository extends JpaRepository<NotiPost, Long>{
    
}
