package com.sethu.blog.controller;

import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.entity.PasswordResetToken;
import com.sethu.blog.service.PasswordResetTokenService;
import com.sethu.blog.service.UserService;
import com.sethu.blog.service.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PasswordResetController {

    private UserService userService;

    private final PasswordResetTokenService passwordResetTokenService;

    private EmailService emailService;


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> email) {
        String userEmail = email.get("email");
        UserDTO userDTO = userService.getUserByEmail(userEmail);

        PasswordResetToken passwordResetToken = passwordResetTokenService.generateResetToken(userDTO.getId());

        // Send reset password link in email
        String resetPasswordLink = "http://localhost:8080/api/v1/reset-password?token=" + passwordResetToken.getResetToken();
        emailService.sendEmail(userDTO.getEmail(), "Reset Your Password", userDTO.getUsername(), resetPasswordLink);

        return ResponseEntity.ok("Password reset link sent to your email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody Map<String, String> password) {
        String newPassword = password.get("password");
        PasswordResetToken passwordResetToken = passwordResetTokenService.getResetToken(token);

        if (passwordResetToken != null) {
            userService.updateUserPassword(passwordResetToken.getUser(), newPassword);
            passwordResetTokenService.deleteResetToken(token);
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
    }
}
