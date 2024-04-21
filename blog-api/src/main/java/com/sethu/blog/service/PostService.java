package com.sethu.blog.service;

import com.sethu.blog.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO newPost);
    PostDTO getPostById(Long postId);
    List<PostDTO> getAllPosts();
    PostDTO updatePost(Long postId, PostDTO updatedPost);
    void deletePost(Long postId);
}
