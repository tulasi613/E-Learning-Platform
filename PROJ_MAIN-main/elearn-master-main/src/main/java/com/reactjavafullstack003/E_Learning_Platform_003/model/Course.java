package com.reactjavafullstack003.E_Learning_Platform_003.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseID;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotBlank(message = "Content URL is mandatory")
    private String contentURL;
    @NotNull(message = "Id Should be in number")
    private int instructorID;

    // Mapping
    @ManyToOne
    @JoinColumn(name = "instructorID", referencedColumnName = "instructorID", insertable = false, updatable = false)
    @JsonIgnoreProperties("courses")
    private Instructor instructor;

    @OneToMany(mappedBy = "course",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("course")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("course")
    private List<Assessment> assessments;

    @OneToMany(mappedBy = "course",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("course") 
    private List<Video> videos;

    @OneToMany(mappedBy = "course",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("course") 
    private List<Resource> resources;

    // Getters and Setters
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }
    public List<Video> getVideos() {
        return videos;
    }
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
    public List<Resource> getResources() {
        return resources;
    }
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Course(int courseID, String title, String description, String contentURL, int instructorID, Instructor instructor, List<Enrollment> enrollments, List<Assessment> assessments, List<Video> videos, List<Resource> resources) {
        super();
        this.courseID = courseID;
        this.title = title;
        this.description = description;
        this.contentURL = contentURL;
        this.instructorID = instructorID;
        this.instructor = instructor;
        this.enrollments = enrollments;
        this.assessments = assessments;
        this.videos = videos;
        this.resources = resources;
    }

    public Course() {
        super();
    }
}