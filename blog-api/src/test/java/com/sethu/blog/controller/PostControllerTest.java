package com.sethu.blog.controller;

import com.sethu.blog.dto.PostDTO;
import com.sethu.blog.dto.UserDTO;
import com.sethu.blog.service.PostService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    private UserDTO testUserDTO;

    @BeforeEach
    public void setUp() {
        testUserDTO = new UserDTO(1L, "testUser", "testUser123@", "test@example.com", null, null);
    }

    @Test
    public void testCreatePost() {
        PostDTO postDTO = new PostDTO(1L, "Tittle 1", "Content 1", new Date(), testUserDTO);

        System.out.println(postDTO.toString());
        // Given
        when(postService.createPost(postDTO)).thenReturn(postDTO);

        // When
        ResponseEntity<PostDTO> responseEntity = postController.createPost(postDTO);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(postDTO, responseEntity.getBody());
        verify(postService, times(1)).createPost(postDTO);
    }

    @Test
    public void testGetPostById() {
        // Given
        Long postId = 1L;
        PostDTO postDTO = new PostDTO(postId,"Post 2", "Content 2", new Date(), testUserDTO);
        when(postService.getPostById(postId)).thenReturn(postDTO);

        // When
        ResponseEntity<PostDTO> responseEntity = postController.getPostById(postId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(postDTO, responseEntity.getBody());
        verify(postService, times(1)).getPostById(postId);
    }

    @Test
    public void testGetAllPosts() {
        // Given
        List<PostDTO> postDTOList = Collections.singletonList(new PostDTO());
        when(postService.getAllPosts()).thenReturn(postDTOList);

        // When
        ResponseEntity<List<PostDTO>> responseEntity = postController.getAllPosts();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(postDTOList, responseEntity.getBody());
        verify(postService, times(1)).getAllPosts();
    }

     @Test
    public   void testUpdatePost() {
        // Given
        Long postId = 1L;
        PostDTO postDTO = new PostDTO();
        when(postService.updatePost(postId, postDTO)).thenReturn(postDTO);

        // When
        ResponseEntity<PostDTO> responseEntity = postController.updatePost(postId, postDTO);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(postDTO, responseEntity.getBody());
        verify(postService, times(1)).updatePost(postId, postDTO);
    }

    @Test
    public void testDeletePost() {
        // Given
        Long postId = 1L;

        // When
        ResponseEntity<String> responseEntity = postController.deletePost(postId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Post deleted successful", responseEntity.getBody());
        verify(postService, times(1)).deletePost(postId);
    }
}
