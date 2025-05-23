package com.example.snapsolve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.snapsolve.models.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
    
    // Tìm tất cả notification của một user, sắp xếp theo ngày tạo giảm dần
    List<Notification> findByUserIdOrderByNotiDateDesc(Long userId);
    
    // Đếm số lượng notification của một user
    long countByUserId(Long userId);
    
    // Đếm số lượng notification chưa đọc của một user (nếu có trường isRead)
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.isRead = false")
    long countUnreadByUserId(@Param("userId") Long userId);
    
    // Tìm notification chưa đọc của một user
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.isRead = false ORDER BY n.notiDate DESC")
    List<Notification> findUnreadByUserIdOrderByNotiDateDesc(@Param("userId") Long userId);
    
    // Đánh dấu tất cả notification của user là đã đọc
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user.id = :userId")
    void markAllAsReadByUserId(@Param("userId") Long userId);
    
    // Xóa tất cả notification của một user
    @Modifying
    @Transactional
    void deleteByUserId(Long userId);
}