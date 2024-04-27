package com.sethu.blog.service;

import com.sethu.blog.entity.User;
import com.sethu.blog.exception.ResourceNotFoundException;
import com.sethu.blog.repository.UserRepository;
import com.sethu.blog.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testUpdateUserPassword() {
        Long userId = 1L;
        String newPassword = "newPassword";

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setPassword("oldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.updateUserPassword(userId, newPassword);

        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(newPassword, existingUser.getPassword());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateUserPassword_UserNotFound() {
        Long userId = 1L;
        String newPassword = "newPassword";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        userService.updateUserPassword(userId, newPassword);
    }
}
