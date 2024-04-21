package com.sethu.blog.controller;

import com.sethu.blog.dto.PostDTO;
import com.sethu.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    void testCreatePost() {
        // Given
        PostDTO postDTO = new PostDTO();
        when(postService.createPost(postDTO)).thenReturn(postDTO);

        // When
        ResponseEntity<PostDTO> responseEntity = postController.createPost(postDTO);

        // Then
        assertEquals(ResponseEntity.ok(postDTO), responseEntity);
    }

    @Test
    void testGetPostById() {
        // Given
        Long postId = 1L;
        PostDTO postDTO = new PostDTO();
        when(postService.getPostById(postId)).thenReturn(postDTO);

        // When
        ResponseEntity<PostDTO> responseEntity = postController.getPostById(postId);

        // Then
        assertEquals(ResponseEntity.ok(postDTO), responseEntity);
    }

    @Test
    void testGetAllPosts() {
        // Given
        List<PostDTO> postDTOList = Arrays.asList(new PostDTO(), new PostDTO());
        when(postService.getAllPosts()).thenReturn(postDTOList);

        // When
        ResponseEntity<List<PostDTO>> responseEntity = postController.getAllPosts();

        // Then
        assertEquals(ResponseEntity.ok(postDTOList), responseEntity);
    }

    @Test
    void testUpdatePost() {
        // Given
        Long postId = 1L;
        PostDTO postDTO = new PostDTO();
        when(postService.updatePost(postId, postDTO)).thenReturn(postDTO);

        // When
        ResponseEntity<PostDTO> responseEntity = postController.updatePost(postId, postDTO);

        // Then
        assertEquals(ResponseEntity.ok(postDTO), responseEntity);
    }

    @Test
    void testDeletePost() {
        // Given
        Long postId = 1L;

        // When
        ResponseEntity<String> responseEntity = postController.deletePost(postId);

        // Then
        verify(postService, times(1)).deletePost(postId);
        assertEquals(ResponseEntity.ok("Post deleted successful"), responseEntity);
    }
}
