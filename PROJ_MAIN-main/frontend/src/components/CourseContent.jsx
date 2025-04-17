import React, { useEffect, useState } from 'react';
import { Container, Typography, Box, List, ListItem, ListItemText, Button, Divider } from '@mui/material';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const CourseContent = () => {
  const { courseID } = useParams(); // Get courseID from the URL
  const [course, setCourse] = useState({});
  const [videos, setVideos] = useState([]);
  const [resources, setResources] = useState([]);
  const [assessments, setAssessments] = useState([]);
  const [selectedVideo, setSelectedVideo] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchCourseContent();
  }, [courseID]);

  const fetchCourseContent = async () => {
    try {
      const token = localStorage.getItem('token');

      // Fetch course details
      const courseResponse = await axios.get(`http://localhost:8081/api/courses/${courseID}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCourse(courseResponse.data);

      // Fetch videos
      const videosResponse = await axios.get(`http://localhost:8081/api/videos/course/${courseID}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setVideos(videosResponse.data);
      if (videosResponse.data.length > 0) {
        setSelectedVideo(videosResponse.data[0]); // Select the first video by default
      }

      // Fetch resources
      const resourcesResponse = await axios.get(`http://localhost:8081/api/resources/course/${courseID}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setResources(resourcesResponse.data);

      // Fetch assessments
      const assessmentsResponse = await axios.get(`http://localhost:8081/api/assessments/course/${courseID}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAssessments(assessmentsResponse.data);
    } catch (error) {
      console.error('Error fetching course content:', error);
      setError('Failed to fetch course content. Please try again later.');
    }
  };

  const handleStartAssessment = (assessmentID) => {
    // Navigate to the assessment page
    window.location.href = `/course/${courseID}/assessment/${assessmentID}`;
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 5 }}>
      {error && (
        <Typography color="error" gutterBottom>
          {error}
        </Typography>
      )}

      <Typography variant="h4" gutterBottom>
        {course.title}
      </Typography>
      <Typography variant="body1" gutterBottom>
        {course.description}
      </Typography>

      <Box sx={{ display: 'flex', mt: 4 }}>
        {/* Sidebar */}
        <Box sx={{ width: '25%', mr: 3 }}>
          {/* Videos Section */}
          <Typography variant="h6" gutterBottom>
            Videos
          </Typography>
          <List>
            {videos.map((video) => (
              <ListItem
                button
                key={video.videoId}
                selected={selectedVideo?.videoId === video.videoId}
                onClick={() => setSelectedVideo(video)}
                sx={{
                  transition: 'background-color 0.3s ease',
                  backgroundColor: selectedVideo?.videoId === video.videoId ? '#e3f2fd' : 'transparent',
                  '&:hover': { backgroundColor: '#f1f1f1' },
                  borderRadius: '4px',
                  mb: 1,
                }}
              >
                <ListItemText primary={video.title} />
              </ListItem>
            ))}
          </List>

          <Divider sx={{ my: 2 }} />

          {/* Resources Section */}
          <Typography variant="h6" gutterBottom>
            Resources
          </Typography>
          <List>
            {resources.map((resource) => (
              <ListItem
                key={resource.resourceId}
                sx={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  alignItems: 'center',
                  mb: 1,
                  borderRadius: '4px',
                  backgroundColor: '#f9f9f9',
                  p: 1,
                }}
              >
                <ListItemText
                  primary={resource.title}
                  secondary={`Type: ${resource.resourceType}`}
                />
                <Button
                  variant="outlined"
                  color="primary"
                  onClick={() => window.open(resource.resourceUrl, '_blank')}
                >
                  View
                </Button>
              </ListItem>
            ))}
          </List>

          <Divider sx={{ my: 2 }} />

          {/* Assessments Section */}
          <Typography variant="h6" gutterBottom>
            Assessments
          </Typography>
          <List>
            {assessments.map((assessment) => (
              <ListItem
                key={assessment.assessmentID}
                sx={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  alignItems: 'center',
                  mb: 1,
                  borderRadius: '4px',
                  backgroundColor: '#f9f9f9',
                  p: 1,
                }}
              >
                <ListItemText
                  primary={assessment.type}
                  secondary={`Max Score: ${assessment.maxScore}`}
                />
                <Button
                  variant="contained"
                  color="primary"
                  onClick={() => handleStartAssessment(assessment.assessmentID)}
                >
                  Start
                </Button>
              </ListItem>
            ))}
          </List>
        </Box>

        {/* Main Content */}
        <Box sx={{ flexGrow: 1 }}>
          {selectedVideo ? (
            <Box>
              <Typography variant="h5" gutterBottom>
                {selectedVideo.title}
              </Typography>
              <iframe
                width="100%"
                height="400"
                src={selectedVideo.videoUrl.replace('watch?v=', 'embed/')}
                title={selectedVideo.title}
                frameBorder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
              ></iframe>
            </Box>
          ) : (
            <Typography variant="body1">Select a video to view</Typography>
          )}
        </Box>
      </Box>
    </Container>
  );
};

export default CourseContent;