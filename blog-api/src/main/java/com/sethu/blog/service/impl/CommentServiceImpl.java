package com.sethu.blog.service.impl;

import com.sethu.blog.dto.CommentDTO;
import com.sethu.blog.entity.Comment;
import com.sethu.blog.exception.ResourceNotFoundException;
import com.sethu.blog.mapper.Mapper;
import com.sethu.blog.repository.CommentRepository;
import com.sethu.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    @Override
    public CommentDTO createComment(CommentDTO newComment) {
        Comment commentToSave = Mapper.mapToComment(newComment);
        Comment savedComment = commentRepository.save(commentToSave);
        return Mapper.mapToCommentDTO(savedComment);
    }

    @Override
    public CommentDTO updateComment(Long commentId, CommentDTO updateComment) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with " + commentId + " does not exist"));
        comment.setContent(updateComment.getContent());
        comment.setCreatedDate(updateComment.getCreatedDate());
        commentRepository.save(comment);
        return Mapper.mapToCommentDTO(comment);
    }

    @Override
    public CommentDTO findCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment with given id: " + commentId + " does not exist"));
        return Mapper.mapToCommentDTO(comment);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll()
                .stream().map(Mapper::mapToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with given id: " + commentId + " does not exist"));
        commentRepository.deleteById(commentId);
    }
}
