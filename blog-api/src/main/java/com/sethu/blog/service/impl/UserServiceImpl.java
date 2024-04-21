package com.sethu.blog.service.impl;

import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.entity.User;
import com.sethu.blog.exception.ResourceAlreadyExistsException;
import com.sethu.blog.mapper.UserMapper;
import com.sethu.blog.repository.UserRepository;
import com.sethu.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String email = userDTO.getEmail();

        User existingUser = userRepository.findUserByEmail(email);

        if (existingUser != null) {
            throw new ResourceAlreadyExistsException("User with email: " + email + " already exists");
        }

        User user = UserMapper.mapToUser(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDTO(savedUser);

    }
}
