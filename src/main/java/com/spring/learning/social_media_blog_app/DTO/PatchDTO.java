package com.spring.learning.social_media_blog_app.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchDTO {
     private String Operation;
     private String Key;
     private String Value;

}
