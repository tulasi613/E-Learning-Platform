package com.reactjavafullstack003.E_Learning_Platform_003.Exceptions;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation exceptions.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle InstructorNotFoundException.
     */
    @ExceptionHandler({InstructorNotFoundException.class})
    public ResponseEntity<String> handleInstructorNotFoundException(InstructorNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle UserNotFoundException.
     */
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle CourseNotFoundException.
     */
    @ExceptionHandler({CourseNotFoundException.class})
    public ResponseEntity<String> handleCourseNotFoundException(CourseNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle ResourceNotFoundException.
     */
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle AssessmentNotFoundException.
     */
    @ExceptionHandler({AssessmentNotFoundException.class})
    public ResponseEntity<String> handleAssessmentNotFoundException(AssessmentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle SubmissionNotFoundException.
     */
    @ExceptionHandler({SubmissionNotFoundException.class})
    public ResponseEntity<String> handleSubmissionNotFoundException(SubmissionNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle EnrollmentNotFoundException.
     */
    @ExceptionHandler({EnrollmentNotFoundException.class})
    public ResponseEntity<String> handleEnrollmentNotFoundException(EnrollmentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle EnrollmentAlreadyExistsException.
     */
    @ExceptionHandler({EnrollmentAlreadyExistsException.class})
    public ResponseEntity<String> handleEnrollmentAlreadyExistsException(EnrollmentAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle ResourceAlreadyExistsException.
     */
    @ExceptionHandler({ResourceAlreadyExistsException.class})
    public ResponseEntity<String> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle AssessmentAlreadyExistsException.
     */
    @ExceptionHandler({AssessmentAlreadyExistsException.class})
    public ResponseEntity<String> handleAssessmentAlreadyExistsException(AssessmentAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle SubmissionAlreadyExistsException.
     */
    @ExceptionHandler({SubmissionAlreadyExistsException.class})
    public ResponseEntity<String> handleSubmissionAlreadyExistsException(SubmissionAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle CourseAlreadyExistsException.
     */
    @ExceptionHandler({CourseAlreadyExistsException.class})
    public ResponseEntity<String> handleCourseAlreadyExistsException(CourseAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle UserAlreadyExistsException.
     */
    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle InstructorAlreadyExistsException.
     */
    @ExceptionHandler({InstructorAlreadyExistsException.class})
    public ResponseEntity<String> handleInstructorAlreadyExistsException(InstructorAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle method argument not valid exceptions.
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), "Validation Failed", ex.getBindingResult().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}