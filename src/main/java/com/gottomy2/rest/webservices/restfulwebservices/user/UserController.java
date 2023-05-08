package com.gottomy2.rest.webservices.restfulwebservices.user;

import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private MessageSource messageSource;
    private UserRepository userRepository;

    //Autowiring UseRepository through a constructor:
    public UserController(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getUser(@PathVariable Integer id) {
        Optional<User> usr = userRepository.findById(id);

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
        User usr = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usr.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        Locale locale = LocaleContextHolder.getLocale();
        return ResponseEntity.ok(messageSource.getMessage("deleted.entity.message", null, "Successfully deleted user: ", locale).toString() + user.get().getName());
    }
}
