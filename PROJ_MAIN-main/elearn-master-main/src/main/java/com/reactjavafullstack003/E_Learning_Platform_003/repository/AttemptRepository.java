package com.reactjavafullstack003.E_Learning_Platform_003.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Assessment;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Attempt;
@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Integer> {
    List<Assessment> findByAssessmentID(int assessmentID);
	
}