package com.example.snapsolve.events;

import com.example.snapsolve.models.Payment;
import org.springframework.context.ApplicationEvent;

public class PaymentCreatedEvent extends ApplicationEvent {
    private final Payment payment;

    public PaymentCreatedEvent(Object source, Payment payment) {
        super(source);
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }
}