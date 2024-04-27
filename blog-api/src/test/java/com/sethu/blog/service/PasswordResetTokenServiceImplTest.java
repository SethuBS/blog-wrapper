package com.sethu.blog.service;

import com.sethu.blog.entity.PasswordResetToken;
import com.sethu.blog.entity.User;
import com.sethu.blog.repository.PasswordResetTokenRepository;
import com.sethu.blog.service.impl.PasswordResetTokenServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordResetTokenServiceImplTest {

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @InjectMocks
    private PasswordResetTokenServiceImpl passwordResetTokenService;

    @Test
    public void testGenerateResetToken() {
        User user = new User();
        user.setId(1L);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setResetToken("testToken");
        resetToken.setTokenExpiry(new Date());
        resetToken.setUser(user.getId());

        when(passwordResetTokenRepository.save(any(PasswordResetToken.class))).thenReturn(resetToken);

        PasswordResetToken generatedToken = passwordResetTokenService.generateResetToken(user.getId());

        assertEquals(generatedToken.getResetToken(), "testToken");
    }

    @Test
    public void testGetResetToken_ValidToken() {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setResetToken("validToken");
        when(passwordResetTokenRepository.findByResetToken("validToken")).thenReturn(resetToken);

        PasswordResetToken retrievedToken = passwordResetTokenService.getResetToken("validToken");

        assertEquals(retrievedToken.getResetToken(), "validToken");
    }

    @Test
    public void testGetResetToken_InvalidToken() {
        when(passwordResetTokenRepository.findByResetToken("invalidToken")).thenReturn(null);

        PasswordResetToken retrievedToken = passwordResetTokenService.getResetToken("invalidToken");

        assertNull(retrievedToken);
    }
}
