package com.reactjavafullstack003.E_Learning_Platform_003.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attemptID;
    @NotNull(message = "Id Should be in number")
    private int assessmentID;
    @NotNull(message = "Id Should be in number")
    private int userID;

    @ElementCollection
    private Map<String, String> answers; // Question and user answer pairs

    // Mapping
    @ManyToOne
    @JsonIgnoreProperties("attempts")
    @JoinColumn(name = "assessmentID", referencedColumnName = "assessmentID", insertable = false, updatable = false)
    private Assessment assessment;

    @ManyToOne
    @JsonIgnoreProperties("attempts")
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User user;

    // Getters and Setters
    public int getAttemptID() {
        return attemptID;
    }

    public void setAttemptID(int attemptID) {
        this.attemptID = attemptID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Attempt() {
    }

    public Attempt(int attemptID, int assessmentID, int userID, Map<String, String> answers, Assessment assessment, User user) {
        this.attemptID = attemptID;
        this.assessmentID = assessmentID;
        this.userID = userID;
        this.answers = answers;
        this.assessment = assessment;
        this.user = user;
    }
}