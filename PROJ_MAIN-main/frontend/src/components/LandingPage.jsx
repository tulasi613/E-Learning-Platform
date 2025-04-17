
import React, { useEffect, useState } from 'react';
import { Container, Typography, Box, Grid, Card, CardContent, CardActions, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Fade } from '@mui/material';

const LandingPage = () => {
  const navigate = useNavigate();
  const [courses, setCourses] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        const response = await axios.get('http://localhost:8081/api/courses/public');
        setCourses(response.data);
      } catch (error) {
        console.error('Error fetching courses:', error);
        setError('Failed to load courses. Please try again later.');
      } finally {
        setLoading(false);
      }
    };

    fetchCourses();
  }, []);

  return (
    <Box
      sx={{
        minHeight: '100vh',
        background: `
          linear-gradient(90deg, #f0f0f0 1px, transparent 1px),
          linear-gradient(180deg, #f0f0f0 1px, transparent 1px),
          linear-gradient(45deg, rgba(255,255,255,0.8), rgba(245,245,245,0.8))
        `,
        backgroundSize: '30px 30px',
        backgroundPosition: '0 0, 0 0',
        position: 'relative',
        overflow: 'hidden',
      }}
    >
      {/* Hero Section */}
      <Box
        sx={{
          background: 'linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%)',
          color: 'white',
          py: 8,
          position: 'relative',
        }}
      >
        <Container maxWidth="md">
          <Fade in={true} timeout={1000}>
            <Box sx={{ textAlign: 'center' }}>
              <Typography variant="h2" sx={{ fontWeight: 700, mb: 3 }}>
                Learn Without Limits
              </Typography>
              <Typography variant="h5" sx={{ opacity: 0.9, mb: 4 }}>
                Expand your knowledge with our expert-led courses
              </Typography>
              <Button
                variant="contained"
                color="secondary"
                size="large"
                onClick={() => navigate('/login')}
                sx={{
                  px: 6,
                  py: 1.5,
                  fontSize: '1.1rem',
                  borderRadius: '50px',
                  boxShadow: 3,
                  '&:hover': { boxShadow: 6 },
                }}
              >
                Get Started
              </Button>
            </Box>
          </Fade>
        </Container>
      </Box>

      {/* Courses Section */}
      <Container maxWidth="lg" sx={{ py: 8 }}>
        <Typography
          variant="h3"
          sx={{
            textAlign: 'center',
            fontWeight: 600,
            mb: 6,
            color: 'primary.main',
          }}
        >
          Featured Courses
        </Typography>

        {loading ? (
          <Typography textAlign="center">Loading courses...</Typography>
        ) : error ? (
          <Typography color="error">{error}</Typography>
        ) : (
          <Grid container spacing={4}>
            {courses.map((course) => (
              <Grid item xs={12} sm={6} md={4} key={course.courseID}>
                <Fade in={true} timeout={500}>
                  <Card
                    sx={{
                      height: '100%',
                      display: 'flex',
                      flexDirection: 'column',
                      transition: 'transform 0.3s, box-shadow 0.3s',
                      '&:hover': {
                        transform: 'translateY(-5px)',
                        boxShadow: 3,
                      },
                    }}
                  >
                    <CardContent sx={{ flexGrow: 1 }}>
                      <Typography variant="h6" gutterBottom sx={{ fontWeight: 600 }}>
                        {course.title}
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        {course.description}
                      </Typography>
                    </CardContent>
                    <CardActions sx={{ justifyContent: 'flex-end' }}>
                      <Button
                        variant="contained"
                        size="small"
                        onClick={() => navigate('/login')}
                        sx={{
                          mb: 1,
                          mr: 1,
                          borderRadius: '20px',
                          textTransform: 'none',
                        }}
                      >
                        Enroll Now
                      </Button>
                    </CardActions>
                  </Card>
                </Fade>
              </Grid>
            ))}
          </Grid>
        )}
      </Container>

      {/* Footer */}
      <Box sx={{ bgcolor: 'background.paper', py: 4, mt: 8 }}>
        <Container maxWidth="lg">
          <Typography variant="body2" color="text.secondary" align="center">
            © {new Date().getFullYear()} E-Learning Platform. All rights reserved.
          </Typography>
        </Container>
      </Box>
    </Box>
  );
};

export default LandingPage;