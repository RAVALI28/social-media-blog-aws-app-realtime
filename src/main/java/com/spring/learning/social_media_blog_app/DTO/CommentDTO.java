package com.spring.learning.social_media_blog_app.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDTO {

    private Long id;

    @Size(min = 3, max = 50, message = "Name should have minimum 3 characters and maximum 50 characters ")
    @NotEmpty
    private String name;

    @Email
    @NotNull(message = "Email should not be null or empty")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum of 10 characters")
    private String body;

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
