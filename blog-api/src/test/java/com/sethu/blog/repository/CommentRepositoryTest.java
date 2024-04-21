package com.sethu.blog.repository;

import com.sethu.blog.entity.Comment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentRepositoryTest {

    @Mock
    private CommentRepository commentRepository;

    @Test
    void testFindById() {
        // Given
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setId(commentId);
        Optional<Comment> optionalComment = Optional.of(comment);
        when(commentRepository.findById(commentId)).thenReturn(optionalComment);

        // When
        Optional<Comment> foundCommentOptional = commentRepository.findById(commentId);

        // Then
        assertEquals(optionalComment, foundCommentOptional);
    }
}

