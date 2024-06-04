package com.example.dragon.controller;

import com.example.dragon.dto.auth.ResponseAuth;
import com.example.dragon.dto.auth.RequestAuth;
import com.example.dragon.dto.user.RequestUser;
import com.example.dragon.dto.user.ResponseUser;
import com.example.dragon.exception.UserAlreadyExistException;
import com.example.dragon.service.JwtService;
import com.example.dragon.service.UserService;
import com.example.dragon.swagger.GenerateApiDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @GenerateApiDoc(
            summary = "Generate JWT",
            description = "Authenticate user and return JWT token",
            responseCode = "200",
            responseDescription = "Token successfully generated",
            responseClass = ResponseAuth.class
    )
    @PostMapping("/generate-token")
    public ResponseEntity<ResponseAuth> authenticateAndGetToken(@Valid @RequestBody RequestAuth requestAuth) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestAuth.getUsername(), requestAuth.getPassword()));
        if (authentication.isAuthenticated()) {
            ResponseAuth response = new ResponseAuth(jwtService.generateToken(requestAuth.getUsername()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @GenerateApiDoc(
            summary = "Register new user",
            description = "Register new user and generate JWT token",
            responseDescription = "User created and token generated successfully",
            responseClass = ResponseAuth.class
    )
    @PostMapping("/create-user")
    public ResponseEntity<ResponseAuth> registrationUser(@Valid @RequestBody RequestUser requestUser) throws UserAlreadyExistException {
        ResponseUser responseUser = userService.createUserHasAuth(requestUser);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestUser.getUsername(), requestUser.getPassword()));
        if (authentication.isAuthenticated()){
            ResponseAuth response = new ResponseAuth(jwtService.generateToken(responseUser.getUsername()), responseUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
}
