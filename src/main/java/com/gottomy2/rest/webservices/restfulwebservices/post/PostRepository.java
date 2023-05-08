package com.gottomy2.rest.webservices.restfulwebservices.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findByUserIdAndId(Integer userId, Integer id);
}
