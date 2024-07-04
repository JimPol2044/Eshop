package com.ecommerce.admin.service;

import com.ecommerce.admin.model.User;

public interface UserService {

    User findByUsername(String username);

    boolean authenticate(String username, String password);

    void saveUser(User user);

    void registerUser(String username, String password, String email, String role);
}
