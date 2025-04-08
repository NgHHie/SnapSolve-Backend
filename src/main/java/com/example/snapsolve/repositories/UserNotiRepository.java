package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.UserNoti;

@Repository
public interface UserNotiRepository extends JpaRepository<UserNoti, Long>{
    
}
