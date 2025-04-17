import React, { useState } from 'react';
import { Container, TextField, Button, Typography, Box } from '@mui/material';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const AddResource = () => {
  const { courseID } = useParams();
  const [resourceData, setResourceData] = useState({
    title: '',
    resourceUrl: '',
    resourceType: '',
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setResourceData({ ...resourceData, [e.target.name]: e.target.value });
  };

  const handleAddResource = async () => {
    try {
      const token = localStorage.getItem('token');
      const payload = { ...resourceData, courseId: courseID };
      const response = await axios.post('http://localhost:8081/api/resources/save', payload, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.status === 200) {
        alert('Resource added successfully!');
        navigate(`/course/${courseID}`);
      }
    } catch (error) {
      console.error('Error adding resource:', error);
      alert('Failed to add resource. Please try again.');
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 5 }}>
      <Typography variant="h4" gutterBottom>
        Add Resource
      </Typography>
      <Box sx={{ mt: 3 }}>
        <TextField
          label="Title"
          name="title"
          fullWidth
          margin="normal"
          value={resourceData.title}
          onChange={handleChange}
        />
        <TextField
          label="Resource URL"
          name="resourceUrl"
          fullWidth
          margin="normal"
          value={resourceData.resourceUrl}
          onChange={handleChange}
        />
        <TextField
          label="Resource Type"
          name="resourceType"
          fullWidth
          margin="normal"
          value={resourceData.resourceType}
          onChange={handleChange}
        />
        <Button
          variant="contained"
          color="primary"
          fullWidth
          sx={{ mt: 2 }}
          onClick={handleAddResource}
        >
          Add Resource
        </Button>
      </Box>
    </Container>
  );
};

export default AddResource;