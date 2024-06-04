package com.example.dragon.controller;

import com.example.dragon.dto.user.RequestUser;
import com.example.dragon.dto.user.ResponseUser;
import com.example.dragon.exception.UserAlreadyExistException;
import com.example.dragon.service.UserService;
import com.example.dragon.swagger.GenerateApiDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "Endpoints for user management")
public class UserController {

    @Autowired
    private UserService userService;


    @GenerateApiDoc(
            summary = "Register a new user",
            description = "Register a new user with the provided data",
            responseDescription = "User registered successfully",
            responseClass = ResponseUser.class
    )
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseUser> registration(@Valid @RequestBody RequestUser user) throws UserAlreadyExistException {
        ResponseUser createdResponseUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponseUser);
    }


    @GenerateApiDoc(
            summary = "Get all users",
            description = "Retrieve a list of all registered users",
            responseDescription = "List of users retrieved successfully",
            responseClass = ResponseUser.class
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ResponseUser>> getUsers() {
      List<ResponseUser> users = userService.getUsers();
      return ResponseEntity.ok(users);
    }


    @GenerateApiDoc(
            summary = "Get user by ID",
            description = "Retrieve a user by its unique identifier",
            responseDescription = "User retrieved successfully by ID",
            responseClass = ResponseUser.class
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable Long id) {
        return  ResponseEntity.ok(userService.getUser(id));
    }


    @GenerateApiDoc(
            summary = "Delete user by ID",
            description = "Delete a user by its unique identifier",
            responseDescription = "User deleted successfully",
            responseClass = Long.class
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
         return ResponseEntity.ok(userService.deleteUser(id));
    }

}
