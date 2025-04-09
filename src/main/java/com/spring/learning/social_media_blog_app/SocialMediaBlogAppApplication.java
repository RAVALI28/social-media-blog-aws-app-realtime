package com.spring.learning.social_media_blog_app;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Social Media Blog Application",
				description = "Spring Boot Social Media Blog Application REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Ravalika",
						email = "ravalika@gmail.com",
						url = "https://github.com/RAVALI28/social-media-blog-aws-app-realtime"
				),
				license = @License(
						name = "Apache 1.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Social Media Blog App Documentation",
				url = "http://localhost:8080/swagger-ui/index.html#/"


))
public class SocialMediaBlogAppApplication {



	public static void main(String[] args) {
		SpringApplication.	run(SocialMediaBlogAppApplication.class, args);
	}

}
