package com.example.snapsolve.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.snapsolve.models.Search;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long>{
    
    // Tìm kiếm lịch sử theo userId, sắp xếp theo createDate giảm dần và giới hạn số lượng kết quả
    @Query(value = "SELECT s FROM Search s WHERE s.user.id = :userId ORDER BY s.createDate DESC")
    List<Search> findByUserIdOrderByCreateDateDesc(@Param("userId") Long userId);
    
    // Phiên bản có giới hạn số lượng
    @Query(value = "SELECT s FROM Search s WHERE s.user.id = :userId ORDER BY s.createDate DESC", nativeQuery = false)
    List<Search> findTopByUserIdOrderByCreateDateDesc(@Param("userId") Long userId, @Param("limit") int limit);
}