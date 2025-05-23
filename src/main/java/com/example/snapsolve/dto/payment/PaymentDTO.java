package com.example.snapsolve.dto.payment;

import java.math.BigDecimal;

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
public class PaymentDTO {

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
    private Long userId; // Assuming you have a userId field to associate with the User entity]

}
