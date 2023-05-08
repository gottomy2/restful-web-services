package com.gottomy2.rest.webservices.restfulwebservices.post;

import com.gottomy2.rest.webservices.restfulwebservices.user.User;
import com.gottomy2.rest.webservices.restfulwebservices.user.UserNotFoundException;
import com.gottomy2.rest.webservices.restfulwebservices.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
class PostController {

    private UserRepository userRepository;

    private PostRepository postRepository;

    public PostController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        return user.get().getPosts();
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public Post retrieveSinglePost(@PathVariable Integer userId, @PathVariable Integer postId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + userId);
        }

        Optional<Post> foundPost = postRepository.findByUserIdAndId(userId, postId);
        if (foundPost.isEmpty()) {
            throw new PostNotFoundException("id: " + postId);
        }

        return foundPost.get();
    }


    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @RequestBody Post post) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new UserNotFoundException("id: " + id);

        post.setUser(user.get());

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{userId}/posts/{postId}")
    public ResponseEntity<Object> deletePostForUser(@PathVariable Integer userId, @PathVariable Integer postId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new UserNotFoundException("id: " + userId);

        Optional<Post> foundPost = postRepository.findByUserIdAndId(userId, postId);
        if (foundPost.isEmpty())
            throw new PostNotFoundException("id: " + postId);

        postRepository.delete(foundPost.get());

        return ResponseEntity.ok("Successfully deleted post for user: " + user.get().getName());
    }
}
