package com.sethu.blog.repository;

import com.sethu.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
}
