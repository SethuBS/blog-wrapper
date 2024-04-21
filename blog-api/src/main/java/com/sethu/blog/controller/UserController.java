package com.sethu.blog.controller;

import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/blogs")
public class UserController {

    private UserService userService;

    public ResponseEntity<UserDTO> createUser(UserDTO userDTO){
        UserDTO savedUser = userService.createUser(userDTO);
        return ResponseEntity.ok(savedUser);
    }
}
