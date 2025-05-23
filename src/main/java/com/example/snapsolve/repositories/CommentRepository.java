package com.example.snapsolve.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    // Tìm tất cả comment gốc của một bài viết (không phải reply)
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.parentComment IS NULL ORDER BY c.createDate DESC")
    List<Comment> findRootCommentsByPostId(@Param("postId") Long postId);
    
    // Tìm tất cả reply của một comment
    @Query("SELECT c FROM Comment c WHERE c.parentComment.id = :parentCommentId ORDER BY c.createDate ASC")
    List<Comment> findRepliesByParentCommentId(@Param("parentCommentId") Long parentCommentId);
    
    // Đếm số lượng comment gốc của một bài viết
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId AND c.parentComment IS NULL")
    long countRootCommentsByPostId(@Param("postId") Long postId);
}
