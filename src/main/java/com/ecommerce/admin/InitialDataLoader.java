package com.ecommerce.admin;

import com.ecommerce.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);

    @Autowired
    public InitialDataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        logger.info("Starting data initialization...");

        // Check if admin user exists, if not, register
        if (userService.findByUsername("admin") == null) {
            logger.info("Registering admin user...");
            userService.registerUser("admin", "0000", "admin@example.com", "ROLE_ADMIN");
            logger.info("Admin user registered successfully.");
        } else {
            logger.info("Admin user already exists.");
        }

        // Check if jim user exists, if not, register
        if (userService.findByUsername("jim") == null) {
            logger.info("Registering jim user...");
            userService.registerUser("jim", "2044", "jim@example.com", "ROLE_USER");
            logger.info("Jim user registered successfully.");
        } else {
            logger.info("Jim user already exists.");
        }

        logger.info("Data initialization complete.");
    }
}
