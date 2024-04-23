package com.sethu.blog.service;

import com.sethu.blog.service.generator.PasswordGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class PasswordGeneratorTest {

    @Test
    void generateDefaultPassword_Length() {
        // Given
        int length = 12;

        // When
        String password = PasswordGenerator.generateDefaultPassword(length);

        // Then
        assertEquals(length, password.length());
    }

    @Test
    void generateDefaultPassword_ValidCharacters() {
        // Given
        String allCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*_+-=?";

        // When
        String password = PasswordGenerator.generateDefaultPassword(allCharacters.length());

        // Then
        for (char c : password.toCharArray()) {
            assertTrue(allCharacters.contains(String.valueOf(c)));
        }
    }

    @Test
    void generateDefaultPassword_SecureRandom() {
        // Given
        int length = 12;

        // When
        String password1 = PasswordGenerator.generateDefaultPassword(length);
        String password2 = PasswordGenerator.generateDefaultPassword(length);

        // Then
        assertNotEquals(password1, password2);
    }
}
