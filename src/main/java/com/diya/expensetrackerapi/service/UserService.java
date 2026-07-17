package com.diya.expensetrackerapi.service;

import com.diya.expensetrackerapi.exception.InvalidCredentialsException;
import com.diya.expensetrackerapi.model.User;
import com.diya.expensetrackerapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//This is the middle layer where hashing of passwords will happen before its sent to repository for storage
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(PasswordEncoder passwordEncoder, UserRepository  userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    public User registerUser(String username, String rawPassword) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }
    public User loginUser(String username, String rawPassword) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(rawPassword, user.get().getPassword())) {
        return user.get();
        } else{
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}


