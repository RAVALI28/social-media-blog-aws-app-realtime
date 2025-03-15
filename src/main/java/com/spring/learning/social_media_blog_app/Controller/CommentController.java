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
    public ResponseEntity<CommentDTO> createNewComment(@PathVariable("postId") Long postId, @RequestBody CommentDTO commentDTO){
        CommentDTO commentDTO1 = commentService.createNewComment(postId, commentDTO);
        return new ResponseEntity<>(commentDTO1, HttpStatus.CREATED);
    };

    //Get All posts - /api/v1/posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable("postId") Long postId){
        List<CommentDTO> commentDTOList = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    };

    //Get Comment by Post id and comment id - /api/posts/{postId}/comments/{id}
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentByPostIdAndCommentId(@PathVariable("postId") Long postId, @PathVariable("id") Long id){
       CommentDTO commentDTO = commentService.getCommentByPostIdAndCommentId(postId, id);
       return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    //Update the comment by postId and comment Id - /api/posts/{postId}/comments/{id}
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateCommentByPostIdAndCommentId(@PathVariable("postId") Long postId, @PathVariable("id") Long id, @RequestBody CommentDTO commentDTO){
        CommentDTO commentDTO1 = commentService.updateCommentByPostIdAndId(postId, id, commentDTO);
        return new ResponseEntity<>(commentDTO1, HttpStatus.OK);

    }

    //Update partial part of comment - /api/posts/{postId}/comments/{id}
    @PatchMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updatePartiallyCommentByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long id, @RequestBody CommentDTO commentDTO){
        commentService.updatePartiallyCommentByPostIdAndCommentId(postId, id, commentDTO);
        return null;

    }


    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentByPostIdAndCommentId(@PathVariable("postId") Long postId, @PathVariable("id") Long id){
        commentService.deleteCommentByPostIdAndCommentId(postId, id);
        return ResponseEntity.ok("Comment deleted successfully");
    }

}
