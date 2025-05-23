package com.example.snapsolve.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.Search;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    
    // Tìm kiếm lịch sử theo userId, sắp xếp theo createDate giảm dần
    @Query("SELECT s FROM Search s WHERE s.user.id = :userId ORDER BY s.createDate DESC")
    List<Search> findByUserIdOrderByCreateDateDesc(@Param("userId") Long userId);
    
    // Phiên bản có giới hạn số lượng
    @Query("SELECT s FROM Search s WHERE s.user.id = :userId ORDER BY s.createDate DESC")
    List<Search> findTopByUserIdOrderByCreateDateDesc(@Param("userId") Long userId, @Param("limit") int limit);
    
    // Sử dụng Pageable để phân trang thay vì custom query với LIMIT và OFFSET
    @Query("SELECT s FROM Search s WHERE s.user.id = :userId")
    Page<Search> findByUserIdPageable(@Param("userId") Long userId, Pageable pageable);
}