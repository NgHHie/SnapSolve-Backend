package com.example.snapsolve.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snapsolve.models.Topic;
import com.example.snapsolve.repositories.TopicRepository;
import com.example.snapsolve.services.TopicService;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic getTopicById(Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        return topic.orElse(null);
    }

    @Override
    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Topic updateTopic(Long id, Topic topicDetails) {
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()) {
            Topic existingTopic = topic.get();
            existingTopic.setName(topicDetails.getName());
            existingTopic.setDescription(topicDetails.getDescription());
            return topicRepository.save(existingTopic);
        }
        return null;
    }

    @Override
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public Topic findByName(String name) {
        return topicRepository.findByName(name);
    }
}