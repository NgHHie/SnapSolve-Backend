package com.example.snapsolve.services;

import com.example.snapsolve.models.Topic;
import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();
    Topic getTopicById(Long id);
    Topic createTopic(Topic topic);
    Topic updateTopic(Long id, Topic topicDetails);
    void deleteTopic(Long id);
    Topic findByName(String name);
}