import React, { useEffect, useState } from 'react';
import { Typography, Grid, Card, CardContent, CardActions, Button, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const EnrolledStudents = ({ courseID }) => {
  const [students, setStudents] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchEnrolledStudents = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get(`http://localhost:8081/api/enrollments/course/${courseID}/students`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setStudents(response.data);
      } catch (error) {
        console.error('Error fetching enrolled students:', error);
      }
    };

    fetchEnrolledStudents();
  }, [courseID]);

  const handleStudentClick = (studentID) => {
    navigate(`/course/${courseID}/student/${studentID}`);
  };

  return (
    <Box sx={{ mt: 4 }}>
      <Typography variant="h5" gutterBottom>
        Enrolled Students
      </Typography>
      {students.length > 0 ? (
        <Grid container spacing={3}>
          {students.map((student) => (
            <Grid item xs={12} sm={6} md={4} key={student.userID}>
              <Card
                sx={{
                  cursor: 'pointer',
                  transition: 'transform 0.2s ease-in-out',
                  '&:hover': { transform: 'scale(1.05)' },
                }}
                onClick={() => handleStudentClick(student.userID)}
              >
                <CardContent>
                  <Typography variant="h6">{student.name}</Typography>
                  <Typography variant="body2" color="textSecondary">
                    Email: {student.email}
                  </Typography>
                </CardContent>
                <CardActions>
                  <Button
                    size="small"
                    color="primary"
                    onClick={(e) => {
                      e.stopPropagation(); // Prevent triggering the card click event
                      handleStudentClick(student.userID);
                    }}
                  >
                    View Profile
                  </Button>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>
      ) : (
        <Typography variant="body1">No students are enrolled in this course.</Typography>
      )}
    </Box>
  );
};

export default EnrolledStudents;