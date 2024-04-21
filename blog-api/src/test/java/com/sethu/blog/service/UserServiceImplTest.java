package com.sethu.blog.service;

import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.entity.User;
import com.sethu.blog.exception.ResourceAlreadyExistsException;
import com.sethu.blog.mapper.UserMapper;
import com.sethu.blog.repository.UserRepository;
import com.sethu.blog.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO testUserDTO;

    @BeforeEach
    public void setUp() {
        // Create a test UserDTO
        testUserDTO = new UserDTO(1L, "testUser", "test@example.com", null, null);
    }

    @Test
    void createUser_Success() {

        User user = UserMapper.mapToUser(testUserDTO);

        when(userRepository.findUserByEmail(testUserDTO.getEmail())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDTO createdUserDTO = userService.createUser(testUserDTO);

        // Assert
        assertNotNull(createdUserDTO);
        assertEquals("test@example.com", createdUserDTO.getEmail());
        assertEquals("testUser", createdUserDTO.getUsername());
        verify(userRepository, times(1)).findUserByEmail("test@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_Failure_UserAlreadyExists() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("existing@example.com");
        userDTO.setUsername("existinguser");

        User existingUser = new User();
        existingUser.setEmail("existing@example.com");

        when(userRepository.findUserByEmail("existing@example.com")).thenReturn(existingUser);

        // Act & Assert
        assertThrows(ResourceAlreadyExistsException.class, () -> userService.createUser(userDTO));
        verify(userRepository, times(1)).findUserByEmail("existing@example.com");
        verify(userRepository, never()).save(any(User.class));
    }
}
