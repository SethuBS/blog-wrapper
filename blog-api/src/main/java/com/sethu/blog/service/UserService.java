package com.sethu.blog.service;

import com.sethu.blog.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(Long userId);
    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long userId, UserDTO updatedUser);

    void deleteUser(Long userId);
    void updateUserPassword(Long userId, String newPassword);

    UserDetails loadUserByEmail(String email);
}
