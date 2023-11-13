package com.example.security.controller;

import com.example.security.dao.AuthenticationResponse;
import com.example.security.email.EmailService;
import com.example.security.entity.UserEntity;
import com.example.security.exceptions.AuthenticationRequestException;
import com.example.security.exceptions.AuthenticationResponseException;
import com.example.security.service.AuthenticationService;
import com.example.security.dao.AuthenticationRequest;
import com.example.security.dao.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    @Value("${home.url}")
    private String homeUrl;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ) throws AuthenticationRequestException {
        if(request.getEmail().isEmpty() || request.getPassword().isEmpty()
                || request.getFirstName().isEmpty() || request.getLastName().isEmpty()) {
            throw new AuthenticationRequestException("request is not valid");
        }
        String verificationLink = homeUrl+"/api/v1/auth/verify";
        emailService.sendVerificationEmail(request.getEmail(), verificationLink, request.getFirstName(), request.getLastName());

        return ResponseEntity.ok(authenticationService.register(request));
    }

    //todo need to add logic here
    @GetMapping("/verify")
    public String isVerified(){
        UserEntity user = new UserEntity();
        user.setVerified(true);
        return "acount verified sucessfully";
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws AuthenticationResponseException {
        if(request.getEmail().isEmpty() || request.getPassword().isEmpty())
            throw new AuthenticationResponseException("request is not valid");
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
