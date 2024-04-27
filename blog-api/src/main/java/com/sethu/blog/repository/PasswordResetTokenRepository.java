package com.sethu.blog.repository;

import com.sethu.blog.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByResetToken(String resetToken);
}
