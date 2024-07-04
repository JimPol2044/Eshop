package com.ecommerce.admin.service;

public interface UserRegistrationService {
    void registerUser(String username, String password, String email, String role);
}
