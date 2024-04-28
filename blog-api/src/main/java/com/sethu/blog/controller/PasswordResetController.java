package com.sethu.blog.controller;

import com.sethu.blog.service.PasswordResetTokenService;
import com.sethu.blog.service.UserService;
import com.sethu.blog.service.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PasswordResetController {

    private final UserService userService;

    private final PasswordResetTokenService passwordResetTokenService;

    private EmailService emailService;


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> email) {
        var userEmail = email.get("email");
        var userDTO = userService.getUserByEmail(userEmail);

        var passwordResetToken = passwordResetTokenService.generateResetToken(userDTO.getId());

        // Send reset password link in email
        var resetPasswordLink = "http://localhost:8080/api/v1/reset-password?token=" + passwordResetToken.getResetToken();
        emailService.sendEmail(userDTO.getEmail(), "Reset Your Password", userDTO.getUsername(), resetPasswordLink);

        return ResponseEntity.ok("Password reset link sent to your email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody Map<String, String> password) {
        var newPassword = password.get("password");
        var passwordResetToken = passwordResetTokenService.getResetToken(token);

        if (passwordResetToken != null) {
            userService.updateUserPassword(passwordResetToken.getUser(), newPassword);
            passwordResetTokenService.deleteResetToken(token);
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
    }
}
