package com.spring.learning.social_media_blog_app.Service;

import com.spring.learning.social_media_blog_app.DTO.CommentDTO;
import com.spring.learning.social_media_blog_app.Entity.Comment;
import com.spring.learning.social_media_blog_app.Entity.Post;
import com.spring.learning.social_media_blog_app.Exception.ResourceNotFoundException;
import com.spring.learning.social_media_blog_app.Repository.CommentRepository;
import com.spring.learning.social_media_blog_app.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImplementation implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDTO createNewComment(long postId, CommentDTO commentDTO) {

        //Map commentDTO to entity
        Comment comment = mapDtoToEntity(commentDTO);
        //Fetch post from POst repo using postId
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        //Set the comment to the post
        comment.setPost(post);
        //Save the comment to DB
        commentRepository.save(comment);
        //Map Comment Entity to DTO
        CommentDTO savedCommentDto = mapEntityToDto(comment);
        return savedCommentDto;
    }

    @Override
    public List<CommentDTO> getAllCommentsByPostId(long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        //Map Entity to DTO
       List<CommentDTO> commentDTOList = commentList.stream().map(comment -> mapEntityToDto(comment)).collect(Collectors.toList());
        return commentDTOList;
    }

    private CommentDTO mapEntityToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(commentDTO.getEmail());
        commentDTO.setBody(commentDTO.getBody());
        return commentDTO;
    }

    private Comment mapDtoToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        return comment;
    }
}
