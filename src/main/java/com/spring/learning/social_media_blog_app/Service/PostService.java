package com.spring.learning.social_media_blog_app.Service;

import com.spring.learning.social_media_blog_app.DTO.PostDTO;


import java.util.List;


public interface PostService {

    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long id);
    PostDTO updatePost(PostDTO postDTO, Long id);
    void deletePostById(Long id);

}
