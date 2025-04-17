
package com.reactfullstack003.ElearningPlatform_.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reactjavafullstack003.E_Learning_Platform_003.model.User;
import com.reactjavafullstack003.E_Learning_Platform_003.repository.UserRepository;
import com.reactjavafullstack003.E_Learning_Platform_003.service.UserService;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User(1, "John Doe", "john@example.com", "password", null, null);
        User user2 = new User(2, "Jane Doe", "jane@example.com", "password", null, null);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertEquals("John Doe", users.get(0).getName());
        assertEquals("Jane Doe", users.get(1).getName());
    }

    @Test
    void testGetUserById() {
        User user = new User(1, "John Doe", "john@example.com", "password", null, null);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1);
        assertEquals("John Doe", foundUser.getName());
    }

    @Test
    void testSaveUser() {
        User user = new User(1, "John Doe", "john@example.com", "password", null, null);
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);
        assertEquals("John Doe", savedUser.getName());
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User(1, "John Doe", "john@example.com", "password", null, null);
        User updatedUser = new User(1, "John Smith", "johnsmith@example.com", "newpassword", null, null);
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.updateUser(1, updatedUser);
        assertEquals("John Smith", result.getName());
        assertEquals("johnsmith@example.com", result.getEmail());
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1);

        userService.deleteUser(1);
        verify(userRepository, times(1)).deleteById(1);
    }
}