package com.reactjavafullstack003.E_Learning_Platform_003.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resourceId;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Resource URL is mandatory")
    private String resourceUrl;
    @NotBlank(message = "Resource Type is mandatory")
    private String resourceType; // e.g., "GITHUB", "PDF", "LINK"
    @NotNull(message = "Id Should be in number")
    private int courseId;
    
    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "courseID", insertable = false, updatable = false)
    @JsonIgnoreProperties("resources")
    private Course course;

    // Getters and setters
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}