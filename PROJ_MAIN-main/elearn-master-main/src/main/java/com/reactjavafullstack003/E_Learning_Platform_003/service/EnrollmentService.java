package com.reactjavafullstack003.E_Learning_Platform_003.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactjavafullstack003.E_Learning_Platform_003.Exceptions.EnrollmentNotFoundException;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Assessment;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Enrollment;
import com.reactjavafullstack003.E_Learning_Platform_003.model.User;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Video;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.AssessmentRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.EnrollmentRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.VideoRepository;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    /**
     * Fetch all enrollments from the repository.
     * Throws EnrollmentNotFoundException if no enrollments are found.
     */
    public List<Enrollment> getAllEnrollments() {
        if (enrollmentRepository.findAll().isEmpty()) {
            throw new EnrollmentNotFoundException("No enrollments found");
        }
        return enrollmentRepository.findAll();
    }

    /**
     * Fetch an enrollment by ID from the repository.
     * Throws EnrollmentNotFoundException if the enrollment is not found.
     */
    public Enrollment getEnrollmentById(int enrollmentID) {
        return enrollmentRepository.findById(enrollmentID)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found with ID: " + enrollmentID));
    }

    /**
     * Save a new enrollment to the repository.
     * Throws EnrollmentNotFoundException if an enrollment with the same courseID and studentID already exists.
     */
    public Enrollment enroll(Enrollment enrollment) {
        if (enrollmentRepository.findByCourseIDAndStudentid(enrollment.getCourseID(), enrollment.getStudentid()).isPresent()) {
            throw new EnrollmentNotFoundException("Enrollment already exists for courseID: " + enrollment.getCourseID() + " and userID: " + enrollment.getStudentid());
        }
        
        return enrollmentRepository.save(enrollment);
    }

    /**
     * Update an existing enrollment in the repository.
     * Returns null if the enrollment is not found.
     */
    public Enrollment updateEnrollment(int enrollmentID, Enrollment enrollment) {
        Enrollment existingEnrollment = enrollmentRepository.findById(enrollmentID).orElse(null);
        if (existingEnrollment != null) {
            existingEnrollment.setStatus(enrollment.getStatus());
            existingEnrollment.setProgress(enrollment.getProgress());
            // Update other fields as necessary
            return enrollmentRepository.save(existingEnrollment);
        } else {
            return null;
        }
    }

    /**
     * Delete an enrollment by ID from the repository.
     * Throws EnrollmentNotFoundException if the enrollment is not found.
     */
    public void deleteEnrollment(int enrollmentID) {
        if (enrollmentRepository.findById(enrollmentID).isEmpty()) {
            throw new EnrollmentNotFoundException("Enrollment not found with ID: " + enrollmentID);
        }
        enrollmentRepository.deleteById(enrollmentID);
    }

    /**
     * Update the progress of an enrollment based on completed videos and assessments.
     * FRONTEND
     */
    public void updateProgress(int enrollmentID) {
        Enrollment enrollment = getEnrollmentById(enrollmentID);
        if (enrollment != null) {
            List<Video> videos = videoRepository.findByCourseId(enrollment.getCourseID());
            List<Assessment> assessments = assessmentRepository.findByCourseID(enrollment.getCourseID());

            int completedVideos = (int) videos.stream().filter(Video::isCompleted).count();
            int completedAssessments = (int) assessments.stream().filter(Assessment::isCompleted).count();

            int totalVideos = videos.size();
            int totalAssessments = assessments.size();
            int totalTasks = totalVideos + totalAssessments;
            int completedTasks = completedVideos + completedAssessments;

            int progress = (int) ((double) completedTasks / totalTasks * 100);

            enrollment.setProgress(progress);

            enrollmentRepository.save(enrollment);
        }
    }

    /**
     * Check if all videos and assessments are completed for an enrollment.
     * FRONTEND
     */
    public boolean isCompleted(int enrollmentID) {
        Enrollment enrollment = getEnrollmentById(enrollmentID);
        if (enrollment != null) {
            List<Video> videos = videoRepository.findByCourseId(enrollment.getCourseID());
            List<Assessment> assessments = assessmentRepository.findByCourseID(enrollment.getCourseID());

            boolean allVideosCompleted = videos.stream().allMatch(Video::isCompleted);
            boolean allAssessmentsCompleted = assessments.stream().allMatch(Assessment::isCompleted);

            return allVideosCompleted && allAssessmentsCompleted;
        }
        return false;
    }
    public List<User> getStudentsByCourseId(int courseID) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseID(courseID);
        return enrollments.stream()
                          .map(Enrollment::getStudent)
                          .collect(Collectors.toList());
    }

    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        return enrollmentRepository.findByStudentid(studentId);
    }

    
}
