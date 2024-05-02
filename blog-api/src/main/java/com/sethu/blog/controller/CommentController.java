package com.sethu.blog.controller;

import com.sethu.blog.dto.CommentDTO;
import com.sethu.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@Validated @RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok(commentService.createComment(commentDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<CommentDTO> updateComment(@Validated @PathVariable("id") Long commentId,
                                                    @RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok(commentService.updateComment(commentId,commentDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<CommentDTO> getCommentById(@Validated @PathVariable("id") Long commentId){
        return ResponseEntity.ok(commentService.findCommentById(commentId));
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments(){
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteComment(@Validated @PathVariable("id") Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successful");
    }
}
