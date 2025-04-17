import React, { useState, useEffect } from 'react';
import {
  Container,
  TextField,
  Button,
  Typography,
  Box,
  Grid,
  IconButton,
  List,
  ListItem,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const UpdateAssessment = () => {
  const { courseID, assessmentId } = useParams();
  const navigate = useNavigate();
  
  const [formData, setFormData] = useState({
    type: '',
    maxScore: '',
    questions: [],
    courseID: courseID
  });
  
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchAssessment = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get(
          `http://localhost:8081/api/assessments/${assessmentId}`,
          { headers: { Authorization: `Bearer ${token}` } }
        );
        
        const questions = Object.entries(response.data.questions).map(([q, a]) => ({
          question: q,
          options: response.data.options[q] || ['', '', '', ''],
          correctAnswer: a
        }));

        setFormData({
          type: response.data.type,
          maxScore: response.data.maxScore,
          questions: questions,
          courseID: response.data.courseID
        });
      } catch (err) {
        setError('Failed to fetch assessment');
        console.error(err);
      }
    };
    fetchAssessment();
  }, [assessmentId, courseID]);

  const handleQuestionChange = (index, field, value) => {
    const updatedQuestions = [...formData.questions];
    updatedQuestions[index][field] = value;
    setFormData({ ...formData, questions: updatedQuestions });
  };

  const handleOptionChange = (questionIndex, optionIndex, value) => {
    const updatedQuestions = [...formData.questions];
    updatedQuestions[questionIndex].options[optionIndex] = value;
    setFormData({ ...formData, questions: updatedQuestions });
  };

  const handleDeleteQuestion = (index) => {
    const updatedQuestions = formData.questions.filter((_, i) => i !== index);
    setFormData({ ...formData, questions: updatedQuestions });
  };

  const addNewQuestion = () => {
    setFormData({
      ...formData,
      questions: [
        ...formData.questions,
        { question: '', options: ['', '', '', ''], correctAnswer: '' }
      ]
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      const payload = {
        ...formData,
        questions: formData.questions.reduce((acc, q) => {
          acc[q.question] = q.correctAnswer;
          return acc;
        }, {}),
        options: formData.questions.reduce((acc, q) => {
          acc[q.question] = q.options;
          return acc;
        }, {})
      };

      await axios.put(
        `http://localhost:8081/api/assessments/${assessmentId}`,
        payload,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      
      navigate(`/course/${courseID}`);
    } catch (err) {
      setError('Failed to update assessment');
      console.error(err);
    }
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" gutterBottom>
        Update Assessment
      </Typography>
      
      {error && (
        <Typography color="error" gutterBottom>
          {error}
        </Typography>
      )}

      <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
        <TextField
          fullWidth
          label="Type"
          value={formData.type}
          onChange={(e) => setFormData({ ...formData, type: e.target.value })}
          margin="normal"
          required
        />
        
        <TextField
          fullWidth
          label="Max Score"
          type="number"
          value={formData.maxScore}
          onChange={(e) => setFormData({ ...formData, maxScore: e.target.value })}
          margin="normal"
          required
        />

        <Typography variant="h6" sx={{ mt: 4, mb: 2 }}>
          Questions
        </Typography>

        <List>
          {formData.questions.map((q, qIndex) => (
            <ListItem
              key={qIndex}
              sx={{
                flexDirection: 'column',
                border: '1px solid #ddd',
                borderRadius: '4px',
                mb: 2,
                p: 2
              }}
            >
              <Box sx={{ display: 'flex', width: '100%', gap: 2 }}>
                <TextField
                  fullWidth
                  label="Question"
                  value={q.question}
                  onChange={(e) => handleQuestionChange(qIndex, 'question', e.target.value)}
                  required
                />
                <IconButton
                  color="error"
                  onClick={() => handleDeleteQuestion(qIndex)}
                >
                  <DeleteIcon />
                </IconButton>
              </Box>

              <Grid container spacing={2} sx={{ mt: 1 }}>
                {q.options.map((opt, optIndex) => (
                  <Grid item xs={6} key={optIndex}>
                    <TextField
                      fullWidth
                      label={`Option ${optIndex + 1}`}
                      value={opt}
                      onChange={(e) => handleOptionChange(qIndex, optIndex, e.target.value)}
                      required
                    />
                  </Grid>
                ))}
              </Grid>

              <TextField
                fullWidth
                label="Correct Answer"
                value={q.correctAnswer}
                onChange={(e) => handleQuestionChange(qIndex, 'correctAnswer', e.target.value)}
                sx={{ mt: 2 }}
                required
              />
            </ListItem>
          ))}
        </List>

        <Button
          variant="outlined"
          onClick={addNewQuestion}
          sx={{ mt: 2, mb: 4 }}
        >
          Add Question
        </Button>

        <Button
          type="submit"
          variant="contained"
          color="primary"
          fullWidth
        >
          Update Assessment
        </Button>
      </Box>
    </Container>
  );
};

export default UpdateAssessment;