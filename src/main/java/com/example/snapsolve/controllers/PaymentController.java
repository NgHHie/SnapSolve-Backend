package com.example.snapsolve.controllers;

import com.example.snapsolve.dto.payment.PaymentDTO;
import com.example.snapsolve.events.PaymentCreatedEvent;
import com.example.snapsolve.models.Payment;
import com.example.snapsolve.models.User;
import com.example.snapsolve.repositories.PaymentRepository;
import com.example.snapsolve.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    
    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO payment) {
        
        Long userId = payment.getUserId();

        Payment paymentEntity = new Payment();
        paymentEntity.setTransactionId(payment.getTransactionId());
        paymentEntity.setOrderId(payment.getOrderId());
        paymentEntity.setAmount(payment.getAmount());
        paymentEntity.setCurrency(payment.getCurrency());

        paymentEntity.setPaymentMethod(payment.getPaymentMethod());
        paymentEntity.setPaymentStatus(payment.getPaymentStatus());
        paymentEntity.setSubscriptionType(payment.getSubscriptionType());
        paymentEntity.setDurationMonths(payment.getDurationMonths());
        paymentEntity.setPaymentDate(payment.getPaymentDate());
        paymentEntity.setExpiryDate(payment.getExpiryDate());
        
        System.out.println("Creating payment: " + payment);
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        
        paymentEntity.setUser(userOpt.get());
        
      // Using LocalDateTime instead of LocalDate
        if (paymentEntity.getPaymentDate() == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            paymentEntity.setPaymentDate(formattedDateTime);
        }
        
        
        Payment savedPayment = paymentRepository.save(paymentEntity);
        
        
        eventPublisher.publishEvent(new PaymentCreatedEvent(this, savedPayment));
        
        return ResponseEntity.ok(savedPayment);
    }
    
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getUserPayments(@PathVariable Long userId) {
        List<Payment> payments = paymentRepository.findByUserId(userId);
        return ResponseEntity.ok(payments);
    }
    
    // Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            return ResponseEntity.ok(payment.get());
        }
        return ResponseEntity.notFound().build();
    }
}