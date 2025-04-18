package com.spring.learning.social_media_blog_app.Repository;

import com.spring.learning.social_media_blog_app.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
