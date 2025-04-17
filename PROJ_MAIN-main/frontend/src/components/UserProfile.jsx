import React, { useState, useEffect } from 'react';
import { 
  Container, Typography, Paper, Button, Dialog,
  DialogTitle, DialogContent, DialogActions, TextField
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const UserProfile = () => {
  const [user, setUser] = useState(null);
  const [openEdit, setOpenEdit] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: ''
  });
  const navigate = useNavigate();

  useEffect(() => {
    fetchUserProfile();
  }, []);
  // const jwtDecode = require('jwt-decode');
  const fetchUserProfile = async () => {
    try {
      const token = localStorage.getItem('token');
      // const decodedToken = jwtDecode(token);
      // console.log('Payload:', decodedToken);
      const response = await axios.get('http://localhost:8081/api/users/profile', {
        headers: { Authorization: `Bearer ${token}` }
      });
      


      setUser(response.data);
      setFormData({
        name: response.data.name,
        email: response.data.email,
        password: ''
      });
    } catch (error) {
      console.error('Error fetching profile:', error);
    }
  };

  const handleUpdate = async () => {
    try {
      const token = localStorage.getItem('token');
      await axios.put(
        `http://localhost:8081/api/users/${user.userID}`,
        formData,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setOpenEdit(false);
      fetchUserProfile();
      alert('Profile updated successfully!');
    } catch (error) {
      console.error('Error updating profile:', error);
      alert('Failed to update profile');
    }
  };

  const handleDelete = async () => {
    if (!window.confirm('Are you sure you want to delete your account? This action cannot be undone.')) {
      return;
    }

    try {
      const token = localStorage.getItem('token');
      await axios.delete(
        `http://localhost:8081/api/users/${user.userID}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      localStorage.removeItem('token');
      navigate('/');
    } catch (error) {
      console.error('Error deleting account:', error);
      alert('Failed to delete account');
    }
  };

  if (!user) return <Typography>Loading...</Typography>;

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Paper sx={{ p: 4 }}>
        <Typography variant="h4" gutterBottom>
          User Profile
        </Typography>
        
        <Typography variant="h6" sx={{ mt: 2 }}>Name</Typography>
        <Typography paragraph>{user.name}</Typography>
        
        <Typography variant="h6">Email</Typography>
        <Typography paragraph>{user.email}</Typography>

        <Button 
          variant="contained" 
          color="primary" 
          onClick={() => setOpenEdit(true)}
          sx={{ mr: 2 }}
        >
          Update Profile
        </Button>
        <Button 
          variant="contained" 
          color="error" 
          onClick={handleDelete}
        >
          Delete Account
        </Button>
      </Paper>

      <Dialog open={openEdit} onClose={() => setOpenEdit(false)}>
        <DialogTitle>Update Profile</DialogTitle>
        <DialogContent>
          <TextField
            fullWidth
            label="Name"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Email"
            value={formData.email}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
            margin="normal"
          />
          <TextField
            fullWidth
            label="New Password"
            type="password"
            value={formData.password}
            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
            margin="normal"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenEdit(false)}>Cancel</Button>
          <Button onClick={handleUpdate} color="primary">Update</Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default UserProfile;
