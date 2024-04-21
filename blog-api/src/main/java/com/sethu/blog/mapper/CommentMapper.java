package com.sethu.blog.mapper;

import com.sethu.blog.dto.CommentDTO;
import com.sethu.blog.entity.Comment;

public class CommentMapper {

    public static CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        // Map the user
        commentDTO.setAuthor(UserMapper.mapToUserDTO(comment.getAuthor()));
        // Map the post
        commentDTO.setPost(PostMapper.mapToPostDTO(comment.getPost()));
        return commentDTO;
    }

    public static Comment mapToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setContent(commentDTO.getContent());
        // Map the user
        comment.setAuthor(UserMapper.mapToUser(commentDTO.getAuthor()));
        // Map the post
        comment.setPost(PostMapper.mapToPost(commentDTO.getPost()));
        return comment;
    }
}
