package com.reactjavafullstack003.E_Learning_Platform_003.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.reactjavafullstack003.E_Learning_Platform_003.Exceptions.ResourceNotFoundException;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Enrollment;
import com.reactjavafullstack003.E_Learning_Platform_003.model.User;
import com.reactjavafullstack003.E_Learning_Platform_003.service.EnrollmentService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.NotificationService;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Validated
@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private static final Logger logger = LogManager.getLogger(EnrollmentController.class);

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private NotificationService notificationService;

    /**
     * Get all enrollments.
     */
    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        logger.info("Fetching all enrollments");
        return enrollmentService.getAllEnrollments();
    }

    /**
     * Get an enrollment by ID.
     */
    @GetMapping("/{enrollmentID}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable int enrollmentID) {
        logger.info("Fetching enrollment with ID: {}", enrollmentID);
        Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentID);
        if (enrollment != null) {
            return new ResponseEntity<>(enrollment, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Enrollment not found with id: " + enrollmentID);
        }
    }

    /**
     * Save a new enrollment.
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveEnrollment(@Valid @RequestBody Enrollment enrollment) {
        logger.info("Saving enrollment");
        notificationService.saveNotification("Saving enrollment");
        enrollmentService.enroll(enrollment);
        return new ResponseEntity<>("You have Successfully Enrolled!", HttpStatus.CREATED);
    }

    /**
     * Update an existing enrollment.
     */
    @PutMapping("/{enrollmentID}")
    public ResponseEntity<String> updateEnrollment(@PathVariable int enrollmentID, @Valid @RequestBody Enrollment enrollment) {
        logger.info("Updating enrollment with ID: {}", enrollmentID);
        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(enrollmentID, enrollment);
        if (updatedEnrollment != null) {
            return new ResponseEntity<>("Enrollment has been Updated", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Enrollment not found with id: " + enrollmentID);
        }
    }

    /**
     * Delete an enrollment by ID.
     */
    @DeleteMapping("/{enrollmentID}")
    public ResponseEntity<String> deleteEnrollment(@PathVariable int enrollmentID) {
        logger.info("Deleting enrollment with ID: {}", enrollmentID);
        notificationService.saveNotification("Enrollment deleted");
        enrollmentService.deleteEnrollment(enrollmentID);
        return  ResponseEntity.ok("Deleted Enrollment");
    }

    /**
     * Update progress for an enrollment.
     * Frontend
     */
    @PutMapping("/updateProgress/{enrollmentID}")
    public ResponseEntity<Enrollment> updateProgress(@PathVariable int enrollmentID) {
        logger.info("Updating progress for enrollment with ID: {}", enrollmentID);
        enrollmentService.updateProgress(enrollmentID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get progress for an enrollment by ID.
     */
    @GetMapping("/progress/{enrollmentID}")
    public ResponseEntity<Integer> getProgress(@PathVariable int enrollmentID) {
        logger.info("Fetching progress for enrollment with ID: {}", enrollmentID);
        Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentID);
        if (enrollment != null) {
            return new ResponseEntity<>(enrollment.getProgress(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Enrollment not found with id: " + enrollmentID);
        }
    }

    /**
     * Check if a course is completed for an enrollment by ID.
    // FRONTEND
     */
    @GetMapping("/isCompleted/{enrollmentID}")
    public ResponseEntity<Boolean> isCourseCompleted(@PathVariable int enrollmentID) {
        logger.info("Checking if enrollment with ID: {} is completed", enrollmentID);
        boolean isCompleted = enrollmentService.isCompleted(enrollmentID);
        return new ResponseEntity<>(isCompleted, HttpStatus.OK);
    }
@GetMapping("/course/{courseID}/students")
public ResponseEntity<List<User>> getStudentsByCourseId(@PathVariable int courseID) {
    logger.info("Fetching students for course with ID: {}", courseID);
    List<User> students = enrollmentService.getStudentsByCourseId(courseID);
    return new ResponseEntity<>(students, HttpStatus.OK);
}

@GetMapping("/student/{studentId}")
public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(@PathVariable int studentId) {
    List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
    return new ResponseEntity<>(enrollments, HttpStatus.OK);
}
}