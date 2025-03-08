package com.spring.learning.social_media_blog_app.Service;

import com.spring.learning.social_media_blog_app.DTO.PostDTO;
import com.spring.learning.social_media_blog_app.Entity.Post;
import com.spring.learning.social_media_blog_app.Exception.ResourceNotFoundException;
import com.spring.learning.social_media_blog_app.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        //Map DTO to Entity
        Post post = mapDTOToEntity(postDTO);


        //Save to DB
        Post savedPost = postRepository.save(post);

        //Then Map Entity to DTO;
        PostDTO savedPostDTO = mapEntityToDTO(post);
        return savedPostDTO;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> allPosts = postRepository.findAll();

        //Map Post Entity to Post DTO
       List<PostDTO> postDTOList = allPosts.stream().map(post -> mapEntityToDTO(post)).collect(Collectors.toList());
       return postDTOList;

    }

    @Override
    public PostDTO getPostById(Long id) {
       Post postById = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(id)));
      PostDTO postDTOById = mapEntityToDTO(postById);
      return postDTOById;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post" , "id", String.valueOf(id)));
        existingPost.setTitle(postDTO.getTitle());
        existingPost.setDescription(postDTO.getDescription());
        existingPost.setContent(postDTO.getContent());
        postRepository.save(existingPost);
        PostDTO updatedPostdto = mapEntityToDTO(existingPost);
        return updatedPostdto;
    }

    @Override
    public void deletePostById(Long id) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(id)));
        postRepository.delete(existingPost);
    }


    //Common methods to map from Entity to DTO and DTO to Entity
    private Post mapDTOToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return post;

    }



    private PostDTO mapEntityToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        return postDTO;
    }




}
