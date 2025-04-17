package com.reactjavafullstack003.E_Learning_Platform_003.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactjavafullstack003.E_Learning_Platform_003.Exceptions.UserNotFoundException;
import com.reactjavafullstack003.E_Learning_Platform_003.model.User;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    /**
     * Fetch all users from the repository.
     * Throws UserNotFoundException if no users are found.
     */
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return users;
    }

    /**
     * Fetch a user by ID from the repository.
     * Throws UserNotFoundException if the user is not found.
     */
    public User getUserById(int userID) {
        return userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userID + " not found"));
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }
    /**
     * Save a new user to the repository.
     * Throws UserNotFoundException if a user with the same email already exists.
     */
    public User saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserNotFoundException("User already exists with email: " + user.getEmail());
        }

        // Log the user details
        System.out.println("Saving user: " + user);

        // Send welcome email
        String subject = "Welcome to E-Learning Platform";
        String message = "Dear " + user.getName() + ",\n\n" +
                "Thank you for registering on our E-Learning Platform. We are excited to have you on board!\n\n" +
                "Best regards,\n" +
                "E-Learning Team";

        try {
            emailService.sendEmail(user.getEmail(), subject, message);
            System.out.println("Email sent to: " + user.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

        // Save the user to the repository
        return userRepository.save(user);
    }

    /**
     * Update an existing user in the repository.
     * Throws UserNotFoundException if the user is not found.
     */
    public User updateUser(int userID, User user) {
        User existingUser = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userID + " not found"));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    /**
     * Delete a user by ID from the repository.
     * Throws UserNotFoundException if the user is not found.
     */
    public void deleteUser(int userID) {
        if (!userRepository.existsById(userID)) {
            throw new UserNotFoundException("User with ID " + userID + " not found");
        }
        userRepository.deleteById(userID);
    }
}