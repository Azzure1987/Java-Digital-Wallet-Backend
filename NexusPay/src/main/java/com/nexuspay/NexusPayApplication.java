package com.nexuspay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the NexusPay Fintech API.
 * The @SpringBootApplication annotation enables auto-configuration and component scanning.
 */
@SpringBootApplication
public class NexusPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusPayApplication.class, args);
    }
}
