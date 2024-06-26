package com.sethu.blog.mapper;

import com.sethu.blog.dto.CommentDTO;
import com.sethu.blog.dto.PostDTO;
import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.entity.Comment;
import com.sethu.blog.entity.Post;
import com.sethu.blog.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Mapper {

    public static UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setPosts(mapToPostDTOList(user.getPosts()));
        userDTO.setComments(mapToCommentDTOList(user.getComments()));
        userDTO.setMobileNumber(user.getMobileNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    private static List<PostDTO> mapToPostDTOList(List<Post> posts){
        return Optional.ofNullable(posts)
                .map(list -> list.stream().map(Mapper::mapToPostDTOWithoutAuthor).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    private static List<CommentDTO> mapToCommentDTOList(List<Comment> comments) {
        return Optional.ofNullable(comments)
                .map(list -> list.stream().map(Mapper::mapToCommentDTOWithoutAuthor).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    private static PostDTO mapToPostDTOWithoutAuthor(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setCreateDate(post.getCreatedDate());
        // Do not map the author to avoid circular mapping
        return postDTO;
    }

    private static CommentDTO mapToCommentDTOWithoutAuthor(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setPost(mapToPostDTOWithoutAuthor(comment.getPost()));
        commentDTO.setCreatedDate(comment.getCreatedDate());
        // Do not map the author and post to avoid circular mapping
        return commentDTO;
    }

    public static User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setRole(userDTO.getRole());
        return user;
    }

    public static PostDTO mapToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setAuthor(mapToUserDTO(post.getAuthor()));
        return postDTO;
    }

    public static Post mapToPost(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAuthor(mapToUser(postDTO.getAuthor()));
        return post;
    }

    public static Comment mapToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setContent(commentDTO.getContent());
        //comment.setAuthor(mapToUser(commentDTO.getAuthor()));
        comment.setPost(mapToPost(commentDTO.getPost()));
        return comment;
    }

    public static CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        // commentDTO.setAuthor(mapToUserDTO(comment.getAuthor()));
        commentDTO.setPost(mapToPostDTO(comment.getPost()));
        return commentDTO;
    }
}
