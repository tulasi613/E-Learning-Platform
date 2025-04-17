package com.reactfullstack003.ElearningPlatform_.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reactjavafullstack003.E_Learning_Platform_003.model.Assessment;
import com.reactjavafullstack003.E_Learning_Platform_003.model.Attempt;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.AssessmentRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.AttemptRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.service.AssessmentService;

class AssessmentServiceTest {

    @Mock
    private AssessmentRepository assessmentRepository;

    @Mock
    private AttemptRepository attemptRepository;

    @InjectMocks
    private AssessmentService assessmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAssessments() {
        Assessment assessment1 = new Assessment(1, 1, "Quiz", 100, null, null, new HashMap<>(), new HashMap<>());
        Assessment assessment2 = new Assessment(2, 1, "Quiz", 100, null, null, new HashMap<>(), new HashMap<>());
        when(assessmentRepository.findAll()).thenReturn(Arrays.asList(assessment1, assessment2));

        List<Assessment> assessments = assessmentService.getAllAssessments();
        assertEquals(2, assessments.size());
        assertEquals("Quiz", assessments.get(0).getType());
        assertEquals("Quiz", assessments.get(1).getType());
    }

    @Test
    void testGetAssessmentById() {
        Assessment assessment = new Assessment(1, 1, "Quiz", 100, null, null, new HashMap<>(), new HashMap<>());
        when(assessmentRepository.findById(1)).thenReturn(Optional.of(assessment));

        Optional<Assessment> foundAssessment = assessmentService.getAssessmentById(1);
        assertTrue(foundAssessment.isPresent());
        assertEquals("Quiz", foundAssessment.get().getType());
    }

    @Test
    void testSaveAssessment() {
        Assessment assessment = new Assessment(1, 1, "Quiz", 100, null, null, new HashMap<>(), new HashMap<>());
        when(assessmentRepository.save(assessment)).thenReturn(assessment);

        Assessment savedAssessment = assessmentService.saveAssessment(assessment);
        assertEquals("Quiz", savedAssessment.getType());
    }

    @Test
    void testCalculateGrade() {
        Map<String, String> questions = new HashMap<>();
        questions.put("Q1", "A1");
        questions.put("Q2", "A2");

        Map<String, String> answers = new HashMap<>();
        answers.put("Q1", "A1");
        answers.put("Q2", "A2");

        Assessment assessment = new Assessment(1, 1, "Quiz", 100, null, null, questions, new HashMap<>());
        Attempt attempt = new Attempt(1, 1, 0, answers, assessment, null);

        when(assessmentRepository.findById(1)).thenReturn(Optional.of(assessment));

        double grade = assessmentService.calculateGrade(attempt);
        assertEquals(100.0, grade);
    }
}