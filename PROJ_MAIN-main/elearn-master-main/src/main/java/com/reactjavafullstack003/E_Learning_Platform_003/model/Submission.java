package com.reactjavafullstack003.E_Learning_Platform_003.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@JsonIgnoreProperties({"student", "assessment"}) // Add properties to be ignored during serialization
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submissionId;

    @Positive(message = "Score must be positive")
    private double score;

    @NotNull(message = "Submission date is mandatory")
    private LocalDateTime submissionDate;

    // Foreign keys
    @NotNull(message = "Id Should be in number")
    private int studentId;

    @NotNull(message = "Assessment ID is mandatory")
    private int assessmentId;

    // Relationships
    @ManyToOne
    @JsonIgnoreProperties("submissions")
    @JoinColumn(name = "studentId", referencedColumnName = "userID", insertable = false, updatable = false)
    private User student;

    @ManyToOne
    @JsonIgnoreProperties("submissions")
    @JoinColumn(name = "assessmentId", referencedColumnName = "assessmentID", insertable = false, updatable = false)
    private Assessment assessment;

    // Getters and Setters
    public int getSubmissionId() { 
        return submissionId;
    }

    public void setSubmissionId(int submissionId) { 
        this.submissionId = submissionId;
    }
    public double getScore() {
        return score; 
    }
    public void setScore(double score) {
        this.score = score;
    }
    public LocalDateTime getSubmissionDate() { 
        return submissionDate; 
    }
    public void setSubmissionDate(LocalDateTime submissionDate) { 
        this.submissionDate = submissionDate;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId; 
    }
    public int getAssessmentId() {
        return assessmentId; 
    }
    public void setAssessmentId(int assessmentId) { 
        this.assessmentId = assessmentId;
    }
    public User getStudent() {
        return student; 
    }
    public void setStudent(User student) {
        this.student = student; 
    }
    public Assessment getAssessment() { 
        return assessment; 
    }
    public void setAssessment(Assessment assessment) {
        this.assessment = assessment; 
    }

    public Submission() {
    }

    public Submission(int submissionId,  double score, LocalDateTime submissionDate,
            int studentId, int assessmentId, User student, Assessment assessment) {
        super();
        this.submissionId = submissionId;
        this.score = score;
        this.submissionDate = submissionDate;
        this.studentId = studentId;
        this.assessmentId = assessmentId;
        this.student = student;
        this.assessment = assessment;
    }

	@PrePersist
    protected void onCreate() {
        this.submissionDate = LocalDateTime.now();
    }
}