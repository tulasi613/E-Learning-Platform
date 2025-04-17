package com.reactjavafullstack003.E_Learning_Platform_003.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Assessment;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Integer> {

    /**
     * Find assessments by assessment ID.
     */
    List<Assessment> findByAssessmentID(int assessmentID);

    /**
     * Find assessments by course ID.
     */
    List<Assessment> findByCourseID(int courseID);
}