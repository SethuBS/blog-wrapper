package com.sethu.blog.repository;

import com.sethu.blog.entity.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostRepositoryTest {

    @Mock
    private PostRepository postRepository;

    @Test
    void testFindById() {
        // Given
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        Optional<Post> optionalPost = Optional.of(post);
        when(postRepository.findById(postId)).thenReturn(optionalPost);

        // When
        Optional<Post> foundPostOptional = postRepository.findById(postId);

        // Then
        assertEquals(optionalPost, foundPostOptional);
    }
}

