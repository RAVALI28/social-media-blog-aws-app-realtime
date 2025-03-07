package com.spring.learning.social_media_blog_app.Controller;

import com.spring.learning.social_media_blog_app.DTO.PostDTO;

import com.spring.learning.social_media_blog_app.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        PostDTO savedPostDto = postService.createPost(postDTO);
        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);

    }

    @GetMapping("/getAll")
    public List<PostDTO> getAllPosts(){
       return postService.getAllPosts();
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO postDTO = postService.getPostById(id);
        return ResponseEntity.ok(postDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long id){
        PostDTO updatedPostDTO = postService.updatePost(postDTO, id);
        return ResponseEntity.ok(updatedPostDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post successfully deleted ::" +id);
    }
}
