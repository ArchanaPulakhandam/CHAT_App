package com.example.chat.controller;

import com.example.chat.model.User;
import com.example.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")  // allow JS requests from your frontend
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        return userService.register(user) ?
                ResponseEntity.ok("Registered") :
                ResponseEntity.badRequest().body("Username exists");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        return userService.authenticate(user.getUsername(), user.getPassword()) ?
                ResponseEntity.ok("Login successful") :
                ResponseEntity.status(401).body("Invalid credentials");
    }

    // ✅ NEW: Fetch all usernames
    @GetMapping("/users")
    public ResponseEntity<List<String>> getAllUsers() {
        List<String> usernames = userService.getAllUsernames();
        return ResponseEntity.ok(usernames);
    }
}
