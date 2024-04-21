package com.sethu.blog.controller;

import com.sethu.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/post")
public class PostController {

    private PostService postService;

}
