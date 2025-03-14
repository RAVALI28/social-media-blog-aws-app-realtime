package com.spring.learning.social_media_blog_app.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private long id;

    private String name;
    private String email;
    private String body;
}
