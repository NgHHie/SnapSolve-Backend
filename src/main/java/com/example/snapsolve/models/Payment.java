package com.example.snapsolve.models;



import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;          
    private String orderId;                
    private BigDecimal amount;             
    private String currency;               
    private String paymentMethod;          
    private String paymentStatus;          
    
    
    private String subscriptionType;   
    private Integer durationMonths; 
    
    private String paymentDate;     

    private String expiryDate;      
    
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}