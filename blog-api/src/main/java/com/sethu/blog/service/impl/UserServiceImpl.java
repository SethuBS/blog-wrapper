package com.sethu.blog.service.impl;

import com.sethu.blog.configuration.EmailConfiguration;
import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.entity.User;
import com.sethu.blog.exception.ResourceAlreadyExistsException;
import com.sethu.blog.exception.ResourceNotFoundException;
import com.sethu.blog.mapper.Mapper;
import com.sethu.blog.repository.UserRepository;
import com.sethu.blog.service.UserService;
import com.sethu.blog.service.email.EmailService;
import com.sethu.blog.service.generator.PasswordGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private EmailService emailService;

    private EmailConfiguration emailConfiguration;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String username = userDTO.getUsername();

        User existingUserEmailAddress = userRepository.findUserByEmail(email);

        if (existingUserEmailAddress != null) {
            throw new ResourceAlreadyExistsException("User with email: " + email + " already exists");
        }

        User existingUserName = userRepository.findByUsername(username);

        if (existingUserName != null) {
            throw new ResourceAlreadyExistsException("User with email: " + username + " already exists");
        }

        User user = Mapper.mapToUser(userDTO);
        user.setPassword(PasswordGenerator.generateDefaultPassword(12));
        User savedUser = userRepository.save(user);
        // welcoming email.
        emailService.sendEmail(savedUser.getEmail(), emailConfiguration.getEmailSubject(), savedUser.getUsername(), emailConfiguration.getEmailBody());

        return Mapper.mapToUserDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        User user = userOptional.orElseThrow(() -> {
            if (userOptional.isPresent())
                return new ResourceNotFoundException("User with given id: " + userId + " does not exist");
            return null;
        });
        return Mapper.mapToUserDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User with given : email" + email + " does not exist");
        }
        return Mapper.mapToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(Mapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " does not exist"));

        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        userRepository.save(user);
        return Mapper.mapToUserDTO(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " does not exist"));
        userRepository.deleteById(userId);
    }

    @Override
    public void updateUserPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
