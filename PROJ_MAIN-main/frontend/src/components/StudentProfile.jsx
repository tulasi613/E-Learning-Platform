import React, { useEffect, useState } from 'react';
import { Container, Typography, List, ListItem, ListItemText, Box } from '@mui/material';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const StudentProfile = () => {
  const { courseID, studentID } = useParams(); // Get courseID and studentID from the URL
  const [student, setStudent] = useState(null);
  const [submissions, setSubmissions] = useState([]);

  useEffect(() => {
    const fetchStudentDetails = async () => {
      try {
        const token = localStorage.getItem('token');

        // Fetch student details
        const studentResponse = await axios.get(`http://localhost:8081/api/users/${studentID}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setStudent(studentResponse.data);

        // Fetch student's assessment submissions for the course
        const submissionsResponse = await axios.get(
          `http://localhost:8081/api/submissions/course/${courseID}/student/${studentID}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );
        setSubmissions(submissionsResponse.data);
      } catch (error) {
        console.error('Error fetching student details or submissions:', error);
      }
    };

    fetchStudentDetails();
  }, [courseID, studentID]);

  if (!student) {
    return <Typography>Loading...</Typography>;
  }

  return (
    <Container maxWidth="md" sx={{ mt: 5 }}>
      <Typography variant="h4" gutterBottom>
        {student.name}'s Profile
      </Typography>
      <Typography variant="body1" gutterBottom>
        Email: {student.email}
      </Typography>

      <Box sx={{ mt: 4 }}>
        <Typography variant="h5" gutterBottom>
          Assessment Results
        </Typography>
        {submissions.length > 0 ? (
          <List>
            {submissions.map((submission) => (
              <ListItem key={submission.submissionId}>
                <ListItemText
                  primary={`Assessment ID: ${submission.assessmentId}`}
                  secondary={`Score: ${submission.score}`}
                />
              </ListItem>
            ))}
          </List>
        ) : (
          <Typography variant="body1">No assessment results available.</Typography>
        )}
      </Box>
    </Container>
  );
};

export default StudentProfile;