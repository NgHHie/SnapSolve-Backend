package com.example.snapsolve.controllers;

import com.example.snapsolve.events.PaymentCreatedEvent;
import com.example.snapsolve.models.Payment;
import com.example.snapsolve.models.User;
import com.example.snapsolve.repositories.PaymentRepository;
import com.example.snapsolve.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<?> createPayment(@RequestBody Payment payment, @RequestParam Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        
        payment.setUser(userOpt.get());
        
        
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }
        
        
        Payment savedPayment = paymentRepository.save(payment);
        
        
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