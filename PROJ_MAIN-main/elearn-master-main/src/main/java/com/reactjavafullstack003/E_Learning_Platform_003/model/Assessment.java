package com.reactjavafullstack003.E_Learning_Platform_003.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assessmentID;
    @NotNull(message = "Id Should be in number")
    private int courseID;
    @NotNull(message = "Type is mandatory")
    private String type;
    @Min(value = 0, message = "Max score must be positive")
    private int maxScore;
  
    private boolean completed;

    @ElementCollection
    private Map<String, String> questions; // Question and correct answer pairs

    @ElementCollection
    private Map<String, List<String>> options; // Question and list of possible answers

    // Mapping
    @ManyToOne
    @JsonIgnoreProperties("assessments")
    @JoinColumn(name = "courseID", referencedColumnName = "courseID", insertable = false, updatable = false)
    private Course course;

    @OneToMany(mappedBy = "assessment",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("assessment")
    private List<Submission> submissions;

    // Getters and Setters
    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public Map<String, String> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, String> questions) {
        this.questions = questions;
    }

    public Map<String, List<String>> getOptions() {
        return options;
    }

    public void setOptions(Map<String, List<String>> options) {
        this.options = options;
    }

    public Assessment(int assessmentID, int courseID, String type, int maxScore, Course course,
                      List<Submission> submissions, Map<String, String> questions, Map<String, List<String>> options) {
        super();
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.type = type;
        this.maxScore = maxScore;
        this.course = course;
        this.submissions = submissions;
        this.questions = questions;
        this.options = options;
    }

    public Assessment() {
        super();
    }
}