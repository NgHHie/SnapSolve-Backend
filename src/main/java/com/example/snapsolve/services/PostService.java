package com.example.snapsolve.services;

import com.example.snapsolve.dto.PostDTO;
import com.example.snapsolve.models.Post;
import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    List<Post> getLatestPosts();
    Post getPostById(Long id);
    List<Post> getPostsByUserId(Long userId);
    List<Post> getPostsByTopicId(Long topicId);
    List<Post> searchPosts(String keyword);
    Post createPost(PostDTO postDTO);
    Post updatePost(Long id, PostDTO postDTO);
    void deletePost(Long id);
    Post likePost(Long postId, Long userId);
    Post unlikePost(Long postId, Long userId);
}