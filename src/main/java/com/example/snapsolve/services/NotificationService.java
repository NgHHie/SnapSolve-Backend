package com.example.snapsolve.services;

import com.example.snapsolve.models.Notification;
import com.example.snapsolve.models.User;

import java.util.List;

public interface NotificationService {
    Notification createNotification(String title, String content, User user);
    List<Notification> getNotificationsByUserId(Long userId);
    List<Notification> getUnreadNotificationsByUserId(Long userId);
    Notification getNotificationById(Long id);
    long getUnreadNotificationCount(Long userId);
    void markAsRead(Long id);
    void markAllAsReadByUserId(Long userId);
    void deleteNotification(Long id);
}