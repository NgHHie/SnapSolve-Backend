package com.example.snapsolve.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Configuration
public class ServerConfig {

    @Value("${server.port:9999}")
    private String serverPort;

    @Bean(name = "serverBaseUrl")
    public String serverBaseUrl() {
        return "http://" + getServerIpAddress() + ":" + serverPort;
    }

    /**
     * Phương thức này sẽ tự động lấy địa chỉ IP của máy chủ,
     * ưu tiên địa chỉ IPv4 không phải loopback
     */
    private String getServerIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                
                // Bỏ qua các interface không hoạt động và loopback
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    
                    // Ưu tiên địa chỉ IPv4 và không phải localhost
                    String hostAddress = address.getHostAddress();
                    if (address.isLoopbackAddress() || hostAddress.contains(":")) {
                        continue; // Bỏ qua IPv6 và địa chỉ loopback
                    }
                    
                    System.out.println("Server IP address detected: " + hostAddress);
                    return hostAddress;
                }
            }
        } catch (SocketException e) {
            System.err.println("Could not determine IP address: " + e.getMessage());
        }
        
        // Fallback to localhost if no suitable address found
        System.out.println("No suitable IP address found, using localhost");
        return "localhost";
    }
}