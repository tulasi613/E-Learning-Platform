package com.reactjavafullstack003.E_Learning_Platform_003.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Assessment;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Attempt;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Submission;
import com.reactjavafullstack003.E_Learning_Platform_003.service.AssessmentService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.NotificationService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.SubmissionService;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Validated
@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private static final Logger logger = LogManager.getLogger(AssessmentController.class);

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private NotificationService notificationService;

    /**
     * Get all assessments.
     */
    @GetMapping
    public List<Assessment> getAllAssessments() {
        logger.info("Fetching all assessments");
        return assessmentService.getAllAssessments();
    }

    /**
     * Get an assessment by ID.
     */
    @GetMapping("/{assessmentID}")
    public ResponseEntity<Assessment> getAssessmentById(@PathVariable int assessmentID) {
        logger.info("Fetching assessment with ID: {}", assessmentID);
        Optional<Assessment> assessmentOpt = assessmentService.getAssessmentById(assessmentID);
        if (assessmentOpt.isPresent()) {
            Assessment assessment = assessmentOpt.get();
            return new ResponseEntity<>(assessment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new assessment.
     */
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<String> createAssessment(@Valid @RequestBody Assessment assessment) {
        logger.info("Creating assessment");
        notificationService.saveNotification("Assessment created");
        assessmentService.saveAssessment(assessment);
        return new ResponseEntity<>("Assessment has been Created!", HttpStatus.CREATED);
    }

    /**
     * Create a new attempt for an assessment.
     */
    @PostMapping("/attempt")
    public ResponseEntity<Double> createAttempt(@RequestBody Attempt attempt) {
        logger.info("Creating attempt");
        notificationService.saveNotification("Attempt created");
        Attempt savedAttempt = assessmentService.saveAttempt(attempt);
        double grade = assessmentService.calculateGrade(savedAttempt);
        return new ResponseEntity<>(grade, HttpStatus.CREATED);
    }

    /**
     * Attempt an assessment and save the submission.
     */
    @PostMapping("/{assessmentId}/attempt")
    public ResponseEntity<Map<String,Object>> attemptAssessment(@PathVariable int assessmentId, @Valid @RequestBody Attempt attempt) {
        logger.info("Attempting assessment");
        notificationService.saveNotification("Attempting assessment");
        Optional<Assessment> assessmentOpt = assessmentService.getAssessmentById(assessmentId);
        if (!assessmentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Assessment assessment = assessmentOpt.get();
        double score = assessmentService.calculateScore(assessment, attempt.getAnswers());
        Submission submission = new Submission();
        submission.setScore(score);
        submission.setStudentId(attempt.getUserID());
        submission.setAssessmentId(assessmentId);
        submission.setSubmissionDate(LocalDateTime.now());

        submissionService.saveSubmission(submission);
        Map<String, Object> result = new HashMap<>();
    result.put("score", score);
    result.put("maxScore", assessment.getMaxScore());
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

    /**
     * Delete an assessment by ID.
     */
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @DeleteMapping("/{assessmentID}")
    public ResponseEntity<String> deleteAssessment(@PathVariable int assessmentID) {
        logger.info("Deleting assessment with ID: {}", assessmentID);
        notificationService.saveNotification("Assessment deleted");
        assessmentService.deleteAssessment(assessmentID);
        return ResponseEntity.ok("Assessment has been deleted!");
    }

    @GetMapping("/course/{courseID}")
    public List<Assessment> getAssessmentsByCourseId(@PathVariable int courseID) {
        return assessmentService.getAssessmentsByCourseId(courseID);
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
@PutMapping("/{assessmentId}")
public ResponseEntity<String> updateAssessment(
    @PathVariable int assessmentId,
    @Valid @RequestBody Assessment assessment
) {
    logger.info("Updating assessment with ID: {}", assessmentId);
    try {
        Assessment updatedAssessment = assessmentService.updateAssessment(assessmentId, assessment);
        if (updatedAssessment != null) {
            notificationService.saveNotification("Assessment updated successfully");
            return new ResponseEntity<>("Assessment updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update assessment", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}