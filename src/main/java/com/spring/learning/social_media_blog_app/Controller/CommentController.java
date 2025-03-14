package com.spring.learning.social_media_blog_app.Controller;

import com.spring.learning.social_media_blog_app.DTO.CommentDTO;
import com.spring.learning.social_media_blog_app.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //create new comment - /api/posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createNewComment(@PathVariable("postId") long postId, @RequestBody CommentDTO commentDTO){
        CommentDTO commentDTO1 = commentService.createNewComment(postId, commentDTO);
        return new ResponseEntity<>(commentDTO1, HttpStatus.CREATED);
    };

    //Get All posts - /api/v1/posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable("postId") long postId){
        List<CommentDTO> commentDTOList = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    };

}
