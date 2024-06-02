package com.example.dragon.controller;

import com.example.dragon.dto.user.RequestUser;
import com.example.dragon.dto.user.ResponseUser;
import com.example.dragon.entity.UserEntity;
import com.example.dragon.exception.UserAlreadyExistException;
import com.example.dragon.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseUser> registration(@Valid @RequestBody RequestUser user) throws UserAlreadyExistException {
        ResponseUser createdResponseUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponseUser);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ResponseUser>> getUsers() {
      List<ResponseUser> users = userService.getUsers();
      return ResponseEntity.ok(users);
    };

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseUser> getUser(@PathVariable Long id) {
        return  ResponseEntity.ok(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
         return ResponseEntity.ok(userService.deleteUser(id));
    };

}
