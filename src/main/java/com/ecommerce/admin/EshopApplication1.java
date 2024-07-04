package com.ecommerce.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ecommerce.admin.repository")
@ComponentScan(basePackages = {"com.ecommerce.admin", "com.ecommerce.admin.repository"})
public class EshopApplication1 {

    public static void main(String[] args) {
        SpringApplication.run(EshopApplication1.class, args);
    }
}
