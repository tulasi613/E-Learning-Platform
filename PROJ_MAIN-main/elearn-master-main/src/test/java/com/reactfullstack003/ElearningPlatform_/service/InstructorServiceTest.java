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

import com.reactjavafullstack003.E_Learning_Platform_003.model.Instructor;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.InstructorRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.service.InstructorService;

class InstructorServiceTest {

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private InstructorService instructorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInstructors() {
        Instructor instructor1 = new Instructor(1, "John Doe", "john@example.com", "password", null);
        Instructor instructor2 = new Instructor(2, "Jane Doe", "jane@example.com", "password", null);
        when(instructorRepository.findAll()).thenReturn(Arrays.asList(instructor1, instructor2));

        List<Instructor> instructors = instructorService.getAllInstructors();
        assertEquals(2, instructors.size());
        assertEquals("John Doe", instructors.get(0).getName());
        assertEquals("Jane Doe", instructors.get(1).getName());
    }

    @Test
    void testGetInstructorById() {
        Instructor instructor = new Instructor(1, "John Doe", "john@example.com", "password", null);
        when(instructorRepository.findById(1)).thenReturn(Optional.of(instructor));

        Instructor foundInstructor = instructorService.getInstructorById(1);
        assertEquals("John Doe", foundInstructor.getName());
    }

    @Test
    void testSaveInstructor() {
        Instructor instructor = new Instructor(1, "John Doe", "john@example.com", "password", null);
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorService.saveInstructor(instructor);
        assertEquals("John Doe", savedInstructor.getName());
    }

    @Test
    void testUpdateInstructor() {
        Instructor existingInstructor = new Instructor(1, "John Doe", "john@example.com", "password", null);
        Instructor updatedInstructor = new Instructor(1, "Updated John", "updatedjohn@example.com", "newpassword", null);
        when(instructorRepository.findById(1)).thenReturn(Optional.of(existingInstructor));
        when(instructorRepository.save(existingInstructor)).thenReturn(updatedInstructor);

        Instructor result = instructorService.updateInstructor(1, updatedInstructor);
        assertEquals("Updated John", result.getName());
        assertEquals("updatedjohn@example.com", result.getEmail());
    }

    @Test
    void testDeleteInstructor() {
        when(instructorRepository.existsById(1)).thenReturn(true);
        doNothing().when(instructorRepository).deleteById(1);

        instructorService.deleteInstructor(1);
        verify(instructorRepository, times(1)).deleteById(1);
    }
}