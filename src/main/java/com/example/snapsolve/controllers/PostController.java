package com.example.snapsolve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.snapsolve.dto.community.PostDTO;
import com.example.snapsolve.exception.ResourceNotFoundException;
import com.example.snapsolve.models.Post;
import com.example.snapsolve.services.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/ping")
    public String ping() {
        return "post";
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    
    @GetMapping("/latest")
    public ResponseEntity<List<Post>> getLatestPosts() {
        List<Post> posts = postService.getLatestPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String keyword) {
        List<Post> posts = postService.searchPosts(keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<Post>> getPostsByTopicId(@PathVariable Long topicId) {
        List<Post> posts = postService.getPostsByTopicId(topicId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) {
        Post newPost = postService.createPost(postDTO);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        Post updatedPost = postService.updatePost(id, postDTO);
        if (updatedPost != null) {
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/liked/{userId}")
    public ResponseEntity<List<Post>> getLikedPostsByUserId(@PathVariable Long userId) {
        List<Post> likedPosts = postService.getLikedPostsByUserId(userId);
        return new ResponseEntity<>(likedPosts, HttpStatus.OK);
    }

    /**
     * API để thích một bài viết
     */
    @PostMapping("/{id}/like")
    public ResponseEntity<Post> likePost(@PathVariable("id") Long postId, @RequestParam Long userId) {
        try {
            Post updatedPost = postService.likePost(postId, userId);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * API để bỏ thích một bài viết
     */
    @PostMapping("/{id}/unlike")
    public ResponseEntity<Post> unlikePost(@PathVariable("id") Long postId, @RequestParam Long userId) {
        try {
            Post updatedPost = postService.unlikePost(postId, userId);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}