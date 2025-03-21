package com.spring.learning.social_media_blog_app.Controller;

import com.spring.learning.social_media_blog_app.DTO.PatchDTO;
import com.spring.learning.social_media_blog_app.DTO.PostDTO;

import com.spring.learning.social_media_blog_app.Payload.PostResponse;
import com.spring.learning.social_media_blog_app.Service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        PostDTO savedPostDto = postService.createPost(postDTO);
        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);

    }

    //Pagination and Sorting
    @GetMapping("/getAll")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int  pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "id", required = false) String sortDir
    ){
       return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO postDTO = postService.getPostById(id);
        return ResponseEntity.ok(postDTO);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO,
                                              @PathVariable Long id){
        PostDTO updatedPostDTO = postService.updatePost(postDTO, id);
        return ResponseEntity.ok(updatedPostDTO);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> partialUpdatePost(@PathVariable long id,
                                                     @RequestBody PatchDTO patchDTO){
        PostDTO partiallyUpdatedPostDto = postService.partialUpdatePost(id, patchDTO);
        return new ResponseEntity<>(partiallyUpdatedPostDto, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePostById(@PathVariable Long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post successfully deleted ::" +id);
    }
}
