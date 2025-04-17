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

import com.reactjavafullstack003.E_Learning_Platform_003.model.Enrollment;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.EnrollmentRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.VideoRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.service.EnrollmentService;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.AssessmentRepository;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private AssessmentRepository assessmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @BeforeEach
   void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEnrollments() {
        Enrollment enrollment1 = new Enrollment(1, 1, 50, 1, null, null, "In Progress");
        Enrollment enrollment2 = new Enrollment(2, 2, 75, 2, null, null, "Completed");
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment1, enrollment2));

        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        assertEquals(2, enrollments.size());
        assertEquals(1, enrollments.get(0).getCourseID());
        assertEquals(2, enrollments.get(1).getCourseID());
    }

    @Test
    void testGetEnrollmentById() {
        Enrollment enrollment = new Enrollment(1, 1, 50, 1, null, null, "In Progress");
        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(enrollment));

        Enrollment foundEnrollment = enrollmentService.getEnrollmentById(1);
        assertEquals(1, foundEnrollment.getCourseID());
    }

    @Test
    void testEnroll() {
        Enrollment enrollment = new Enrollment(1, 1, 50, 1, null, null, "In Progress");
        when(enrollmentRepository.save(enrollment)).thenReturn(enrollment);

        Enrollment savedEnrollment = enrollmentService.enroll(enrollment);
        assertEquals(1, savedEnrollment.getCourseID());
    }

    @Test
    void testUpdateEnrollment() {
        Enrollment existingEnrollment = new Enrollment(1, 1, 50, 1, null, null, "In Progress");
        Enrollment updatedEnrollment = new Enrollment(1, 1, 75, 1, null, null, "Completed");
        when(enrollmentRepository.findById(1)).thenReturn(Optional.of(existingEnrollment));
        when(enrollmentRepository.save(existingEnrollment)).thenReturn(updatedEnrollment);

        Enrollment result = enrollmentService.updateEnrollment(1, updatedEnrollment);
        assertEquals(75, result.getProgress());
        assertEquals("Completed", result.getStatus());
    }

}