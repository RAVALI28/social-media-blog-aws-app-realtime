package com.spring.learning.social_media_blog_app.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//Creating different configuration class for ModelMapper is the best practice
//instead creating the modelmapper bean in the main Springboot class or in the security config class
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
