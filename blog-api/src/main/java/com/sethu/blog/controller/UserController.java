package com.sethu.blog.controller;

import com.sethu.blog.configuration.JwtProvider;
import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.response.AuthResponse;
import com.sethu.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;

    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@Validated @RequestBody UserDTO userDTO) {

        String password = userDTO.getPassword();
        userDTO.setPassword(passwordEncoder.encode(password));
        logger.info(password);
        UserDTO user = userService.createUser(userDTO);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);


        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody UserDTO loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        logger.info(username + "-------" + password);

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@Validated @PathVariable("id") Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@Validated @PathVariable("id") Long userId,
                                              @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(userId, userDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@Validated @PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successful");
    }

    private Authentication authenticate(String username, String password) {

        logger.info(username + "---++----" + password);

        UserDetails userDetails = userService.loadUserByEmail(username);

        logger.info("Sig in in user details" + userDetails);

        if (userDetails == null) {
            logger.info("Sign in details - null" + userDetails);

            throw new BadCredentialsException("Invalid username and password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.info("Sign in userDetails - password mismatch" + userDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
