package com.sethu.blog.repository;

import com.sethu.blog.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindUserByEmail() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findUserByEmail(email)).thenReturn(user);

        // When
        User foundUser = userRepository.findUserByEmail(email);

        // Then
        assertEquals(user, foundUser);
    }

    @Test
    void testFindByUsername() {
        // Given
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        // When
        User foundUser = userRepository.findByUsername(username);

        // Then
        assertEquals(user, foundUser);
    }
}

