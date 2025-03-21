package com.spring.learning.social_media_blog_app.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private Long id;

    //Min 5 characters is needed
    //Should not be null
    @NotEmpty
    @Size(min = 5, message = "Post title should have at least 5 characters", max = 100)
    private String title;

    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;

}
