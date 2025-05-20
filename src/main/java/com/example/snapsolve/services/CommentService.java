package com.example.snapsolve.services;

import com.example.snapsolve.dto.community.CommentDTO;
import com.example.snapsolve.models.Comment;


import java.util.List;

public interface CommentService {
    List<Comment> getRootCommentsByPostId(Long postId);
    List<Comment> getRepliesByParentCommentId(Long parentCommentId);
    Comment createComment(CommentDTO commentDTO);
    Comment createReply(CommentDTO commentDTO);
    Comment getCommentById(Long id);
    void deleteComment(Long id);
    long countRootCommentsByPostId(Long postId);
}