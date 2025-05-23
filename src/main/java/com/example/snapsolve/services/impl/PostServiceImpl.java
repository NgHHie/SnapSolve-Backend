package com.example.snapsolve.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snapsolve.dto.community.PostDTO;
import com.example.snapsolve.exception.ResourceNotFoundException;
import com.example.snapsolve.models.Post;
import com.example.snapsolve.models.React;
import com.example.snapsolve.models.Topic;
import com.example.snapsolve.models.User;
import com.example.snapsolve.repositories.PostRepository;
import com.example.snapsolve.repositories.ReactRepository;
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

    @Autowired
    private ReactRepository reactRepository;

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
        post.setCreateDate(LocalDate.now());
        
        // Xử lý nhiều ảnh
        if (postDTO.getImages() != null && !postDTO.getImages().isEmpty()) {
            // Ảnh đầu tiên làm thumbnail
            post.setImage(postDTO.getImages().get(0));
            
            // Các ảnh còn lại vào additionalImages
            if (postDTO.getImages().size() > 1) {
                post.setAdditionalImages(new ArrayList<>(postDTO.getImages().subList(1, postDTO.getImages().size())));
            }
        }
        
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
            
            // Cập nhật ảnh
            if (postDTO.getImages() != null && !postDTO.getImages().isEmpty()) {
                // Ảnh đầu tiên làm thumbnail
                existingPost.setImage(postDTO.getImages().get(0));
                
                // Các ảnh còn lại vào additionalImages
                if (postDTO.getImages().size() > 1) {
                    existingPost.setAdditionalImages(new ArrayList<>(postDTO.getImages().subList(1, postDTO.getImages().size())));
                } else {
                    existingPost.setAdditionalImages(new ArrayList<>());
                }
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
    public Post likePost(Long postId, Long userId) {
        // Tìm bài viết theo ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        
        // Tìm người dùng theo ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        // Kiểm tra xem người dùng đã thích bài viết này chưa
        boolean alreadyLiked = post.getReact().stream()
                .anyMatch(react -> react.getUser().getId().equals(userId));
        
        // Nếu người dùng chưa thích bài viết, thêm lượt thích mới
        if (!alreadyLiked) {
            // Tạo đối tượng React mới
            React newReact = new React();
            newReact.setType("like"); // Loại phản ứng, có thể mở rộng sau này với "love", "wow", v.v.
            newReact.setCreateDate(LocalDate.now());
            newReact.setUser(user);
            newReact.setPost(post);
            
            // Lưu React vào database
            React savedReact = reactRepository.save(newReact);
            
            // Cập nhật danh sách React của bài viết
            List<React> reacts = post.getReact();
            reacts.add(savedReact);
            post.setReact(reacts);
            
            // Lưu bài viết đã cập nhật
            return postRepository.save(post);
        }
        
        // Nếu đã thích rồi, trả về bài viết mà không thay đổi
        return post;
    }
    
    @Override
    public Post unlikePost(Long postId, Long userId) {
        // Tìm bài viết theo ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        
        // Kiểm tra xem người dùng đã thích bài viết này chưa
        List<React> reacts = post.getReact();
        React reactToRemove = null;
        
        // Tìm lượt thích cần xóa
        for (React react : reacts) {
            if (react.getUser().getId().equals(userId)) {
                reactToRemove = react;
                break;
            }
        }
        
        // Nếu tìm thấy lượt thích, xóa nó
        if (reactToRemove != null) {
            // Xóa khỏi danh sách
            reacts.remove(reactToRemove);
            post.setReact(reacts);
            
            // Xóa khỏi database
            reactRepository.deleteById(reactToRemove.getId());
            
            // Lưu bài viết đã cập nhật
            return postRepository.save(post);
        }
        
        // Nếu không tìm thấy lượt thích, trả về bài viết mà không thay đổi
        return post;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getLikedPostsByUserId(Long userId) {
        // Lấy tất cả các React của người dùng có type là "like"
        List<React> userLikes = reactRepository.findByUserIdAndType(userId, "like");
    
        // Chuyển đổi danh sách React thành danh sách Post
        List<Post> likedPosts = new ArrayList<>();
        for (React react : userLikes) {
            likedPosts.add(react.getPost());
        }
    
        return likedPosts;
    }
}