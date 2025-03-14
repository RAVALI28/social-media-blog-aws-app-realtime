package com.spring.learning.social_media_blog_app.Service;

import com.spring.learning.social_media_blog_app.DTO.CommentDTO;

import java.util.*;

public interface CommentService {

    CommentDTO createNewComment(long postId, CommentDTO commentDTO);

    List<CommentDTO> getAllCommentsByPostId(long postId);

}
