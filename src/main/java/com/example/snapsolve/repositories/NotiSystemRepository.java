package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.NotiSystem;

@Repository
public interface NotiSystemRepository extends JpaRepository<NotiSystem, Long>{
    
}
