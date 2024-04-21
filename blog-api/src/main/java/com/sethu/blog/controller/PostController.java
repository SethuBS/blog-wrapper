package com.sethu.blog.controller;

import com.sethu.blog.dto.PostDTO;
import com.sethu.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/post")
public class PostController {

    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        return ResponseEntity.ok(postService.createPost(postDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping("id")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("id") Long postId,
                                              @RequestBody PostDTO postDTO){
        return ResponseEntity.ok(postService.updatePost(postId,postDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long postId){
        postService.deletePost(postId);
        return ResponseEntity.ok("Post deleted successful");
    }
}
