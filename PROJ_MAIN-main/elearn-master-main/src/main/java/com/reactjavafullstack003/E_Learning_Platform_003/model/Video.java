package com.reactjavafullstack003.E_Learning_Platform_003.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int videoId;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Video URL is mandatory")
    private String videoUrl;
    @NotNull(message = "Id Should be in number")
    private int courseId;
    @NotNull(message = "It should not be null")
    private boolean completed;
    
    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "courseID", insertable = false, updatable = false)
    @JsonIgnoreProperties("videos")
    private Course course;

    // Getters and setters
    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}