package com.example.snapsolve.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snapsolve.exception.ResourceNotFoundException;
import com.example.snapsolve.models.Notification;
import com.example.snapsolve.models.User;
import com.example.snapsolve.repositories.NotificationRepository;
import com.example.snapsolve.services.NotificationService;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationWebSocketService webSocketService;

    @Override
    public Notification createNotification(String title, String content, User user) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setContent(content);
        notification.setNotiDate(LocalDate.now());
        notification.setUser(user);
        
        // Lưu notification vào database
        Notification savedNotification = notificationRepository.save(notification);
        
        // Gửi thông báo qua WebSocket đến user
        webSocketService.sendNotificationToUser(user.getId(), savedNotification);
        
        // Gửi update số lượng thông báo chưa đọc
        long unreadCount = getUnreadNotificationCount(user.getId());
        webSocketService.sendUnreadCountUpdate(user.getId(), unreadCount);
        
        System.out.println("Created and sent notification via WebSocket for user: " + user.getUsername());
        
        return savedNotification;
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserIdOrderByNotiDateDesc(userId);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }

    
    @Override
    public List<Notification> getUnreadNotificationsByUserId(Long userId) {
        return notificationRepository.findUnreadByUserIdOrderByNotiDateDesc(userId);
    }

    /**
     * Đếm số lượng thông báo chưa đọc của user
     */
    public long getUnreadNotificationCount(Long userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }

    /**
     * Đánh dấu tất cả notification của user là đã đọc
     */
    public void markAllAsReadByUserId(Long userId) {
        notificationRepository.markAllAsReadByUserId(userId);
        
        // Gửi update số lượng thông báo chưa đọc = 0
        webSocketService.sendUnreadCountUpdate(userId, 0);
    }

    @Override
    public void markAsRead(Long id) {
        // Notification notification = getNotificationById(id);
        // notification.setRead(true);
        // notificationRepository.save(notification);

        // long unreadCount = getUnreadNotificationCount(notification.getUser().getId());
        // webSocketService.sendUnreadCountUpdate(notification.getUser().getId(), unreadCount);
    }
}