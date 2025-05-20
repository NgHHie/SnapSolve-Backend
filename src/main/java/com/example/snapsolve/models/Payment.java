package com.example.snapsolve.models;



import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private LocalDateTime paymentDate;     
    private LocalDateTime expiryDate;      
    
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}