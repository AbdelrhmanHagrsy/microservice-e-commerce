package com.example.emailnotifservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EmailNotifServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailNotifServiceApplication.class, args);
    }

}
