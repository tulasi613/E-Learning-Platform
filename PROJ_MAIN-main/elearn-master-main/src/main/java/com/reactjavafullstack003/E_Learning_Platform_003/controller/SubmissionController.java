package com.reactjavafullstack003.E_Learning_Platform_003.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Submission;
import com.reactjavafullstack003.E_Learning_Platform_003.service.SubmissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private static final Logger logger = LogManager.getLogger(SubmissionController.class);
    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        logger.info("SubmissionController instantiated");
        this.submissionService = submissionService;
    }

    @GetMapping
    public List<Submission> getAllSubmissions() {
        logger.info("Fetching all submissions");
        return submissionService.getAllSubmissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable int id) {
        logger.info("Fetching submission with ID: {}", id);
        Optional<Submission> submission = submissionService.getSubmissionById(id);
        return submission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<Submission> getSubmissionsByStudentId(@PathVariable int studentId) {
        logger.info("Fetching submissions for student with ID: {}", studentId);
        return submissionService.getSubmissionsByStudentId(studentId);
    }

    @GetMapping("/assessment/{assessmentId}")
    public List<Submission> getSubmissionsByAssessmentId(@PathVariable int assessmentId) {
        logger.info("Fetching submissions for assessment with ID: {}", assessmentId);
        return submissionService.getSubmissionsByAssessmentId(assessmentId);
    }

    @GetMapping("/submission/{submissionId}")
public ResponseEntity<Submission> findBySubmissionId(@PathVariable int submissionId) {
    logger.info("Fetching submission with ID: {}", submissionId);
    Optional<Submission> submission = submissionService.findBySubmissionId(submissionId);
    return submission.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<?> saveSubmission(@Valid @RequestBody Submission submission, BindingResult result) {
        logger.info("Saving submission");
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Submission savedSubmission = submissionService.saveSubmission(submission);
        return ResponseEntity.ok(savedSubmission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubmission(@PathVariable int id) {
        logger.info("Deleting submission with ID: {}", id);
        submissionService.deleteSubmission(id);
        return ResponseEntity.ok("Submission is deleted!");
    }
    @GetMapping("/course/{courseID}/student/{studentID}")
public ResponseEntity<List<Submission>> getSubmissionsByCourseAndStudent(
        @PathVariable int courseID, @PathVariable int studentID) {
    logger.info("Fetching assessment results for student ID: {} in course ID: {}", studentID, courseID);
    List<Submission> submissions = submissionService.getSubmissionsByCourseAndStudent(courseID, studentID);
    return new ResponseEntity<>(submissions, HttpStatus.OK);
}
}
