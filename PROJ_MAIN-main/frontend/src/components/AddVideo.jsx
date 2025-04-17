import React, { useState } from 'react';
import { Container, TextField, Button, Typography, Box } from '@mui/material';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const AddVideo = () => {
  const { courseID } = useParams();
  const [videoData, setVideoData] = useState({
    title: '',
    videoUrl: '',
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setVideoData({ ...videoData, [e.target.name]: e.target.value });
  };

  const handleAddVideo = async () => {
    try {
      const token = localStorage.getItem('token');
      const payload = { ...videoData, courseId: courseID };
      const response = await axios.post('http://localhost:8081/api/videos/save', payload, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.status === 200) {
        alert('Video added successfully!');
        navigate(`/course/${courseID}`);
      }
    } catch (error) {
      console.error('Error adding video:', error);
      alert('Failed to add video. Please try again.');
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 5 }}>
      <Typography variant="h4" gutterBottom>
        Add Video
      </Typography>
      <Box sx={{ mt: 3 }}>
        <TextField
          label="Title"
          name="title"
          fullWidth
          margin="normal"
          value={videoData.title}
          onChange={handleChange}
        />
        <TextField
          label="YouTube URL"
          name="videoUrl"
          fullWidth
          margin="normal"
          value={videoData.videoUrl}
          onChange={handleChange}
        />
        <Button
          variant="contained"
          color="primary"
          fullWidth
          sx={{ mt: 2 }}
          onClick={handleAddVideo}
        >
          Add Video
        </Button>
      </Box>
    </Container>
  );
};

export default AddVideo;