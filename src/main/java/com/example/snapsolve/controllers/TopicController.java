package com.example.snapsolve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.snapsolve.dto.community.TopicDTO;
import com.example.snapsolve.models.Topic;
import com.example.snapsolve.services.TopicService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/ping")
    public String ping() {
        return "topic";
    }

    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        
        // Chuyển đổi sang DTO để tránh vấn đề JSON đệ quy
        List<TopicDTO> topicDTOs = topics.stream()
            .map(topic -> new TopicDTO(
                topic.getId(), 
                topic.getName(), 
                topic.getDescription(),
                topic.getPosts() != null ? topic.getPosts().size() : 0))
            .collect(Collectors.toList());
            
        return new ResponseEntity<>(topicDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopicById(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        if (topic != null) {
            TopicDTO topicDTO = new TopicDTO(
                topic.getId(),
                topic.getName(),
                topic.getDescription(),
                topic.getPosts() != null ? topic.getPosts().size() : 0
            );
            return new ResponseEntity<>(topicDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        Topic newTopic = topicService.createTopic(topic);
        return new ResponseEntity<>(newTopic, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic topic) {
        Topic updatedTopic = topicService.updateTopic(id, topic);
        if (updatedTopic != null) {
            return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}