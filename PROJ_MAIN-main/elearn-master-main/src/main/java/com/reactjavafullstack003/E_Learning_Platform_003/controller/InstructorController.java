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
import org.springframework.security.core.userdetails.UserDetails;
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

import com.reactjavafullstack003.E_Learning_Platform_003.model.Instructor;
import com.reactjavafullstack003.E_Learning_Platform_003.model.User;
import com.reactjavafullstack003.E_Learning_Platform_003.service.InstructorService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.JwtService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.NotificationService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    private static final Logger logger = LogManager.getLogger(InstructorController.class);

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    // Get all instructors
    @GetMapping
    public List<Instructor> getAllInstructors() {
        logger.info("Fetching all instructors");
        return instructorService.getAllInstructors();
    }
    @GetMapping("/profile")
    public ResponseEntity<Instructor> getInstructorProfile(Authentication authentication) {
    String email = authentication.getName();
    Instructor instructor = instructorService.getInstructorByEmail(email);
    return new ResponseEntity<>(instructor, HttpStatus.OK);
}

    // Get instructor by ID
    @GetMapping("/{instructorID}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable int instructorID) {
        logger.info("Fetching instructor with ID: {}", instructorID);
        Instructor instructor = instructorService.getInstructorById(instructorID);
        if (instructor != null) {
            return new ResponseEntity<>(instructor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new instructor
    @PostMapping
    public ResponseEntity<String> createInstructor(@Valid @RequestBody Instructor instructor) {
        logger.info("Creating instructor");
        notificationService.saveNotification("Creating Instructors");
        instructor.setPassword(passwordEncoder.encode(instructor.getPassword()));
        instructorService.saveInstructor(instructor);
        return new ResponseEntity<>("Instructor has been Created!", HttpStatus.CREATED);
    }

    // Update an existing instructor
    @PutMapping("/{instructorID}")
    public ResponseEntity<String> updateInstructor(@PathVariable int instructorID, @Valid @RequestBody Instructor instructor) {
        logger.info("Updating instructor with ID: {}", instructorID);
        instructor.setPassword(passwordEncoder.encode(instructor.getPassword()));
        Instructor updatedInstructor = instructorService.updateInstructor(instructorID, instructor);
        if (updatedInstructor != null) {
            notificationService.saveNotification("Updating Instructors");
            return new ResponseEntity<>("Instructor has been Updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // Delete an instructor
    @DeleteMapping("/{instructorID}")
    public ResponseEntity<String> deleteInstructor(@PathVariable int instructorID) {
        logger.info("Deleting instructor with ID: {}", instructorID);
        notificationService.saveNotification("Deleting Instructors");
        instructorService.deleteInstructor(instructorID);
        return new ResponseEntity<>("Instructor has been Deleted", HttpStatus.OK);
    }

    @PostMapping("/login")
public ResponseEntity<String> login(@RequestBody Instructor instructor) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(instructor.getEmail(), instructor.getPassword())
    );

    if (authentication.isAuthenticated()) {
        Instructor authenticatedInstructor = instructorService.getInstructorByEmail(instructor.getEmail());
        String token = jwtService.generateToken(
            instructor.getEmail(),
            authentication.getAuthorities(),
            authenticatedInstructor.getInstructorID(), // Pass instructorID
            "INSTRUCTOR" // Pass role
        );
        return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}

}