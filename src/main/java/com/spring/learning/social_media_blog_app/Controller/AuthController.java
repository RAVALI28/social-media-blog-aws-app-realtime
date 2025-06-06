package com.spring.learning.social_media_blog_app.Controller;

import com.spring.learning.social_media_blog_app.DTO.JWTAuthResponse;
import com.spring.learning.social_media_blog_app.DTO.LoginDTO;
import com.spring.learning.social_media_blog_app.DTO.RegisterDTO;
import com.spring.learning.social_media_blog_app.Service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "SOCIAL MEDIA BLOG APP AUTHENTICATION RESOURCE REST CRUD APIs"
)
public class AuthController {

    @Autowired
    private AuthService authService;

    //HTTP POST - "/login", "/signin"
    @PostMapping(value = { "/signin", "/login" })
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO){
        String token = authService.login(loginDTO);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);

    }

    //HTTP POST - "/register", "/signup"
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
       String response = authService.register(registerDTO);
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
