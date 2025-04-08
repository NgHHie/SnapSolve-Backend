package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
    
}
