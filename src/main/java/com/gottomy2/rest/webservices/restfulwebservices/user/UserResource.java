package com.gottomy2.rest.webservices.restfulwebservices.user;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {

    private UserRepository repository;

    //Autowiring UseRepository through a constructor:
    public UserResource(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getUser(@PathVariable Integer id) {
        Optional<User> usr = repository.findById(id);

        if (usr.isEmpty()) {
            throw new UserNotFoundException("id:" + id);
        }

        //Adding HATEOAS for REST API:
        EntityModel<User> entityModel = EntityModel.of(usr.get());
        WebMvcLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).getAllUsers());
        entityModel.add(linkBuilder.withRel("all-users"));

        return entityModel;
    }

    //Returns response with status code 201 - created with location of the new user
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User usr = repository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usr.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok("Entity deleted");
    }
}
