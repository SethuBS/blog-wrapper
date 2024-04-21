package com.sethu.blog.service.impl;

import com.sethu.blog.dto.PostDTO;
import com.sethu.blog.entity.Post;
import com.sethu.blog.exception.ResourceNotFundException;
import com.sethu.blog.mapper.Mapper;
import com.sethu.blog.repository.PostRepository;
import com.sethu.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    @Override
    public PostDTO createPost(PostDTO newPost) {
        Post postToSave = Mapper.mapToPost(newPost);
        Post post = postRepository.save(postToSave);
        return Mapper.mapToPostDTO(post);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFundException("Post with given id: " + postId + " does not exist"));
        return Mapper.mapToPostDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream().map(Mapper::mapToPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO updatePost(Long postId, PostDTO updatedPost) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFundException("Post with given id: " + postId + " does not exist"));

        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setCreatedDate(updatedPost.getCreateDate());
        postRepository.save(post);
        return Mapper.mapToPostDTO(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFundException("Post with given id: " + postId + " does not exist"));
        postRepository.deleteById(postId);
    }
}
