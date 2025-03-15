package com.spring.learning.social_media_blog_app.Service;

import com.spring.learning.social_media_blog_app.DTO.CommentDTO;

import java.util.*;

public interface CommentService {

    CommentDTO createNewComment(Long postId, CommentDTO commentDTO);

    List<CommentDTO> getAllCommentsByPostId(Long postId);

    CommentDTO getCommentByPostIdAndCommentId(Long postId, Long id);

    CommentDTO updateCommentByPostIdAndId(Long postId, Long id, CommentDTO commentDTO);

    CommentDTO updatePartiallyCommentByPostIdAndCommentId(Long postId, Long id, CommentDTO commentDTO);

    void deleteCommentByPostIdAndCommentId(Long postId, Long id);
}
