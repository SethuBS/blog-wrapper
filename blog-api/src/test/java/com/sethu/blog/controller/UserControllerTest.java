package com.sethu.blog.controller;

import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.exception.ResourceAlreadyExistsException;
import com.sethu.blog.exception.ResourceNotFoundException;
import com.sethu.blog.response.AuthResponse;
import com.sethu.blog.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO testUserDTO;

    @Mock
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp() {
        // Create a test UserDTO
        testUserDTO = new UserDTO(1L, "testUser", "testUser123@", "test@example.com", null, null, "+2784327374", "USER");
    }

    @Test
    void createUser_shouldReturnAuthResponseWithToken_whenUserCreatedSuccessfully() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        when(userService.createUser(userDTO)).thenReturn(userDTO);

        // Act
        ResponseEntity<AuthResponse> responseEntity = userController.createUser(userDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Register Success", Objects.requireNonNull(responseEntity.getBody()).getMessage());
        Assertions.assertTrue(responseEntity.getBody().getStatus());
        verify(passwordEncoder).encode("password");
        verify(userService).createUser(userDTO);
    }

    @Test
    public void testGetUserById() {
        // Mock data
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("testUser");

        // Mocking userService.getUserById(userId) to return userDTO
        when(userService.getUserById(userId)).thenReturn(userDTO);

        // Call the controller method
        ResponseEntity<UserDTO> responseEntity = userController.getUserById(userId);

        // Verify the response entity
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDTO, responseEntity.getBody());
    }

    @Test
    public void testGetAllUsers() {

        // Creating some sample UserDTO objects
        UserDTO user1 = new UserDTO(null, "user1", "user1@123", "user1@example.com", null, null, "mobile number", "ADMIN");
        UserDTO user2 = new UserDTO(null, "user2", "user2@123", "user2@example.com", null, null, "mobile number", "USER");

        // Mocking the behavior of userService.getAllUsers() to return a list of UserDTOs
        List<UserDTO> expectedUserDTOList = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(expectedUserDTOList);

        // Calling the getAllUsers method
        ResponseEntity<List<UserDTO>> responseEntity = userController.getAllUsers();

        // Verifying that the response status is OK (200)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verifying that the returned UserDTO list matches the expected list
        assertEquals(expectedUserDTOList, responseEntity.getBody());
    }

    @Test
    void testUpdateUser() {
        // Given
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("updatedUsername");
        userDTO.setEmail("updatedUsername@gmail.com");
        userDTO.setPassword("updatedUsername@123");
        // Mock userService.updateUser method
        when(userService.updateUser(userId, userDTO)).thenReturn(userDTO);

        // When
        ResponseEntity<UserDTO> responseEntity = userController.updateUser(userId, userDTO);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDTO, responseEntity.getBody());
        verify(userService, times(1)).updateUser(userId, userDTO);
    }

    @Test
    void testDeleteUser() {
        // Given
        Long userId = 1L;

        // When
        ResponseEntity<String> responseEntity = userController.deleteUser(userId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User deleted successful", responseEntity.getBody());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testGetUserById_ResourceNotFoundException() {
        // Mock data
        Long userId = 1L;

        // Mocking userService.getUserById(userId) to throw a ResourceNotFoundException
        when(userService.getUserById(userId)).thenThrow(new ResourceNotFoundException("User not found"));

        // Call the controller method and assert that ResourceNotFundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> userController.getUserById(userId));
    }

    @Test
    public void testCreateUser_ResourceAlreadyExistsException() {
        // Mock the userService's createUser method to throw a ResourceAlreadyExistsException
        when(userService.createUser(testUserDTO)).thenThrow(new ResourceAlreadyExistsException("User already exists"));

        // Call the createUser method in the userController and assert that ResourceAlreadyExistsException is thrown
        assertThrows(ResourceAlreadyExistsException.class, () -> userController.createUser(testUserDTO));
    }
}
