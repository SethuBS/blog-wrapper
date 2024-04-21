package com.sethu.blog.controller;

import com.sethu.blog.dto.CommentDTO;
import com.sethu.blog.service.CommentService;
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
class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @Test
    void testCreateComment() {
        // Given
        CommentDTO commentDTO = new CommentDTO();
        when(commentService.createComment(commentDTO)).thenReturn(commentDTO);

        // When
        ResponseEntity<CommentDTO> responseEntity = commentController.createComment(commentDTO);

        // Then
        assertEquals(ResponseEntity.ok(commentDTO), responseEntity);
    }

    @Test
    void testUpdateComment() {
        // Given
        Long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        when(commentService.updateComment(commentId, commentDTO)).thenReturn(commentDTO);

        // When
        ResponseEntity<CommentDTO> responseEntity = commentController.updateComment(commentId, commentDTO);

        // Then
        assertEquals(ResponseEntity.ok(commentDTO), responseEntity);
    }

    @Test
    void testGetCommentById() {
        // Given
        Long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO();
        when(commentService.findCommentById(commentId)).thenReturn(commentDTO);

        // When
        ResponseEntity<CommentDTO> responseEntity = commentController.getCommentById(commentId);

        // Then
        assertEquals(ResponseEntity.ok(commentDTO), responseEntity);
    }

    @Test
    void testGetAllComments() {
        // Given
        List<CommentDTO> commentDTOList = Arrays.asList(new CommentDTO(), new CommentDTO());
        when(commentService.getAllComments()).thenReturn(commentDTOList);

        // When
        ResponseEntity<List<CommentDTO>> responseEntity = commentController.getAllComments();

        // Then
        assertEquals(ResponseEntity.ok(commentDTOList), responseEntity);
    }

    @Test
    void testDeleteComment() {
        // Given
        Long commentId = 1L;

        // When
        ResponseEntity<String> responseEntity = commentController.deleteComment(commentId);

        // Then
        verify(commentService, times(1)).deleteComment(commentId);
        assertEquals(ResponseEntity.ok("Comment deleted successful"), responseEntity);
    }
}

