package com.example.snapsolve.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snapsolve.dto.PostDTO;
import com.example.snapsolve.models.Post;
import com.example.snapsolve.models.Topic;
import com.example.snapsolve.models.User;
import com.example.snapsolve.repositories.PostRepository;
import com.example.snapsolve.repositories.TopicRepository;
import com.example.snapsolve.repositories.UserRepository;
import com.example.snapsolve.services.PostService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    @Override
    public List<Post> getLatestPosts() {
        return postRepository.findAllByOrderByCreateDateDesc();
    }
    
    @Override
    public List<Post> searchPosts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }
        return postRepository.searchByKeyword(keyword);
    }

    @Override
    public Post getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }

    @Override
    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public List<Post> getPostsByTopicId(Long topicId) {
        return postRepository.findByTopicId(topicId);
    }

    @Override
    public Post createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImage(postDTO.getImage());
        post.setCreateDate(LocalDate.now());
        
        // Thiết lập user
        if (postDTO.getUserId() != null) {
            Optional<User> user = userRepository.findById(postDTO.getUserId());
            user.ifPresent(post::setUser);
        }
        
        // Thiết lập topics
        if (postDTO.getTopicIds() != null && !postDTO.getTopicIds().isEmpty()) {
            List<Topic> topics = new ArrayList<>();
            for (Long topicId : postDTO.getTopicIds()) {
                Optional<Topic> topic = topicRepository.findById(topicId);
                topic.ifPresent(topics::add);
            }
            post.setTopics(topics);
        }
        
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, PostDTO postDTO) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            
            // Cập nhật thông tin
            if (postDTO.getTitle() != null) {
                existingPost.setTitle(postDTO.getTitle());
            }
            
            if (postDTO.getContent() != null) {
                existingPost.setContent(postDTO.getContent());
            }
            
            if (postDTO.getImage() != null) {
                existingPost.setImage(postDTO.getImage());
            }
            
            // Cập nhật topics nếu có
            if (postDTO.getTopicIds() != null && !postDTO.getTopicIds().isEmpty()) {
                List<Topic> topics = new ArrayList<>();
                for (Long topicId : postDTO.getTopicIds()) {
                    Optional<Topic> topic = topicRepository.findById(topicId);
                    topic.ifPresent(topics::add);
                }
                existingPost.setTopics(topics);
            }
            
            return postRepository.save(existingPost);
        }
        return null;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}