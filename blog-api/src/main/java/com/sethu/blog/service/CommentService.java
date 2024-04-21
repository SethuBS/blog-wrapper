package com.sethu.blog.service;

import com.sethu.blog.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CommentDTO newComment);
    CommentDTO updateComment(Long commentId, CommentDTO updateComment);
    CommentDTO findCommentById(Long commentId);
    List<CommentDTO> getAllComments();
    void deleteComment(Long commentId);
}
