package com.gym.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Gym Membership Management System.
 * Starts the embedded Tomcat web server on port 8080.
 */
@SpringBootApplication
public class GymManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymManagementApplication.class, args);
        System.out.println("=================================================");
        System.out.println("Gym Membership Management Backend is running!");
        System.out.println("API Base URL: http://localhost:8080/api");
        System.out.println("=================================================");
    }
}
