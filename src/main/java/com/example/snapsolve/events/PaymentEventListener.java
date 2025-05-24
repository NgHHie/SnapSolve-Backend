package com.example.snapsolve.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.snapsolve.models.Payment;
import com.example.snapsolve.services.NotificationService;

@Component
public class PaymentEventListener {

    @Autowired
    private NotificationService notificationService;

    @EventListener
    public void handlePaymentCreated(PaymentCreatedEvent event) {
        Payment payment = event.getPayment();
        
        
        String title = "Payment Successful";
        String content = createNotificationContent(payment);
        
        // Tạo thông báo cho user
        notificationService.createNotification(title, content, payment.getUser());
        
        System.out.println("Notification created for user: " + payment.getUser().getUsername() + 
                          " - Payment ID: " + payment.getId());
    }



    //ham tao noi dung cho thong bao 
        private String createNotificationContent(Payment payment) {
            StringBuilder content = new StringBuilder();

            if ("COMPLETED".equalsIgnoreCase(payment.getPaymentStatus()) || 
                "SUCCESS".equalsIgnoreCase(payment.getPaymentStatus())) {

                content.append("Congratulations! You are now a Premium User! ");

                if (payment.getSubscriptionType() != null) {
                    content.append("Subscription plan: ").append(payment.getSubscriptionType()).append(". ");
                }

                if (payment.getDurationMonths() != null) {
                    content.append("Duration: ").append(payment.getDurationMonths()).append(" month(s). ");
                }

                content.append("Thank you for trusting and using our service!");

            } else if ("PENDING".equalsIgnoreCase(payment.getPaymentStatus())) {
                content.append("Your payment is currently being processed. ")
                    .append("We will notify you once the transaction is complete.");
                    
            } else if ("FAILED".equalsIgnoreCase(payment.getPaymentStatus())) {
                content.append("Payment failed. ")
                    .append("Please try again or contact customer support.");
            } else {
                content.append("Thank you for your payment. ")
                    .append("Status: ").append(payment.getPaymentStatus());
            }

            return content.toString();
        }

}