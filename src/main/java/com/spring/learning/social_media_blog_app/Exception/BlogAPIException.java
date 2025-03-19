package com.spring.learning.social_media_blog_app.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BlogAPIException extends RuntimeException{

    private HttpStatus status;
    private String message;

}
