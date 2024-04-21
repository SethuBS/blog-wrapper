package com.sethu.blog.controller;

import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO testUserDTO;

    @BeforeEach
    public void setUp() {
        // Create a test UserDTO
        testUserDTO = new UserDTO(1L, "testUser", "test@example.com", null, null);
    }

    @Test
    public void testCreateUser() {
        // Mock the userService's createUser method to return the testUserDTO when called
        when(userService.createUser(testUserDTO)).thenReturn(testUserDTO);

        // Call the createUser method in the userController
        ResponseEntity<UserDTO> responseEntity = userController.createUser(testUserDTO);

        // Verify that the returned ResponseEntity contains the expected UserDTO
        assertEquals(testUserDTO, responseEntity.getBody());
    }
}
