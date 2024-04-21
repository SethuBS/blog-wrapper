package com.sethu.blog.mapper;

import com.sethu.blog.dto.PostDTO;
import com.sethu.blog.entity.Post;

public class PostMapper {

    public static PostDTO mapToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        // Map the user
        postDTO.setAuthor(UserMapper.mapToUserDTO(post.getAuthor()));
        return postDTO;
    }

    public static Post mapToPost(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        // Map the user
        post.setAuthor(UserMapper.mapToUser(postDTO.getAuthor()));
        return post;
    }
}
