package com.spring.learning.social_media_blog_app.Service;

import com.spring.learning.social_media_blog_app.DTO.CommentDTO;
import com.spring.learning.social_media_blog_app.Entity.Comment;
import com.spring.learning.social_media_blog_app.Entity.Post;
import com.spring.learning.social_media_blog_app.Exception.ResourceNotFoundException;
import com.spring.learning.social_media_blog_app.Repository.CommentRepository;
import com.spring.learning.social_media_blog_app.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImplementation implements CommentService{


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDTO createNewComment(Long postId, CommentDTO commentDTO) {

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
    public List<CommentDTO> getAllCommentsByPostId(Long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        //Map Entity to DTO
       List<CommentDTO> commentDTOList = commentList.stream().map(comment -> mapEntityToDto(comment)).collect(Collectors.toList());
        return commentDTOList;
    }

        @Override
        public CommentDTO getCommentByPostIdAndCommentId(Long postId, Long id) {

        Comment comment = commentRepository.findCommentByPostIdAndId(postId, id);
        CommentDTO commentDTO = mapEntityToDto(comment);

            return commentDTO;
        }

    @Override
    public CommentDTO updateCommentByPostIdAndId(Long postId, Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findCommentByPostIdAndId(postId, id);
        if(comment == null) {
            throw new ResourceNotFoundException("Comment", "id", String.valueOf(id));
        }
        System.out.println("comment: " +comment.toString());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        commentRepository.save(comment);
        CommentDTO commentDTO1 = mapEntityToDto(comment);
        return commentDTO1;
    }

    @Override
    public CommentDTO updatePartiallyCommentByPostIdAndCommentId(Long postId, Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findCommentByPostIdAndId(postId, id);

        if(comment == null) {
            throw new ResourceNotFoundException("Comment", "id", String.valueOf(id));
        }


        if(commentDTO.getName() != null && !commentDTO.getName().isEmpty()){
            comment.setName(commentDTO.getName());
        }
        if(commentDTO.getEmail() != null && !commentDTO.getEmail().isEmpty()){
            comment.setEmail(commentDTO.getEmail());
        }
        if(commentDTO.getBody() != null && !commentDTO.getBody().isEmpty()){
            comment.setBody(commentDTO.getBody());
        }

        commentRepository.save(comment);
        CommentDTO commentDTO1 = mapEntityToDto(comment);
        return commentDTO1;
    }

    @Override
    public void deleteCommentByPostIdAndCommentId(Long postId, Long id) {
        Comment comment = commentRepository.findCommentByPostIdAndId(postId, id);
        if(comment == null) {
            throw new ResourceNotFoundException("Comment", "id", String.valueOf(id));
        }
        commentRepository.delete(comment);
    }

    private CommentDTO mapEntityToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());

        return commentDTO;
    }

    private Comment mapDtoToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        return comment;
    }
}
