package com.example.snapsolve.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.snapsolve.dto.notification.NotificationDTO;
import com.example.snapsolve.models.Notification;

@Service
public class NotificationWebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Gửi thông báo đến một user cụ thể qua WebSocket
     */
    public void sendNotificationToUser(Long userId, Notification notification) {
        NotificationDTO notificationDTO = convertToDTO(notification);
        
        // Gửi đến user cụ thể
        messagingTemplate.convertAndSendToUser(
            userId.toString(),
            "/queue/notifications",
            notificationDTO
        );
        
        System.out.println("Sent notification to user " + userId + ": " + notification.getTitle());
    }

    /**
     * Gửi thông báo đến tất cả user (broadcast)
     */
    public void broadcastNotification(Notification notification) {
        NotificationDTO notificationDTO = convertToDTO(notification);
        
        messagingTemplate.convertAndSend("/topic/notifications", notificationDTO);
        
        System.out.println("Broadcasted notification: " + notification.getTitle());
    }

    /**
     * Gửi update số lượng thông báo chưa đọc
     */
    public void sendUnreadCountUpdate(Long userId, long unreadCount) {
        // Tạo object chứa thông tin unread count
        UnreadCountDTO unreadCountDTO = new UnreadCountDTO();
        unreadCountDTO.setUserId(userId);
        unreadCountDTO.setUnreadCount(unreadCount);
        unreadCountDTO.setType("unread_count");
        
        messagingTemplate.convertAndSendToUser(
            userId.toString(),
            "/queue/unread-count",
            unreadCountDTO
        );
        
        System.out.println("Sent unread count update to user " + userId + ": " + unreadCount);
    }

    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitle());
        dto.setContent(notification.getContent());
        dto.setNotiDate(notification.getNotiDate().toString());
        dto.setUserId(notification.getUser().getId());
        
        dto.setType("notification");
        return dto;
    }

    // Inner class cho UnreadCount DTO
    public static class UnreadCountDTO {
        private Long userId;
        private long unreadCount;
        private String type;

        // Getters và setters
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        
        public long getUnreadCount() { return unreadCount; }
        public void setUnreadCount(long unreadCount) { this.unreadCount = unreadCount; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
}