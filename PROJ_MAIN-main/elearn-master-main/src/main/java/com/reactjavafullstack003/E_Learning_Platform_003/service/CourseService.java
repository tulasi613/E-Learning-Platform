package com.reactjavafullstack003.E_Learning_Platform_003.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactjavafullstack003.E_Learning_Platform_003.Exceptions.CourseNotFoundException;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Course;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Fetch all courses from the repository.
     * Throws CourseNotFoundException if no courses are found.
     */
    public List<Course> getAllCourses() {
        if (courseRepository.findAll().isEmpty()) {
            throw new CourseNotFoundException("No courses found");
        }
        return courseRepository.findAll();
    }

    /**
     * Fetch a course by ID from the repository.
     * Throws CourseNotFoundException if the course is not found.
     */
    public Course getCourseById(int courseID) {
        return courseRepository.findById(courseID)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseID));
    }

    /**
     * Save a new course to the repository.
     * Throws CourseNotFoundException if a course with the same title already exists.
     */
    public Course saveCourse(Course course) {
        if (courseRepository.findByTitle(course.getTitle()).isPresent()) {
            throw new CourseNotFoundException("Course already exists with title: " + course.getTitle());
        }
        return courseRepository.save(course);
    }

    /**
     * Delete a course by ID from the repository.
     * Throws CourseNotFoundException if the course is not found.
     */
    public void deleteCourse(int courseID) {
        if (courseRepository.findById(courseID).isEmpty()) {
            throw new CourseNotFoundException("Course not found with ID: " + courseID);
        }
        courseRepository.deleteById(courseID);
    }

    /**
     * Update an existing course in the repository.
     * Returns null if the course is not found.
     */
    public Course updateCourse(int courseID, Course course) {
        Course existingCourse = courseRepository.findById(courseID)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with ID: " + courseID));
        
        // Update only allowed fields
        existingCourse.setTitle(course.getTitle());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setContentURL(course.getContentURL());
        
        // Maintain the original instructorID
        existingCourse.setInstructorID(existingCourse.getInstructorID());
        
        return courseRepository.save(existingCourse);
    }

    /**
     * Create a new course (method not implemented).
     */
    public Course createCourse(Course course) {
        // Method not implemented
        return null;
    }

    public List<Course> getCoursesByInstructorId(int instructorID) {
        return courseRepository.findByInstructorID(instructorID);
    }
}