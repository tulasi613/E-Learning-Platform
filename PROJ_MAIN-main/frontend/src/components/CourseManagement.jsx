import React, { useEffect, useState } from 'react';
import {
  Container, Typography, Button, Box, List, ListItem, ListItemText,
  Paper, Divider, CircularProgress, Card, CardContent
} from '@mui/material';
import {
  VideoLibrary as VideoIcon,
  Description as ResourceIcon,
  Assessment as AssessmentIcon,
  People as PeopleIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  Add as AddIcon,
  PlayArrow as PlayIcon
} from '@mui/icons-material';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import EnrolledStudents from './EnrolledStudents'; 

const CourseManagement = () => {
  const { courseID } = useParams(); // Get courseID from the URL
  const [course, setCourse] = useState({});
  const [videos, setVideos] = useState([]);
  const [resources, setResources] = useState([]);
  const [assessments, setAssessments] = useState([]);
  const [enrolledStudents, setEnrolledStudents] = useState([]);
  const [selectedVideo, setSelectedVideo] = useState(null);
  const navigate = useNavigate();

  // Fetch course details, videos, resources, assessments, and enrolled students
  const fetchCourseDetails = async () => {
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
      const updatedVideos = videosResponse.data;
      setVideos(updatedVideos);

      // Preserve the selected video or select another video if the selected one is deleted
      if (selectedVideo) {
        const stillExists = updatedVideos.find((video) => video.videoId === selectedVideo.videoId);
        if (!stillExists && updatedVideos.length > 0) {
          setSelectedVideo(updatedVideos[0]); // Select the first video if the selected one is deleted
        } else if (!stillExists) {
          setSelectedVideo(null); // No videos left
        }
      } else if (updatedVideos.length > 0) {
        setSelectedVideo(updatedVideos[0]); // Automatically select the first video if none is selected
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

      // Fetch enrolled students
      const studentsResponse = await axios.get(`http://localhost:8081/api/enrollments/course/${courseID}/students`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setEnrolledStudents(studentsResponse.data);
    } catch (error) {
      console.error('Error fetching course details:', error);
    }
  };

  // Call fetchCourseDetails on component mount
  useEffect(() => {
    fetchCourseDetails();
  }, [courseID]);

  // Handlers for adding videos, resources, and assessments
  const handleAddVideo = () => navigate(`/course/${courseID}/add-video`);
  const handleAddResource = () => navigate(`/course/${courseID}/add-resource`);
  const handleAddAssessment = () => navigate(`/course/${courseID}/add-assessment`);

  // Handlers for deleting videos, resources, and assessments
  const handleDeleteVideo = async (videoId) => {
    try {
      const token = localStorage.getItem('token');
      await axios.delete(`http://localhost:8081/api/videos/${videoId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert('Video deleted successfully!');
      fetchCourseDetails(); // Refresh data after deletion
    } catch (error) {
      console.error('Error deleting video:', error);
      alert('Failed to delete video. Please try again.');
    }
  };

  const handleDeleteResource = async (resourceId) => {
    try {
      const token = localStorage.getItem('token');
      await axios.delete(`http://localhost:8081/api/resources/${resourceId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert('Resource deleted successfully!');
      fetchCourseDetails(); // Refresh data after deletion
    } catch (error) {
      console.error('Error deleting resource:', error);
      alert('Failed to delete resource. Please try again.');
    }
  };

  const handleUpdateResource = (resourceId) => {
    navigate(`/course/${courseID}/resource/${resourceId}/update`);
  };

  const handleUpdateVideo = (videoId) => {
    navigate(`/course/${courseID}/video/${videoId}/update`);
  };
  
  const handleUpdateAssessment = (assessmentId) => {
    navigate(`/course/${courseID}/assessment/${assessmentId}/update`);
  };

  const handleDeleteAssessment = async (assessmentID) => {
    try {
      const token = localStorage.getItem('token');
      await axios.delete(`http://localhost:8081/api/assessments/${assessmentID}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      alert('Assessment deleted successfully!');
      fetchCourseDetails(); // Refresh data after deletion
    } catch (error) {
      console.error('Error deleting assessment:', error);
      alert('Failed to delete assessment. Please try again.');
    }
  };

  // Helper function to convert YouTube URL to embed URL
  const getEmbedUrl = (videoUrl) => {
    const videoId = videoUrl.split('v=')[1]?.split('&')[0]; // Extract video ID
    return `https://www.youtube.com/embed/${videoId}`;
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 5 }}>
      {/* Course Title */}
      <Typography variant="h4" gutterBottom>
        Manage Course: {course.title}
      </Typography>

      <Box sx={{ mt: 3, display: 'flex' }}>
        {/* Sidebar */}
        <Box sx={{ width: '25%', mr: 3 }}>
          {/* Videos Section */}
          <Typography variant="h6">Videos</Typography>
          <List>
          {videos.map((video) => (
  <ListItem
    button
    key={video.videoId}
    selected={selectedVideo?.videoId === video.videoId}
    onClick={() => setSelectedVideo(video)}
    sx={{
      transition: 'background-color 0.3s ease',
      backgroundColor: selectedVideo?.videoId === video.videoId ? 'lightblue' : 'transparent',
      '&:hover': { backgroundColor: 'lightgray' },
    }}
  >
    <ListItemText primary={video.title} />
    <Box sx={{ display: 'flex', gap: 1 }}>
      <Button
        variant="outlined"
        color="primary"
        onClick={(e) => {
          e.stopPropagation();
          handleUpdateVideo(video.videoId);
        }}
        sx={{ ml: 2 }}
      >
        Update
      </Button>
      <Button
        variant="outlined"
        color="secondary"
        onClick={(e) => {
          e.stopPropagation();
          handleDeleteVideo(video.videoId);
        }}
      >
        Delete
      </Button>
    </Box>
  </ListItem>
))}
          </List>
          <Button
            variant="contained"
            color="primary"
            fullWidth
            sx={{ mt: 2 }}
            onClick={handleAddVideo}
          >
            Add Video
          </Button>

          {/* Resources Section */}
          <Typography variant="h6" sx={{ mt: 4 }}>
            Resources
          </Typography>
          <List>
          {resources.map((resource) => (
  <ListItem
    key={resource.resourceId}
    sx={{
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'flex-start',
      border: '1px solid #ddd',
      borderRadius: '8px',
      padding: '16px',
      marginBottom: '12px',
      backgroundColor: '#f9f9f9',
    }}
  >
    <Typography variant="subtitle1" sx={{ fontWeight: 'bold', mb: 1 }}>
      {resource.title}
    </Typography>
    <Typography variant="body2" sx={{ color: 'gray', mb: 1 }}>
      Type: {resource.resourceType}
    </Typography>
    <Typography
      variant="body2"
      sx={{
        color: 'blue',
        textDecoration: 'underline',
        cursor: 'pointer',
        mb: 2,
      }}
      onClick={() => window.open(resource.resourceUrl, '_blank')}
    >
      View Resource
    </Typography>
    <Box sx={{ display: 'flex', gap: 1, alignSelf: 'flex-end' }}>
      <Button
        variant="outlined"
        color="primary"
        onClick={() => handleUpdateResource(resource.resourceId)}
      >
        Update
      </Button>
      <Button
        variant="outlined"
        color="secondary"
        onClick={() => handleDeleteResource(resource.resourceId)}
      >
        Delete
      </Button>
    </Box>
  </ListItem>
))}
          </List>
          <Button
            variant="contained"
            color="primary"
            fullWidth
            sx={{ mt: 2 }}
            onClick={handleAddResource}
          >
            Add Resource
          </Button>

          {/* Assessments Section */}
          <Box sx={{ mt: 3 }}>
            <Typography variant="h5" sx={{ mt: 4 }}>
              Assessments
            </Typography>
            <List>
            {assessments.map((assessment) => (
  <ListItem
    button
    key={assessment.assessmentID}
    sx={{
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'flex-start',
      border: '1px solid #ddd',
      borderRadius: '8px',
      padding: '16px',
      marginBottom: '12px',
      backgroundColor: '#f9f9f9',
      cursor: 'pointer',
    }}
   // onClick={() => navigate(`/course/${courseID}/assessment/${assessment.assessmentID}`)}
  >
    <Typography variant="subtitle1" sx={{ fontWeight: 'bold', mb: 1 }}>
      {assessment.type}
    </Typography>
    <Typography variant="body2" sx={{ color: 'gray', mb: 1 }}>
      Max Score: {assessment.maxScore}
    </Typography>
    <Box sx={{ display: 'flex', gap: 1, alignSelf: 'flex-end' }}>
      <Button
        variant="outlined"
        color="primary"
        onClick={(e) => {
          e.stopPropagation();
          handleUpdateAssessment(assessment.assessmentID);
        }}
      >
        Update
      </Button>
      <Button
        variant="outlined"
        color="secondary"
        onClick={(e) => {
          e.stopPropagation();
          handleDeleteAssessment(assessment.assessmentID);
        }}
      >
        Delete
      </Button>
    </Box>
  </ListItem>
))}
            </List>
            <Button
              variant="contained"
              color="primary"
              fullWidth
              sx={{ mt: 2 }}
              onClick={handleAddAssessment}
            >
              Add Assessment
            </Button>
          </Box>

          {/* Enrolled Students Section */}
          <Box sx={{ mt: 3 }}>
  <EnrolledStudents courseID={courseID} />
</Box>
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
                src={getEmbedUrl(selectedVideo.videoUrl)}
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

export default CourseManagement;