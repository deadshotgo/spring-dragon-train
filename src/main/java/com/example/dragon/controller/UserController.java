package com.example.dragon.controller;

import com.example.dragon.entity.UserEntity;
import com.example.dragon.exeption.UserAlreadyExistException;
import com.example.dragon.exeption.UserNotFoundException;
import com.example.dragon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity registration(@RequestBody UserEntity user){
        try {
            userService.createUser(user);
            return ResponseEntity.ok("User is created");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        try {
          Iterable<UserEntity> users = userService.getUsers();
          return ResponseEntity.ok(users);
        } catch (Exception e) {
           return ResponseEntity.badRequest().body("Помилка");
        }
    };

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            return  ResponseEntity.ok(userService.getUser(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Помилка");
        }
    };

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Помилка");
        }
    };

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Помилка");
        }
    };

}
