package com.spring.learning.social_media_blog_app.Repository;

import com.spring.learning.social_media_blog_app.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}
