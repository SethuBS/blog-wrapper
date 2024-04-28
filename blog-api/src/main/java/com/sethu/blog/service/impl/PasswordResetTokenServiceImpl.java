package com.sethu.blog.service.impl;

import com.sethu.blog.entity.PasswordResetToken;
import com.sethu.blog.repository.PasswordResetTokenRepository;
import com.sethu.blog.service.PasswordResetTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken generateResetToken(Long userId) {

        String token = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date expiryDate = calendar.getTime();

        var resetToken = new PasswordResetToken();
        resetToken.setResetToken(token);
        resetToken.setTokenExpiry(expiryDate);
        resetToken.setUser(userId);

        return passwordResetTokenRepository.save(resetToken);
    }

    @Override
    public PasswordResetToken getResetToken(String token) {
        return passwordResetTokenRepository.findByResetToken(token);
    }

    @Override
    public void deleteResetToken(String token) {
        var resetToken = passwordResetTokenRepository.findByResetToken(token);
        if (resetToken != null) {
            passwordResetTokenRepository.delete(resetToken);
        }
    }
}
