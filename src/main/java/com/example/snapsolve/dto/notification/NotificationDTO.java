package com.example.snapsolve.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private String title;
    private String content;
    private String notiDate;
    private Long userId;
    private boolean isRead = false;
    private String type = "notification"; 
}