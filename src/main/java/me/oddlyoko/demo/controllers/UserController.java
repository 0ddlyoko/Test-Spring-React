package me.oddlyoko.demo.controllers;

import me.oddlyoko.demo.DemoApplication;
import me.oddlyoko.demo.exceptions.ResourceNotFoundException;
import me.oddlyoko.demo.models.User;
import me.oddlyoko.demo.payloads.requests.user.UserRequest;
import me.oddlyoko.demo.payloads.requests.user.UserUpdateRequest;
import me.oddlyoko.demo.payloads.responses.EmptyResponse;
import me.oddlyoko.demo.payloads.responses.PagedResponse;
import me.oddlyoko.demo.payloads.responses.UserResponse;
import me.oddlyoko.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<UserResponse>> getAllUsers(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = DemoApplication.DEFAULT_PAGE_SIZE) int size) {
        Page<UserResponse> users = userService.getAllUsers(page, size).map(UserResponse::fromModel);
        PagedResponse<UserResponse> response = new PagedResponse<>(users.getContent(), page, users.getNumberOfElements(), users.getTotalElements(), users.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") long id) throws ResourceNotFoundException {
        User user = userService.getUser(id).orElseThrow(() -> new ResourceNotFoundException("User " + id + " does not exist"));
        return new ResponseEntity<>(UserResponse.fromModel(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest userRequest) {
        User user = userService.addUser(new User(userRequest.email(), userRequest.name()));
        return new ResponseEntity<>(UserResponse.fromModel(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable("id") long id,
            @Valid @RequestBody UserUpdateRequest userRequest) throws ResourceNotFoundException {
        User user = userService.getUser(id).orElseThrow(() -> new ResourceNotFoundException("User " + id + " does not exist"));
        user.setName(userRequest.name());
        user = userService.updateUser(user);
        return new ResponseEntity<>(UserResponse.fromModel(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmptyResponse> deleteUser(@PathVariable(name = "id") long id) {
        if (userService.deleteUser(id))
            return new ResponseEntity<>(new EmptyResponse(), HttpStatus.NO_CONTENT);
        throw new ResourceNotFoundException("User " + id + " does not exist");
    }
}
