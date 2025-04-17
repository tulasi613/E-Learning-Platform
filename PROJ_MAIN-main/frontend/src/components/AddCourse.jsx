import React, { useState } from 'react';
import { Container, TextField, Button, Typography, Box } from '@mui/material';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const AddCourse = () => {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    contentURL: '',
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    try {
      const token = localStorage.getItem('token');
      const decodedToken = JSON.parse(atob(token.split('.')[1])); // Decode the JWT token
      const instructorID = decodedToken.instructorID; // Extract the instructorID from the token

      const payload = {
        ...formData,
        instructorID, // Include the instructorID in the payload
      };

      const response = await axios.post(
        'http://localhost:8081/api/courses/save',
        payload,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.status === 201) {
        alert('Course added successfully!');
        navigate('/instructor-dashboard'); // Redirect to instructor dashboard
      }
    } catch (error) {
      console.error('Error adding course:', error);
      alert('Failed to add course. Please try again.');
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 5 }}>
      <Typography variant="h4" gutterBottom>
        Add Course
      </Typography>
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
          onClick={handleSubmit}
        >
          Add Course
        </Button>
      </Box>
    </Container>
  );
};

export default AddCourse;