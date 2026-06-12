package com.example.secureapp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.secureapp")
public class TaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
