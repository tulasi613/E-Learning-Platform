
import React, { useContext } from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

const Navbar = () => {
  const navigate = useNavigate();
  const { auth, setAuth } = useContext(AuthContext);

  const handleLogout = () => {
    localStorage.removeItem('token'); // Clear the token
    setAuth({ token: null, role: null }); // Reset auth context
    navigate('/'); // Redirect to the landing page
  };

  return (
    <AppBar position="static" color="primary">
      <Toolbar>
        <Typography
          variant="h6"
          sx={{ flexGrow: 1, cursor: 'pointer' }}
          onClick={() => navigate('/')}
        >
         E-Learning-Platform
        </Typography>
        <Box>
          {auth.role === 'INSTRUCTOR' && (
            <>
              <Button color="inherit" onClick={() => navigate('/instructor-dashboard')}>
                Dashboard
              </Button>
              <Button color="inherit" onClick={() => navigate('/instructor-profile')}>
                Profile
              </Button>
              <Button color="inherit" onClick={() => navigate('/add-course')}>
                Add Course
              </Button>
              <Button color="inherit" onClick={handleLogout}>
                Logout
              </Button>
            </>
          )}
          {auth.role === 'USER' && (
            <>
              <Button color="inherit" onClick={() => navigate('/user-dashboard')}>
                Dashboard
              </Button>
              <Button color="inherit" onClick={() => navigate('/user-profile')}>
                Profile
              </Button>
              <Button color="inherit" onClick={handleLogout}>
                Logout
              </Button>
            </>
          )}
          {!auth.token && (
            <>
              <Button color="inherit" onClick={() => navigate('/login')}>
                Login
              </Button>
              <Button color="inherit" onClick={() => navigate('/register')}>
                Register
              </Button>
            </>
          )}
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
