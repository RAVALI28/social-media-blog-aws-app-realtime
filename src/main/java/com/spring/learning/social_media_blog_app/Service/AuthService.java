package com.spring.learning.social_media_blog_app.Service;

import com.spring.learning.social_media_blog_app.DTO.LoginDTO;
import com.spring.learning.social_media_blog_app.DTO.RegisterDTO;

public interface AuthService {

    //Login
    String login(LoginDTO loginDTO);

    //Register
    String register(RegisterDTO registerDTO);

}
