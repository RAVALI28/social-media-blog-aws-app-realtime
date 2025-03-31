package com.spring.learning.social_media_blog_app.Controller;

import com.spring.learning.social_media_blog_app.DTO.LoginDTO;
import com.spring.learning.social_media_blog_app.DTO.RegisterDTO;
import com.spring.learning.social_media_blog_app.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //HTTP POST - "/login", "/signin"
    @PostMapping(value = { "/signin", "/login" })
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        String response = authService.login(loginDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //HTTP POST - "/register", "/signup"
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
       String response = authService.register(registerDTO);
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
