import React, { useState, useEffect } from 'react';
import { Container, TextField, Button, Typography, Box } from '@mui/material';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const UpdateCourse = () => {
  const { courseID } = useParams(); // Get courseID from the URL
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    contentURL: '',
  });
  const [error, setError] = useState(''); // State for error messages
  const navigate = useNavigate();

  // Fetch the existing course details
  useEffect(() => {
    const fetchCourseDetails = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          throw new Error('Authentication token is missing. Please log in again.');
        }
        const response = await axios.get(`http://localhost:8081/api/courses/${courseID}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setFormData(response.data); // Populate the form with existing course details
      } catch (error) {
        console.error('Error fetching course details:', error);
        setError('Failed to fetch course details. Please try again later.');
      }
    };

    fetchCourseDetails();
  }, [courseID]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleUpdate = async () => {
    if (!formData.title || !formData.description || !formData.contentURL) {
        alert('Please fill out all fields.');
        return;
    }

    try {
        const token = localStorage.getItem('token');
        if (!token) {
            throw new Error('Authentication token is missing.');
        }

        // Only send required fields
        const updateData = {
            title: formData.title,
            description: formData.description,
            contentURL: formData.contentURL,
            instructorID: formData.instructorID // Keep the original instructorID
        };

        const response = await axios.put(
            `http://localhost:8081/api/courses/${courseID}`,
            updateData,
            {
                headers: { Authorization: `Bearer ${token}` }
            }
        );

        if (response.status === 200) {
            alert('Course updated successfully!');
            navigate('/instructor-dashboard');
        }
    } catch (error) {
        console.error('Error updating course:', error);
        if (error.response?.status === 403) {
            setError('You are not authorized to update this course.');
        } else {
            setError('Failed to update course. Please try again later.');
        }
    }
};

  return (
    <Container maxWidth="sm" sx={{ mt: 5 }}>
      <Typography variant="h4" gutterBottom>
        Update Course
      </Typography>
      {error && (
        <Typography variant="body1" color="error" gutterBottom>
          {error}
        </Typography>
      )}
      <Box sx={{ mt: 3 }}>
        <TextField
          label="Title"
          name="title"
          fullWidth
          margin="normal"
          value={formData.title}
          onChange={handleChange}
        />
        <TextField
          label="Description"
          name="description"
          fullWidth
          margin="normal"
          value={formData.description}
          onChange={handleChange}
        />
        <TextField
          label="Content URL"
          name="contentURL"
          fullWidth
          margin="normal"
          value={formData.contentURL}
          onChange={handleChange}
        />
        <Button
          variant="contained"
          color="primary"
          fullWidth
          sx={{ mt: 2 }}
          onClick={handleUpdate}
        >
          Update Course
        </Button>
      </Box>
    </Container>
  );
};

export default UpdateCourse;