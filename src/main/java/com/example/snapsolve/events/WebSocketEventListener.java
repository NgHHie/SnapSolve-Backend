package com.example.snapsolve.events; // Hoặc package của bạn

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class WebSocketEventListener {

    

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal userPrincipal = headerAccessor.getUser();

        if (userPrincipal != null) {
            String username = userPrincipal.getName();
            System.out.println("A User Connected: " + username + " (Session ID: " + headerAccessor.getSessionId() + ")");
           
        } else {
            System.out.println("A User  STOMP Client Connected (Session ID: " + headerAccessor.getSessionId() + ")");
           
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal userPrincipal = headerAccessor.getUser();

        if (userPrincipal != null) {
            String username = userPrincipal.getName();
            System.out.println("User Disconnected: " + username + " (Session ID: " + headerAccessor.getSessionId() + ")");
            
        } else {
            System.out.println("A User STOMP Client Disconnected (Session ID: " + headerAccessor.getSessionId() + ")");
            
        }
    }
}