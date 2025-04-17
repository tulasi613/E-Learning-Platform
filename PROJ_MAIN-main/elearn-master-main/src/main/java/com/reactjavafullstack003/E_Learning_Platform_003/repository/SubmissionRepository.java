package com.reactjavafullstack003.E_Learning_Platform_003.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {


	List<Submission> findByStudentId(int studentId);
	List<Submission> findByAssessmentId(int assessmentId);
	@Query("SELECT s FROM Submission s WHERE s.assessment.course.courseID = :courseID AND s.studentId = :studentID")
    List<Submission> findByCourseIdAndStudentId(int courseID, int studentID);

}
