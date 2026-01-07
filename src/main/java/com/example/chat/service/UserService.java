package com.example.chat.service;

import com.example.chat.model.User;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register user
    public boolean register(User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()) return false;
        userRepository.save(user);
        return true;
    }

    // Authenticate user
    public boolean authenticate(String username, String password){
        return userRepository.findByUsername(username)
                             .filter(u -> u.getPassword().equals(password))
                             .isPresent();
    }

    // ✅ Get all usernames
    public List<String> getAllUsernames() {
        return userRepository.findAll()
                             .stream()
                             .map(User::getUsername)
                             .collect(Collectors.toList());
    }
}
