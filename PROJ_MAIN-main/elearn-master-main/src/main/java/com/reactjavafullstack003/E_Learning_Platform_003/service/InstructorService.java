package com.reactjavafullstack003.E_Learning_Platform_003.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactjavafullstack003.E_Learning_Platform_003.Exceptions.InstructorNotFoundException;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Instructor;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.InstructorRepository;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    /**
     * Fetch all instructors from the repository.
     * Throws InstructorNotFoundException if no instructors are found.
     */
    public List<Instructor> getAllInstructors() {
        List<Instructor> instructors = instructorRepository.findAll();
        if (instructors.isEmpty()) {
            throw new InstructorNotFoundException("No instructors found");
        }
        return instructors;
    }

    /**
     * Fetch an instructor by ID from the repository.
     * Throws InstructorNotFoundException if the instructor is not found.
     */
    public Instructor getInstructorById(int instructorID) {
        return instructorRepository.findById(instructorID)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor with ID " + instructorID + " not found"));
    }

    /**
     * Save a new instructor to the repository.
     * Throws InstructorNotFoundException if an instructor with the same email already exists.
     */
    public Instructor saveInstructor(Instructor instructor) {
        if (instructorRepository.findByEmail(instructor.getEmail()).isPresent()) {
            throw new InstructorNotFoundException("Instructor already exists with email: " + instructor.getEmail());
        }
        return instructorRepository.save(instructor);
    }

    /**
     * Update an existing instructor in the repository.
     * Throws InstructorNotFoundException if the instructor is not found.
     */
    public Instructor updateInstructor(int instructorID, Instructor instructor) {
        Instructor existingInstructor = instructorRepository.findById(instructorID)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor with ID " + instructorID + " not found"));
        existingInstructor.setInstructorID(instructorID);        
        existingInstructor.setName(instructor.getName());
        existingInstructor.setEmail(instructor.getEmail());
        existingInstructor.setPassword(instructor.getPassword());
        return instructorRepository.save(existingInstructor);
    }

    /**
     * Delete an instructor by ID from the repository.
     * Throws InstructorNotFoundException if the instructor is not found.
     */
    public void deleteInstructor(int instructorID) {
        if (!instructorRepository.existsById(instructorID)) {
            throw new InstructorNotFoundException("Instructor with ID " + instructorID + " not found");
        }
        instructorRepository.deleteById(instructorID);
    }

    /**
     * Fetch an instructor by email from the repository.
     * Throws InstructorNotFoundException if the instructor is not found.
     */
    public Instructor getInstructorByEmail(String email) {
        return instructorRepository.findByEmail(email)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor with email " + email + " not found"));
    }
}