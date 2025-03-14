package com.spring.learning.social_media_blog_app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchDTO {

     @JsonProperty("title")
     private String title;

     @JsonProperty("content")
     private String content;

     @JsonProperty("description")
     private String description;

}
