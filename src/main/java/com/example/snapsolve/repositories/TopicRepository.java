package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    // Tìm topic theo tên
    Topic findByName(String name);
}