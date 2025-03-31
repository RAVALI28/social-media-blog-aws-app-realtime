package com.spring.learning.social_media_blog_app.Service.Impl;

import com.spring.learning.social_media_blog_app.Entity.RoleEntity;
import com.spring.learning.social_media_blog_app.Entity.UserEntity;
import com.spring.learning.social_media_blog_app.Repository.RoleRepository;
import com.spring.learning.social_media_blog_app.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SocialMediaCustomizedUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with Email or Username::" +usernameOrEmail));

        //Converted User roles to Granted Authorities
        Set<GrantedAuthority> grantedAuthorities = user
                .getRoles().stream().map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).collect(Collectors.toSet());


        //Returing Spring Specific user with username, password and granted authority
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
