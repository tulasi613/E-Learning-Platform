
import React, { useState, useContext } from 'react';
import { 
  Container, TextField, Button, Typography, Box, 
  MenuItem, Fade, useTheme, Paper, Zoom 
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';
import { LockOpen, Person } from '@mui/icons-material';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('USER');
  const navigate = useNavigate();
  const { setAuth } = useContext(AuthContext);
  const theme = useTheme();

  const handleLogin = async () => {
    try {
      const endpoint = role === 'INSTRUCTOR' 
        ? 'http://localhost:8081/api/instructors/login'
        : 'http://localhost:8081/api/users/login';

      const response = await axios.post(endpoint, { email, password });

      if (response.data?.token) {
        const token = response.data.token;
        localStorage.setItem('token', token);
        const decodedToken = JSON.parse(atob(token.split('.')[1]));
        setAuth({ token, role: decodedToken.roles[0] });

        navigate(decodedToken.roles.includes('INSTRUCTOR') 
          ? '/instructor-dashboard' 
          : '/user-dashboard');
      }
    } catch (error) {
      console.error('Login failed:', error);
      alert('Login failed. Please check your credentials.');
    }
  };

  return (
    <Box
      sx={{
        minHeight: '100vh',
        background: `
          linear-gradient(135deg, ${theme.palette.primary.main} 0%, ${theme.palette.secondary.main} 100%),
          repeating-linear-gradient(45deg, 
            rgba(255,255,255,0.1) 0px, 
            rgba(255,255,255,0.1) 2px,
            transparent 2px,
            transparent 4px)
        `,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        py: 8
      }}
    >
      <Fade in={true} timeout={800}>
        <Paper
          elevation={6}
          sx={{
            width: '100%',
            maxWidth: '450px',
            p: 4,
            borderRadius: '20px',
            background: 'rgba(255, 255, 255, 0.95)',
            backdropFilter: 'blur(10px)',
            boxShadow: '0 8px 32px rgba(0, 0, 0, 0.1)'
          }}
        >
          <Box textAlign="center" mb={4}>
            <LockOpen 
              sx={{ 
                fontSize: 50, 
                color: theme.palette.primary.main,
                mb: 2 
              }} 
            />
            <Typography 
              variant="h3" 
              sx={{ 
                fontWeight: 700,
                background: `linear-gradient(45deg, ${theme.palette.primary.main}, ${theme.palette.secondary.main})`,
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent',
                mb: 1
              }}
            >
              Welcome Back
            </Typography>
            <Typography variant="body1" color="text.secondary">
              Continue your learning journey
            </Typography>
          </Box>

          <Zoom in={true} style={{ transitionDelay: '200ms' }}>
            <Box component="form" onSubmit={(e) => { e.preventDefault(); handleLogin(); }}>
              <TextField
                fullWidth
                label="Email"
                margin="normal"
                variant="outlined"
                InputProps={{
                  startAdornment: <Person sx={{ color: 'action.active', mr: 1 }} />
                }}
                sx={{
                  '& .MuiOutlinedInput-root': {
                    borderRadius: '12px',
                    '&:hover fieldset': { borderColor: theme.palette.primary.main }
                  }
                }}
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />

              <TextField
                fullWidth
                type="password"
                label="Password"
                margin="normal"
                variant="outlined"
                sx={{
                  '& .MuiOutlinedInput-root': {
                    borderRadius: '12px',
                    '&:hover fieldset': { borderColor: theme.palette.primary.main }
                  }
                }}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />

              <TextField
                select
                fullWidth
                label="Login as"
                margin="normal"
                value={role}
                onChange={(e) => setRole(e.target.value)}
                sx={{
                  '& .MuiOutlinedInput-root': {
                    borderRadius: '12px',
                    '&:hover fieldset': { borderColor: theme.palette.primary.main }
                  }
                }}
              >
                <MenuItem value="USER">Learner</MenuItem>
                <MenuItem value="INSTRUCTOR">Instructor</MenuItem>
              </TextField>

              <Button
                fullWidth
                variant="contained"
                size="large"
                sx={{
                  mt: 3,
                  py: 1.5,
                  borderRadius: '12px',
                  fontSize: '1.1rem',
                  textTransform: 'none',
                  transition: 'transform 0.2s',
                  '&:hover': {
                    transform: 'translateY(-2px)'
                  }
                }}
                onClick={handleLogin}
              >
                Sign In
              </Button>

              <Typography 
                variant="body2" 
                align="center" 
                sx={{ mt: 3, color: 'text.secondary' }}
              >
                New here?{' '}
                <Button 
                  href="/register" 
                  color="primary"
                  sx={{ 
                    textTransform: 'none',
                    fontWeight: 600,
                    '&:hover': { background: 'none' }
                  }}
                >
                  Create an account
                </Button>
              </Typography>
            </Box>
          </Zoom>
        </Paper>
      </Fade>
    </Box>
  );
};

export default LoginPage;