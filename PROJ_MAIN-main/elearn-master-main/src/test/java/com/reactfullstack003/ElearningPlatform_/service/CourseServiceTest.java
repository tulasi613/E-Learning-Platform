
package com.reactfullstack003.ElearningPlatform_.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Course;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Instructor;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.CourseRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.service.CourseService ;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCourses() {
        Instructor instructor = new Instructor(1, "John Doe", "john@example.com", "password", null);
        Course course1 = new Course(1, "Course 1", "Description 1", "URL 1", 0, instructor, null, null, null, null);
        Course course2 = new Course(2, "Course 2", "Description 2", "URL 2", 0, instructor, null, null, null, null);
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        List<Course> courses = courseService.getAllCourses();
        assertEquals(2, courses.size());
        assertEquals("Course 1", courses.get(0).getTitle());
        assertEquals("Course 2", courses.get(1).getTitle());
    }

    @Test
    void testGetCourseById() {
        Instructor instructor = new Instructor(1, "John Doe", "john@example.com", "password", null);
        Course course = new Course(1, "Course 1", "Description 1", "URL 1", 0, instructor, null, null, null, null);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Course foundCourse = courseService.getCourseById(1);
        assertEquals("Course 1", foundCourse.getTitle());
    }

    @Test
    void testSaveCourse() {
        Instructor instructor = new Instructor(1, "John Doe", "john@example.com", "password", null);
        Course course = new Course(1, "Course 1", "Description 1", "URL 1", 0, instructor, null, null, null, null);
        when(courseRepository.save(course)).thenReturn(course);

        Course savedCourse = courseService.saveCourse(course);
        assertEquals("Course 1", savedCourse.getTitle());
    }

    @Test
    void testUpdateCourse() {
        Instructor instructor = new Instructor(1, "John Doe", "john@example.com", "password", null);
        Course existingCourse = new Course(1, "Course 1", "Description 1", "URL 1", 0, instructor, null, null, null, null);
        Course updatedCourse = new Course(1, "Updated Course", "Updated Description", "Updated URL", 0, instructor, null, null, null, null);
        when(courseRepository.findById(1)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(updatedCourse);

        Course result = courseService.updateCourse(1, updatedCourse);
        assertEquals("Updated Course", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
    }

}
