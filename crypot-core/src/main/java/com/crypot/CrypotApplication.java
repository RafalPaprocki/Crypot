package com.crypot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrypotApplication {
    public static void main(String[] args) {
        System.out.println("started");
        SpringApplication.run(CrypotApplication.class, args);
    }
}
