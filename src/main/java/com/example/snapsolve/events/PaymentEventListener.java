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
        
        // Tạo nội dung thông báo dựa trên thông tin payment
        String title = "Thanh toán thành công";
        String content = createNotificationContent(payment);
        
        // Tạo thông báo cho user
        notificationService.createNotification(title, content, payment.getUser());
        
        System.out.println("Notification created for user: " + payment.getUser().getUsername() + 
                          " - Payment ID: " + payment.getId());
    }

    private String createNotificationContent(Payment payment) {
        StringBuilder content = new StringBuilder();
        
        if ("COMPLETED".equalsIgnoreCase(payment.getPaymentStatus()) || 
            "SUCCESS".equalsIgnoreCase(payment.getPaymentStatus())) {
            
            content.append("Chúc mừng bạn đã trở thành Premium User! ");
            
            if (payment.getSubscriptionType() != null) {
                content.append("Gói đăng ký: ").append(payment.getSubscriptionType()).append(". ");
            }
            
            if (payment.getDurationMonths() != null) {
                content.append("Thời hạn: ").append(payment.getDurationMonths()).append(" tháng. ");
            }
            
            if (payment.getExpiryDate() != null) {
                content.append("Hết hạn vào: ").append(payment.getExpiryDate().toLocalDate()).append(". ");
            }
            
            content.append("Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ của chúng tôi!");
            
        } else if ("PENDING".equalsIgnoreCase(payment.getPaymentStatus())) {
            content.append("Thanh toán của bạn đang được xử lý. ")
                   .append("Chúng tôi sẽ thông báo khi giao dịch hoàn tất.");
                   
        } else if ("FAILED".equalsIgnoreCase(payment.getPaymentStatus())) {
            content.append("Thanh toán không thành công. ")
                   .append("Vui lòng thử lại hoặc liên hệ hỗ trợ khách hàng.");
        } else {
            content.append("Cảm ơn bạn đã thực hiện thanh toán. ")
                   .append("Trạng thái: ").append(payment.getPaymentStatus());
        }
        
        return content.toString();
    }
}