package com.spring.learning.social_media_blog_app.Service;

import com.spring.learning.social_media_blog_app.DTO.PatchDTO;
import com.spring.learning.social_media_blog_app.DTO.PostDTO;
import com.spring.learning.social_media_blog_app.Entity.Post;
import com.spring.learning.social_media_blog_app.Exception.ResourceNotFoundException;
import com.spring.learning.social_media_blog_app.Payload.PostResponse;
import com.spring.learning.social_media_blog_app.Repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Pageable pageable;
        if(sortBy != null && sortDir != null){

            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            pageable = PageRequest.of(pageNo, pageSize, sort);
        }else {
            pageable = PageRequest.of(pageNo, pageSize);
        }
        //List<Post> allPosts = postRepository.findAll();
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();

        //Map Post Entity to Post DTO
       //List<PostDTO> postDTOList = allPosts.stream().map(post -> mapEntityToDTO(post)).collect(Collectors.toList());
        List<PostDTO> postDTOList = postList.stream().map(post -> mapEntityToDTO(post)).collect(Collectors.toList());

        //Customize the Post Resource Response
        PostResponse postResponse = PostResponse.builder()
                .content(postDTOList)
                .pageNo(posts.getNumber())
                .pageSize(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .isLastPage(posts.isLast())
                .build();

       return postResponse;

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
    public PostDTO partialUpdatePost(long id, PatchDTO patchDTO) {
        //Fetch post from post repository using id
        Post postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));

        partiallyUpdatePostEntity(patchDTO, postEntity);

        Post partiallyUpdatedPostEntity = postRepository.save(postEntity);

        //Map Post Entity to DTO
        PostDTO partiallyUpdatedPostDTO = mapEntityToDTO(partiallyUpdatedPostEntity);
        return partiallyUpdatedPostDTO;
    }




    @Override
    public void deletePostById(Long id) {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", String.valueOf(id)));
        postRepository.delete(existingPost);
    }




    //Common methods to map from Entity to DTO and DTO to Entity
    private Post mapDTOToEntity(PostDTO postDTO) {
        //Using Model Maper to map one object to another
        Post post = modelMapper.map(postDTO, Post.class);

//        Post post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());
        return post;

    }



    private PostDTO mapEntityToDTO(Post post) {

        //Using Model Maper to map one object to another
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);

//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());
        return postDTO;
    }


    private void partiallyUpdatePostEntity(PatchDTO patchDTO, Post post) {

        String key = patchDTO.getKey();
        switch (key) {
            case "Title" :
                post.setTitle(patchDTO.getValue());
                break;
            case "Content" :
                post.setContent(patchDTO.getValue());
                break;
            case "Description" :
                post.setDescription(patchDTO.getValue());
                break;
        }
    }




}
