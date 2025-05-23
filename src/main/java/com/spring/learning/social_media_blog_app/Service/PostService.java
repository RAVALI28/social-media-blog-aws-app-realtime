package com.spring.learning.social_media_blog_app.Service;

import com.spring.learning.social_media_blog_app.DTO.PatchDTO;
import com.spring.learning.social_media_blog_app.DTO.PostDTO;
import com.spring.learning.social_media_blog_app.Payload.PostResponse;


import java.util.List;


public interface PostService {

    PostDTO createPost(PostDTO postDTO);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO getPostById(Long id);
    PostDTO updatePost(PostDTO postDTO, Long id);
    void deletePostById(Long id);

    PostDTO partialUpdatePost(long id, PatchDTO patchDTO);
}
