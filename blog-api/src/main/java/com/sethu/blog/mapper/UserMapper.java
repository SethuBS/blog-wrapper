package com.sethu.blog.mapper;

import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.entity.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        // Map the posts
        userDTO.setPosts(user.getPosts().stream()
                .map(PostMapper::mapToPostDTO)
                .collect(Collectors.toList()));
        // Map the comments
        userDTO.setComments(user.getComments().stream()
                .map(CommentMapper::mapToCommentDTO)
                .collect(Collectors.toList()));
        return userDTO;
    }

    public static User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        // Map the posts
        user.setPosts(userDTO.getPosts().stream()
                .map(PostMapper::mapToPost)
                .collect(Collectors.toList()));
        // Map the comments
        user.setComments(userDTO.getComments().stream()
                .map(CommentMapper::mapToComment)
                .collect(Collectors.toList()));
        return user;
    }
}
