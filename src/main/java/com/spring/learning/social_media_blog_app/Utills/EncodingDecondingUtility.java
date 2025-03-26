package com.spring.learning.social_media_blog_app.Utills;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodingDecondingUtility {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Ravali:" +passwordEncoder.encode("ravali28"));
        System.out.println("Admin :" +passwordEncoder.encode("admin"));
    }

}
