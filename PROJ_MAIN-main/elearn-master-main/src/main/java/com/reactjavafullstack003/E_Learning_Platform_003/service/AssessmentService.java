package com.reactjavafullstack003.E_Learning_Platform_003.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactjavafullstack003.E_Learning_Platform_003.Exceptions.AssessmentNotFoundException;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Assessment;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Attempt;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.AssessmentRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.AttemptRepository;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AttemptRepository attemptRepository;

    /**
     * Fetch all assessments from the repository.
     * Throws AssessmentNotFoundException if no assessments are found.
     */
    public List<Assessment> getAllAssessments() {
        if (assessmentRepository.findAll().isEmpty()) {
            throw new AssessmentNotFoundException("No assessments found");
        }
        return assessmentRepository.findAll();
    }

    /**
     * Fetch an assessment by ID from the repository.
     */
    public Optional<Assessment> getAssessmentById(int assessmentID) {
        return assessmentRepository.findById(assessmentID);
    }

    /**
     * Save a new assessment to the repository.
     */
    public Assessment saveAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    /**
     * Save a new attempt to the repository.
     */
    public Attempt saveAttempt(Attempt attempt) {
        return attemptRepository.save(attempt);
    }

    /**
     * Delete an assessment by ID from the repository.
     * Throws AssessmentNotFoundException if the assessment is not found.
     */
    public void deleteAssessment(int assessmentID) {
        if (assessmentRepository.findById(assessmentID).isEmpty()) {
            throw new AssessmentNotFoundException("Assessment not found with ID: " + assessmentID);
        }
        assessmentRepository.deleteById(assessmentID);
    }

    /**
     * Calculate the score for an assessment based on the provided answers.
     */
    public double calculateScore(Assessment assessment, Map<String, String> answers) {
        // Fetch the correct answers from the assessment
        Map<String, String> correctAnswers = assessment.getQuestions();
        int correctCount = 0;
    
        // Compare user answers with correct answers
        for (Map.Entry<String, String> entry : correctAnswers.entrySet()) {
            String question = entry.getKey();
            String correctAnswer = entry.getValue();
            String userAnswer = answers.get(question);
    
            if (correctAnswer.equals(userAnswer)) {
                correctCount++; // Increment correct count for each correct answer
            }
        }
    
        // Calculate the score as a percentage of the maximum score
        return (double) correctCount / correctAnswers.size() * assessment.getMaxScore();
    }

    /**
     * Calculate the grade for an attempt based on the correct answers.
     */
    public double calculateGrade(Attempt attempt) {
        Assessment assessment = getAssessmentById(attempt.getAssessmentID())
                .orElseThrow(() -> new RuntimeException("Assessment not found"));
        Map<String, String> correctAnswers = assessment.getQuestions();
        Map<String, String> userAnswers = attempt.getAnswers();

        int correctCount = 0;
        for (Map.Entry<String, String> entry : correctAnswers.entrySet()) {
            if (entry.getValue().equals(userAnswers.get(entry.getKey()))) {
                correctCount++;
            }
        }

        return (double) correctCount / correctAnswers.size() * assessment.getMaxScore();
    }
    public List<Assessment>getAssessmentsByCourseId(int courseID){
        return assessmentRepository.findByCourseID(courseID);
    }

    public Assessment updateAssessment(int assessmentId, Assessment assessment) {
        Assessment existingAssessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new AssessmentNotFoundException("Assessment not found with ID: " + assessmentId));
        
        existingAssessment.setAssessmentID(assessmentId);
        existingAssessment.setType(assessment.getType());
        existingAssessment.setMaxScore(assessment.getMaxScore());
        existingAssessment.setQuestions(assessment.getQuestions());
        existingAssessment.setOptions(assessment.getOptions());
        
        return assessmentRepository.save(existingAssessment);
    }

}