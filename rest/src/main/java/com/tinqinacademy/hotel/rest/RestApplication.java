package com.tinqinacademy.hotel.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.tinqinacademy.hotel"})
@EntityScan(basePackages = {"com.tinqinacademy.hotel.persistence.models"})
@EnableJpaRepositories(basePackages = {"com.tinqinacademy.hotel.persistence.repositories"})
public class RestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
