package com.example.security.controller;


import com.example.security.dao.AuthenticationResponse;
import com.example.security.exceptions.AuthenticationRequestException;
import com.example.security.exceptions.AuthenticationResponseException;
import com.example.security.service.AuthenticationService;
import com.example.security.dao.AuthenticationRequest;
import com.example.security.dao.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws AuthenticationRequestException {
        if(request.getEmail().isEmpty() && request.getPassword().isEmpty()
                && request.getFirstName().isEmpty() && request.getLastName().isEmpty())
            throw new AuthenticationRequestException("request is not valid");
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws AuthenticationResponseException {
        if(request.getEmail().isEmpty() && request.getPassword().isEmpty())
            throw new AuthenticationResponseException("request is not valid");
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
