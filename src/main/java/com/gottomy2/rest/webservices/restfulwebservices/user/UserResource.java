package com.gottomy2.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    private UserDaoService service;

    //Autowiring UserDaoService through a constructor:
    public UserResource(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getUser(@PathVariable Integer id) {
        User usr = service.findOne(id);

        if (usr == null) {
            throw new UserNotFoundException("id:" + id);
        }

        //Adding HATEOAS for REST API:
        EntityModel<User> entityModel = EntityModel.of(usr);
        WebMvcLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).getAllUsers());

        entityModel.add(linkBuilder.withRel("all-users"));

        return entityModel;
    }

    //Returns response with status code 201 - created with location of the new user
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User usr = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usr.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        var deleted = service.deleteById(id);
        if (!deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok("Entity deleted");
        }
    }
}
