package com.ecommerce.admin.service;

import com.ecommerce.admin.model.User;
import com.ecommerce.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordHashingService passwordHashingService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordHashingService passwordHashingService) {
        this.userRepository = userRepository;
        this.passwordHashingService = passwordHashingService;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        String hashedPassword = passwordHashingService.encodePassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = findByUsername(username);
        if (user == null) {
            return false;
        }
        return passwordHashingService.matchPassword(password, user.getPassword());
    }

    @Override
    public void registerUser(String username, String password, String email, String role) {
        String hashedPassword = passwordHashingService.encodePassword(password);
        User newUser = new User(username, hashedPassword, email, role);
        userRepository.save(newUser);
    }
}
