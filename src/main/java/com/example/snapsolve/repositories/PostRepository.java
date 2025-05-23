package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    
    // Tìm kiếm post theo userId
    List<Post> findByUserId(Long userId);
    
    // Tìm kiếm post bằng từ khóa trong title hoặc content
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Post> searchByKeyword(@Param("keyword") String keyword);
    
    // Tìm posts theo topicId
    @Query("SELECT p FROM Post p JOIN p.topics t WHERE t.id = :topicId")
    List<Post> findByTopicId(@Param("topicId") Long topicId);
    
    // Lấy các posts mới nhất
    List<Post> findAllByOrderByCreateDateDesc();
}