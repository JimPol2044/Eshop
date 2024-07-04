package com.ecommerce.admin.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptPasswordHashingExample {

    public static void main(String[] args) {
        String password1 = "0000";
        String password2 = "2044";

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Hash the passwords
        String hashedPassword1 = passwordEncoder.encode(password1);
        String hashedPassword2 = passwordEncoder.encode(password2);

        System.out.println("Hashed Password for '0000': " + hashedPassword1);
        System.out.println("Hashed Password for '2044': " + hashedPassword2);
    }
}
