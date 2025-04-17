import React, { useState } from 'react';
import { Container, TextField, Button, Typography, Box, MenuItem } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const RegistrationPage = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    role: 'USER',
  });
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const validate = () => {
    const newErrors = {};
    if (!formData.name) newErrors.name = 'Name is required.';
    if (!formData.email || !/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Please enter a valid email.';
    }
    if (!formData.password || formData.password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters long.';
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleRegister = async () => {
    if (!validate()) return;

    try {
      const endpoint =
        formData.role === 'INSTRUCTOR'
          ? 'http://localhost:8081/api/instructors'
          : 'http://localhost:8081/api/users';

      const response = await axios.post(endpoint, formData);
      if (response.status === 201) {
        navigate('/login'); // Redirect to login page
      }
    } catch (error) {
      console.error('Registration failed', error);
      setErrors({ general: 'Registration failed. Please try again.' });
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 5 }}>
      <Typography variant="h4" gutterBottom>
        Register
      </Typography>
      <Box sx={{ mt: 3 }}>
        <TextField
          label="Name"
          name="name"
          fullWidth
          margin="normal"
          value={formData.name}
          onChange={handleChange}
          error={!!errors.name}
          helperText={errors.name}
        />
        <TextField
          label="Email"
          name="email"
          fullWidth
          margin="normal"
          value={formData.email}
          onChange={handleChange}
          error={!!errors.email}
          helperText={errors.email}
        />
        <TextField
          label="Password"
          name="password"
          type="password"
          fullWidth
          margin="normal"
          value={formData.password}
          onChange={handleChange}
          error={!!errors.password}
          helperText={errors.password}
        />
        <TextField
          select
          label="Role"
          name="role"
          fullWidth
          margin="normal"
          value={formData.role}
          onChange={handleChange}
        >
          <MenuItem value="USER">User</MenuItem>
          <MenuItem value="INSTRUCTOR">Instructor</MenuItem>
        </TextField>
        {errors.general && (
          <Typography color="error" sx={{ mt: 1 }}>
            {errors.general}
          </Typography>
        )}
        <Button
          variant="contained"
          color="primary"
          fullWidth
          sx={{ mt: 2 }}
          onClick={handleRegister}
        >
          Register
        </Button>
      </Box>
      <Typography variant="body2" align="center" sx={{ mt: 2 }}>
        Already have an account? <a href="/login">Login here</a>
      </Typography>
    </Container>
  );
};

export default RegistrationPage;