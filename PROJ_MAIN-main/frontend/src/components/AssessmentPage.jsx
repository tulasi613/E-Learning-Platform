import React, { useEffect, useState } from 'react';
import { Container, Typography, Box, Button, Grid } from '@mui/material';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const AssessmentPage = () => {
  const { courseID, assessmentID } = useParams(); // Get courseID and assessmentID from the URL
  const [assessment, setAssessment] = useState(null);
  const [answers, setAnswers] = useState({});
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchAssessmentDetails();
  }, [assessmentID]);

  const fetchAssessmentDetails = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`http://localhost:8081/api/assessments/${assessmentID}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAssessment(response.data);

      // Initialize answers state with empty strings for each question
      const initialAnswers = {};
      Object.keys(response.data.questions).forEach((question) => {
        initialAnswers[question] = '';
      });
      setAnswers(initialAnswers);
    } catch (error) {
      console.error('Error fetching assessment details:', error);
      setError('Failed to fetch assessment details. Please try again later.');
    }
  };

  const handleAnswerChange = (question, value) => {
    setAnswers({ ...answers, [question]: value });
  };

  const handleSubmit = async () => {
    try {
      const token = localStorage.getItem('token');
      const payload = {
        assessmentID: parseInt(assessmentID),
        userID: JSON.parse(atob(token.split('.')[1])).userID, // Extract userID from token
        answers,
      };

      const response = await axios.post(
        `http://localhost:8081/api/assessments/${assessmentID}/attempt`,
        payload,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      setResult(response.data); // Display the result after submission
    } catch (error) {
      console.error('Error submitting assessment:', error);
      setError('Failed to submit assessment. Please try again later.');
    }
  };

  if (!assessment) {
    return <Typography>Loading...</Typography>;
  }

  return (
    <Container maxWidth="md" sx={{ mt: 5 }}>
      {error && (
        <Typography color="error" gutterBottom>
          {error}
        </Typography>
      )}

      <Typography variant="h4" gutterBottom>
        {assessment.type} Assessment
      </Typography>
      <Typography variant="body1" gutterBottom>
        Max Score: {assessment.maxScore}
      </Typography>

      {result ? (
        <Box sx={{ mt: 4 }}>
          <Typography variant="h5" gutterBottom>
            Assessment Result
          </Typography>
          <Typography variant="body1">
            Your Score: {result.score} / {assessment.maxScore}
          </Typography>
        </Box>
      ) : (
        <Box sx={{ mt: 4 }}>
          <Typography variant="h5" gutterBottom>
            Questions
          </Typography>
          <form>
            {Object.entries(assessment.questions).map(([question, correctAnswer], index) => (
              <Box key={index} sx={{ mb: 3 }}>
                <Typography variant="body1" gutterBottom>
                  Q{index + 1}: {question}
                </Typography>
                <Grid container spacing={2}>
                  {assessment.options[question].map((option, optIndex) => (
                    <Grid item xs={6} key={optIndex}>
                      <Button
                        variant={answers[question] === option ? 'contained' : 'outlined'}
                        color="primary"
                        fullWidth
                        onClick={() => handleAnswerChange(question, option)}
                      >
                        {option}
                      </Button>
                    </Grid>
                  ))}
                </Grid>
              </Box>
            ))}
            <Button
              variant="contained"
              color="primary"
              fullWidth
              sx={{ mt: 4 }}
              onClick={handleSubmit}
            >
              Submit Assessment
            </Button>
          </form>
        </Box>
      )}
    </Container>
  );
};

export default AssessmentPage;