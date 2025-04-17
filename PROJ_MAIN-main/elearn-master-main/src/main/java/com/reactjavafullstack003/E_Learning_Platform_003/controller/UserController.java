package com.reactjavafullstack003.E_Learning_Platform_003.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactjavafullstack003.E_Learning_Platform_003.model.User;
import com.reactjavafullstack003.E_Learning_Platform_003.service.JwtService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.NotificationService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.UserService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {



    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{userID}")
    public ResponseEntity<User> getUserById(@PathVariable int userID) {
        logger.info("Fetching user with ID: {}", userID);
        User user = userService.getUserById(userID);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<String> saveUser(@Valid @RequestBody User user) {
        logger.info("Creating user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userService.saveUser(user);
        if (createdUser != null) {
            notificationService.saveNotification("User created: " + createdUser.getEmail());
            return new ResponseEntity<>("User has been Created Successfully!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update an existing user
    @PutMapping("/{userID}")
    public ResponseEntity<String> updateUser(@PathVariable int userID, @Valid @RequestBody User user) {
        logger.info("Updating user with ID: {}", userID);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User updatedUser = userService.updateUser(userID, user);
        if (updatedUser != null) {
            notificationService.saveNotification("User updated: " + updatedUser.getEmail());
            return new ResponseEntity<>("User has been Updated Successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // @GetMapping("/profile")
    // public ResponseEntity<User> getUserProfile(Authentication authentication) {
    //     String email = authentication.getName();
    //     User user = userService.getUserByEmail(email);
    //     return new ResponseEntity<>(user, HttpStatus.OK);
    // }    
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userService.getUserByEmail(email);
            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Delete a user
    @DeleteMapping("/{userID}")
    public ResponseEntity<String> deleteUser(@PathVariable int userID) {
        logger.info("Deleting user with ID: {}", userID);
        userService.deleteUser(userID);
        notificationService.saveNotification("User deleted with ID: " + userID);
        return new ResponseEntity<>("User has been Deleted Successfully!", HttpStatus.OK);
    }
    @PostMapping("/login")
public ResponseEntity<String> login(@RequestBody User user) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
    );

    if (authentication.isAuthenticated()) {
        User authenticatedUser = userService.getUserByEmail(user.getEmail());
        String token = jwtService.generateToken(
            user.getEmail(),
            authentication.getAuthorities(),
            authenticatedUser.getUserID(), // Pass userID
            "USER" // Pass role
        );
        return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
}