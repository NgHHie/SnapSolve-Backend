package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.NotiReact;

@Repository
public interface NotiReactRepository extends JpaRepository<NotiReact, Long> {
    
}
