import React, { useEffect, useState } from 'react';
import { Container, Typography, Box, List, ListItem, ListItemText } from '@mui/material';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const AssessmentDetails = () => {
  const { assessmentID } = useParams(); // Get assessmentID from the URL
  const [assessment, setAssessment] = useState(null);

  useEffect(() => {
    const fetchAssessmentDetails = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get(`http://localhost:8081/api/assessments/${assessmentID}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setAssessment(response.data);
      } catch (error) {
        console.error('Error fetching assessment details:', error);
      }
    };

    fetchAssessmentDetails();
  }, [assessmentID]);

  if (!assessment) {
    return <Typography>Loading...</Typography>;
  }

  return (
    <Container maxWidth="md" sx={{ mt: 5 }}>
      <Typography variant="h4" gutterBottom>
        Assessment Details
      </Typography>
      <Typography variant="h6" gutterBottom>
        Type: {assessment.type}
      </Typography>
      <Typography variant="body1" gutterBottom>
        Max Score: {assessment.maxScore}
      </Typography>

      <Box sx={{ mt: 3 }}>
        <Typography variant="h5" gutterBottom>
          Questions
        </Typography>
        <List>
          {Object.entries(assessment.questions).map(([question, correctAnswer], index) => (
            <ListItem key={index} sx={{ mb: 2 }}>
              <ListItemText
                primary={`Q${index + 1}: ${question}`}
                secondary={`Correct Answer: ${correctAnswer}`}
              />
            </ListItem>
          ))}
        </List>
      </Box>
    </Container>
  );
};

export default AssessmentDetails;