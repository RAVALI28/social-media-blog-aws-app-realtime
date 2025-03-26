package com.spring.learning.social_media_blog_app.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.spring.learning.social_media_blog_app.DTO.CommentDTO;
import com.spring.learning.social_media_blog_app.DTO.PatchDTO;
import com.spring.learning.social_media_blog_app.Entity.Comment;
import com.spring.learning.social_media_blog_app.Entity.Post;
import com.spring.learning.social_media_blog_app.Exception.BlogAPIException;
import com.spring.learning.social_media_blog_app.Exception.ResourceNotFoundException;
import com.spring.learning.social_media_blog_app.Repository.CommentRepository;
import com.spring.learning.social_media_blog_app.Repository.PostRepository;
import com.spring.learning.social_media_blog_app.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImplementation implements CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

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

        //Fetch Post by PostId
        Post postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        //Fetch Comment by CommentId
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(id)));

        // validate comment belongs to that Particular Post
        if (!commentEntity.getPost().getId().equals(postEntity.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bad Request Comment Not Found in Post");
        }

        // Map Comment Entity to Comment DTO
        CommentDTO commentDto = mapEntityToDto(commentEntity);
        return commentDto;

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
    public CommentDTO updatePartiallyCommentByPostIdAndCommentId(Long postId, Long id, PatchDTO patchDTO) {
        //Fetch Post from Post Repository using postId
        Post postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        //Fetch Comment from Comment Repository using id
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("comment", "id", String.valueOf(id)));

        //Validate Comment belongs to that particular post
        if(!commentEntity.getPost().getId().equals(postEntity.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bad request not  found in Post");
        }

        partiallyUpdateCommentEntity(patchDTO, commentEntity);

        Comment updatedCommentEntity = commentRepository.save(commentEntity);

        //Map Comment Entity to Comment DTO
        CommentDTO updatedCommentDTO = mapEntityToDto(updatedCommentEntity);
        return updatedCommentDTO;
    }

    @Override
    public CommentDTO updatePartiallyCommentByPostIdAndCommentIdUsingJsonPatch(Long postId, Long id, JsonPatch jsonPatch) {
        //Fetch Post from Post Repository using postId
        Post postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));

        //Fetch Comment from Comment Repository using id
        Comment commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("comment", "id", String.valueOf(id)));

        //Validate Comment belongs to that particular post
        if(!commentEntity.getPost().getId().equals(postEntity.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bad request not  found in Post");
        }

        CommentDTO commentDTO = mapEntityToDto(commentEntity);

        try {
            commentDTO = applyPatchToComment(jsonPatch, commentDTO);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Comment updatedcommentEntity = mapDtoToEntity(commentDTO);
        updatedcommentEntity.setId(commentEntity.getId());
        updatedcommentEntity.setPost(postEntity);
        commentRepository.save(updatedcommentEntity);
        return commentDTO;
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
        //Using Model Mapper to map one object to another
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());

        return commentDTO;
    }

    private Comment mapDtoToEntity(CommentDTO commentDTO) {
        //Using Model Mapper to map one object to another
        Comment comment = modelMapper.map(commentDTO, Comment.class);

//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        return comment;
    }

    private void partiallyUpdateCommentEntity(PatchDTO patchDTO, Comment commentEntity) {

        String key = patchDTO.getKey();
        switch (key){
            case "Email" :
                commentEntity.setEmail(patchDTO.getValue());
                break;
            case "Body" :
                commentEntity.setBody(patchDTO.getValue());
                break;
            case "Name" :
                commentEntity.setName(patchDTO.getValue());
                break;

        }
    }

    private CommentDTO applyPatchToComment(
            JsonPatch patch, CommentDTO commentDTO) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(commentDTO, JsonNode.class));
        return objectMapper.treeToValue(patched, CommentDTO.class);

    }

}
