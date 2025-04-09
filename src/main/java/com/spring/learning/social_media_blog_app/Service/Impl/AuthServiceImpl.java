package com.spring.learning.social_media_blog_app.Service.Impl;


import com.spring.learning.social_media_blog_app.DTO.LoginDTO;
import com.spring.learning.social_media_blog_app.DTO.RegisterDTO;
import com.spring.learning.social_media_blog_app.Entity.RoleEntity;
import com.spring.learning.social_media_blog_app.Entity.UserEntity;
import com.spring.learning.social_media_blog_app.Exception.BlogAPIException;
import com.spring.learning.social_media_blog_app.Repository.RoleRepository;
import com.spring.learning.social_media_blog_app.Repository.UserRepository;
import com.spring.learning.social_media_blog_app.Security.JwtTokenUtility;
import com.spring.learning.social_media_blog_app.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Override
    public String login(LoginDTO loginDTO) {
        //Authenticate the User using Authentication Manager
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        //Set the Authentication object into Security Context Holder
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwtToken = jwtTokenUtility.generateJwtToken(authentication);

        return jwtToken;
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        //Check whether username already exists in DB
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already Registered");
        }
        //Check whether email already exists in DB
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        //If it is not in DB, Map RegsterDTO to User and create new User Entity
        UserEntity user = new UserEntity();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));


        //Map the Roles to the User
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        //Save the User into DB
        userRepository.save(user);

        return "User Registered Successfully";
    }
}
