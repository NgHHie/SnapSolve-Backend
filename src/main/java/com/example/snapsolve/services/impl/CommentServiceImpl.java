package com.example.snapsolve.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snapsolve.dto.community.CommentDTO;
import com.example.snapsolve.models.Comment;
import com.example.snapsolve.models.Post;
import com.example.snapsolve.models.User;
import com.example.snapsolve.repositories.CommentRepository;
import com.example.snapsolve.repositories.PostRepository;
import com.example.snapsolve.repositories.UserRepository;
import com.example.snapsolve.services.CommentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Comment> getRootCommentsByPostId(Long postId) {
        return commentRepository.findRootCommentsByPostId(postId);
    }

    @Override
    public List<Comment> getRepliesByParentCommentId(Long parentCommentId) {
        return commentRepository.findRepliesByParentCommentId(parentCommentId);
    }

    @Override
    public Comment createComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreateDate(LocalDate.now());
        
        // Xử lý nhiều ảnh
        if (commentDTO.getImages() != null && !commentDTO.getImages().isEmpty()) {
            comment.setImages(commentDTO.getImages());
        }
        
        // Thiết lập user
        if (commentDTO.getUserId() != null) {
            Optional<User> user = userRepository.findById(commentDTO.getUserId());
            user.ifPresent(comment::setUser);
        }
        
        // Thiết lập post
        if (commentDTO.getPostId() != null) {
            Optional<Post> post = postRepository.findById(commentDTO.getPostId());
            post.ifPresent(comment::setPost);
        }
        
        // Comment gốc không có parentComment
        comment.setParentComment(null);
        
        return commentRepository.save(comment);
    }

    @Override
    public Comment createReply(CommentDTO commentDTO) {
        Comment reply = new Comment();
        reply.setContent(commentDTO.getContent());
        reply.setCreateDate(LocalDate.now());
        
        // Xử lý nhiều ảnh
        if (commentDTO.getImages() != null && !commentDTO.getImages().isEmpty()) {
            reply.setImages(commentDTO.getImages());
        }
        
        // Thiết lập user
        if (commentDTO.getUserId() != null) {
            Optional<User> user = userRepository.findById(commentDTO.getUserId());
            user.ifPresent(reply::setUser);
        }
        
        // Thiết lập post (lấy từ parent comment)
        if (commentDTO.getParentCommentId() != null) {
            Optional<Comment> parentComment = commentRepository.findById(commentDTO.getParentCommentId());
            if (parentComment.isPresent()) {
                reply.setParentComment(parentComment.get());
                reply.setPost(parentComment.get().getPost());
            }
        }
        
        return commentRepository.save(reply);
    }

    @Override
    public Comment getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElse(null);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public long countRootCommentsByPostId(Long postId) {
        return commentRepository.countRootCommentsByPostId(postId);
    }
}