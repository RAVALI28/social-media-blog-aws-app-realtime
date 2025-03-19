package com.spring.learning.social_media_blog_app.Service;

import com.github.fge.jsonpatch.JsonPatch;
import com.spring.learning.social_media_blog_app.DTO.CommentDTO;
import com.spring.learning.social_media_blog_app.DTO.PatchDTO;

import java.util.*;

public interface CommentService {

    CommentDTO createNewComment(Long postId, CommentDTO commentDTO);

    List<CommentDTO> getAllCommentsByPostId(Long postId);

    CommentDTO getCommentByPostIdAndCommentId(Long postId, Long id);

    CommentDTO updateCommentByPostIdAndId(Long postId, Long id, CommentDTO commentDTO);

    CommentDTO updatePartiallyCommentByPostIdAndCommentId(Long postId, Long id, PatchDTO patchDTO);

    void deleteCommentByPostIdAndCommentId(Long postId, Long id);

    CommentDTO updatePartiallyCommentByPostIdAndCommentIdUsingJsonPatch(Long postId, Long id, JsonPatch jsonPatch);
}
