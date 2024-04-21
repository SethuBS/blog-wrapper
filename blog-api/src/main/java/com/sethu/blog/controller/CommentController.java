package com.sethu.blog.controller;

import com.sethu.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/comment")
public class CommentController {

    private CommentService commentService;
}
