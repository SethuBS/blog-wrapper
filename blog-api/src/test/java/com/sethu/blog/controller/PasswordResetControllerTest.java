package com.sethu.blog.controller;

import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.entity.PasswordResetToken;
import com.sethu.blog.service.PasswordResetTokenService;
import com.sethu.blog.service.UserService;
import com.sethu.blog.service.email.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PasswordResetControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordResetTokenService passwordResetTokenService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PasswordResetController passwordResetController;

    @Test
    public void testForgotPassword() {
        Map<String, String> emailMap = new HashMap<>();
        emailMap.put("email", "test@example.com");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("test@example.com");
        userDTO.setUsername("testuser");

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setResetToken("testToken");
        passwordResetToken.setUser(userDTO.getId());

        when(userService.getUserByEmail("test@example.com")).thenReturn(userDTO);
        when(passwordResetTokenService.generateResetToken(userDTO.getId())).thenReturn(passwordResetToken);

        ResponseEntity<?> response = passwordResetController.forgotPassword(emailMap);

        verify(emailService).sendEmail(eq("test@example.com"), eq("Reset Your Password"), eq("testuser"), anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testResetPassword_ValidToken() {
        Map<String, String> passwordMap = new HashMap<>();
        passwordMap.put("password", "newPassword");

        String testToken = "validToken";

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setResetToken(testToken);
        passwordResetToken.setUser(1L);

        when(passwordResetTokenService.getResetToken(testToken)).thenReturn(passwordResetToken);

        ResponseEntity<?> response = passwordResetController.resetPassword(testToken, passwordMap);

        verify(userService).updateUserPassword(passwordResetToken.getUser(), "newPassword");
        verify(passwordResetTokenService).deleteResetToken(testToken);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testResetPassword_InvalidToken() {
        Map<String, String> passwordMap = new HashMap<>();
        passwordMap.put("password", "newPassword");

        String invalidToken = "invalidToken";

        when(passwordResetTokenService.getResetToken(invalidToken)).thenReturn(null);

        ResponseEntity<?> response = passwordResetController.resetPassword(invalidToken, passwordMap);

        verify(userService, never()).updateUserPassword(anyLong(), anyString());
        verify(passwordResetTokenService, never()).deleteResetToken(anyString());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
