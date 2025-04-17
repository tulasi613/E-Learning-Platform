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
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrollmentid;
    @NotNull(message = "Id Should be in number")
    private int courseID;
    
    private int progress;
    @NotNull(message = "Id Should be in number")
    private int studentid;
    @NotBlank(message = "Status is mandatory")
    private String status;
    
    // Mapping
    @ManyToOne
    @JsonIgnoreProperties("enrollments")
    @JoinColumn(name = "studentID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User student;

    @ManyToOne
	@JsonIgnoreProperties("enrollments")
    @JoinColumn(name = "courseID", referencedColumnName = "courseID", insertable = false, updatable = false)
    private Course course;
  
    // @Transient
    // private int totalVideos;
    // @Transient
    // private int completedVideos;
    // @Transient
    // private int totalAssessments;
    // @Transient
    // private int completedAssessments;
    // Getters and Setters
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getEnrollmentid() {
        return enrollmentid;
    }

    public void setEnrollmentid(int enrollmentid) {
        this.enrollmentid = enrollmentid;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Enrollment(int courseID, int enrollmentid, int progress, int studentid, User student, Course course,String status) {
        super();
        this.courseID = courseID;
        this.enrollmentid = enrollmentid;
        this.progress = progress;
        this.studentid = studentid;
        this.student = student;
        this.course = course;
        this.status = status;
    }

    public Enrollment() {
        super();
    }

    @Override
    public String toString() {
        return "Enrollment [courseID=" + courseID + ", enrollmentid=" + enrollmentid + ", progress=" + progress
                + ", studentid=" + studentid + "]";
    }

}