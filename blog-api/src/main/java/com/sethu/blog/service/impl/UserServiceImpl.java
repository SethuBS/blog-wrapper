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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final EmailConfiguration emailConfiguration;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EmailService emailService,
                           EmailConfiguration emailConfiguration) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.emailConfiguration = emailConfiguration;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        var email = userDTO.getEmail();
        var username = userDTO.getUsername();

        var existingUserEmailAddress = userRepository.findUserByEmail(email);
        var existingUserName = userRepository.findByUsername(username);

        Optional.ofNullable(existingUserEmailAddress)
                .ifPresent(e -> { throw new ResourceAlreadyExistsException("User with email: " + email + " already exists"); });

        Optional.ofNullable(existingUserName)
                .ifPresent(u -> { throw new ResourceAlreadyExistsException("User with email: " + username + " already exists"); });


        var user = Mapper.mapToUser(userDTO);

        var savedUser = userRepository.save(user);
        // welcoming email.
        emailService.sendEmail(savedUser.getEmail(), emailConfiguration.getEmailSubject(), savedUser.getUsername(), emailConfiguration.getEmailBody());

        return Mapper.mapToUserDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        var user = userOptional.orElseThrow(() -> {
            if (userOptional.isPresent())
                return new ResourceNotFoundException("User with given id: " + userId + " does not exist");
            return null;
        });
        return Mapper.mapToUserDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        var user = userRepository.findUserByEmail(email);
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
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " does not exist"));

        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        user.setMobileNumber(updatedUser.getMobileNumber());
        user.setRole(updatedUser.getRole());
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
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByEmail(String email) {
        var user = userRepository.findUserByEmail(email);
        logger.info(user);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with this email" + email);
        }

        logger.info("Loaded user: " + user.getEmail() + ", User Password: " + user.getPassword() + ", Role: " + user.getRole());
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Add user roles as authorities if needed
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);

    }
}
