package com.sethu.blog.service;

import com.sethu.blog.entity.PasswordResetToken;

public interface PasswordResetTokenService {
    PasswordResetToken generateResetToken(Long userId);

    PasswordResetToken getResetToken(String token);

    void deleteResetToken(String token);
}
