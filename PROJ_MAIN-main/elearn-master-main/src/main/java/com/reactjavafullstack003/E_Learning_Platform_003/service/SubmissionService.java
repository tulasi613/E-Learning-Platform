package com.reactjavafullstack003.E_Learning_Platform_003.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Submission;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.SubmissionRepository;

@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    public Optional<Submission> getSubmissionById(int id) {
        return submissionRepository.findById(id);
    }

    public List<Submission> getSubmissionsByStudentId(int studentId) {
        return submissionRepository.findByStudentId(studentId);
    }

    public List<Submission> getSubmissionsByAssessmentId(int assessmentId) {
        return submissionRepository.findByAssessmentId(assessmentId);
    }

    public Submission saveSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

    public Optional<Submission> findBySubmissionId(int submissionId) {
        return submissionRepository.findById(submissionId);
    }
    public void deleteSubmission(int id) {
        submissionRepository.deleteById(id);
    }

    public List<Submission> getSubmissionsByCourseAndStudent(int courseID, int studentID) {
        return submissionRepository.findByCourseIdAndStudentId(courseID, studentID);
    }
}