package com.reactjavafullstack003.E_Learning_Platform_003.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import com.reactjavafullstack003.E_Learning_Platform_003.Exceptions.CourseNotFoundException;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Course;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Instructor;
import com.reactjavafullstack003.E_Learning_Platform_003.service.CourseService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.InstructorService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.NotificationService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private static final Logger logger = LogManager.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private InstructorService instructorService;

    /**
     * Get all courses.
     */
    @GetMapping
    public List<Course> getAllCourses() {
        logger.info("Fetching all courses");
        return courseService.getAllCourses();
    }

    /**
     * Get a course by ID.
     */
    @GetMapping("/{courseID}")
    public ResponseEntity<Course> getCourseById(@PathVariable int courseID) {
        logger.info("Fetching course with ID: {}", courseID);
        Course course = courseService.getCourseById(courseID);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            throw new CourseNotFoundException("Course not found with id: " + courseID);
        }
    }

    /**
     * Update an existing course.
     */

     @PreAuthorize("hasAuthority('INSTRUCTOR')")
     @PutMapping("/{courseID}")
     public ResponseEntity<String> updateCourse(@PathVariable int courseID, @Valid @RequestBody Course course) {
         logger.info("Updating course with ID: {}", courseID);
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String authenticatedEmail = authentication.getName();
         Instructor authenticatedInstructor = instructorService.getInstructorByEmail(authenticatedEmail);
     
         if (authenticatedInstructor == null) {
             return new ResponseEntity<>("Authenticated instructor not found", HttpStatus.UNAUTHORIZED);
         }
     
         // Get existing course to verify ownership
         Course existingCourse = courseService.getCourseById(courseID);
         if (existingCourse == null) {
             throw new CourseNotFoundException("Course not found with id: " + courseID);
         }
     
         // Verify course ownership
         if (existingCourse.getInstructorID() != authenticatedInstructor.getInstructorID()) {
             return new ResponseEntity<>("You are not authorized to update this course", HttpStatus.FORBIDDEN);
         }
     
         // Set the correct instructorID and courseID
         course.setInstructorID(authenticatedInstructor.getInstructorID());
         course.setCourseID(courseID);
     
         Course updatedCourse = courseService.updateCourse(courseID, course);
         notificationService.saveNotification("Course updated");
         return new ResponseEntity<>("Course has been Updated Successfully!", HttpStatus.OK);
     }

    /**
     * Save a new course.
     */
    
    @PostMapping("/save")
    public ResponseEntity<String> saveCourse(@Valid @RequestBody Course course) {
        logger.info("Saving new course");
        notificationService.saveNotification("Course created");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedEmail = authentication.getName(); // Assuming email is the username
        Instructor authenticatedInstructor = instructorService.getInstructorByEmail(authenticatedEmail);

        if (authenticatedInstructor == null) {
            return new ResponseEntity<>("Authenticated instructor not found", HttpStatus.UNAUTHORIZED);
        }

        // Check if the instructorID in the course matches the authenticated instructor's ID
        if (course.getInstructorID() != authenticatedInstructor.getInstructorID()) {
            return new ResponseEntity<>("You are not authorized to add courses for another instructor", HttpStatus.FORBIDDEN);
        }

        courseService.saveCourse(course);
        return new ResponseEntity<>("Course has been Added Successfully", HttpStatus.CREATED);
    }

    /**
     * Delete a course by ID.
     */
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @DeleteMapping("/{courseID}")
    public ResponseEntity<String> deleteCourse(@PathVariable int courseID) {
        logger.info("Deleting course with ID: {}", courseID);
        notificationService.saveNotification("Course deleted");
        courseService.deleteCourse(courseID);
        return new ResponseEntity<>("Course has been Deleted!", HttpStatus.OK);
    }

@GetMapping("/instructor")
@PreAuthorize("hasAuthority('INSTRUCTOR')")
public List<Course> getCoursesForInstructor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String authenticatedEmail = authentication.getName(); // Assuming email is the username
    Instructor authenticatedInstructor = instructorService.getInstructorByEmail(authenticatedEmail);

    if (authenticatedInstructor == null) {
        throw new RuntimeException("Authenticated instructor not found");
    }

    return courseService.getCoursesByInstructorId(authenticatedInstructor.getInstructorID());
}

@GetMapping("/public")
public List<Course> getPublicCourses() {
    return courseService.getAllCourses(); // Return all courses without authentication
}

}