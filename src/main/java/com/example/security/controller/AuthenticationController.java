package com.example.security.controller;

import com.example.security.dao.AuthenticationRequest;
import com.example.security.dao.AuthenticationResponse;
import com.example.security.dao.User;
import com.example.security.dao.UserEmail;
import com.example.security.email.EmailService;
import com.example.security.entity.UserEntity;
import com.example.security.exceptions.AuthenticationRequestException;
import com.example.security.exceptions.AuthenticationResponseException;
import com.example.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Value("${home.url}")
    private String homeUrl;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ) throws AuthenticationRequestException {
        if (request.getEmail().isEmpty() || request.getPassword().isEmpty()
                || request.getFirstName().isEmpty() || request.getLastName().isEmpty()) {
            throw new AuthenticationRequestException("request is not valid");
        }
        String verificationLink = homeUrl + "/api/v1/auth/verify";
        emailService.sendVerificationEmail(request.getEmail(), verificationLink, request.getFirstName(), request.getLastName());

        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody UserEmail user) throws AuthenticationRequestException {
        if (user.getEmail().isEmpty())
            throw new AuthenticationRequestException("request is not valid");
        authenticationService.findByEmail(user);
        return ResponseEntity.ok("Password reset link sent");
    }

    @PostMapping("/reset/validate")
    public ResponseEntity<String> authenticate(
            @RequestBody String password
    ) throws AuthenticationRequestException {

        authenticationService.validatePassword(password);
        return ResponseEntity.ok("Password validated sucessfully");
    }


    //todo need to add logic here
    @GetMapping("/verify")
    public String isVerified() {
        UserEntity user = new UserEntity();
        user.setVerified(true);
        return "acount verified sucessfully";
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws AuthenticationResponseException {
        if (request.getEmail().isEmpty() || request.getPassword().isEmpty())
            throw new AuthenticationResponseException("request is not valid");
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
