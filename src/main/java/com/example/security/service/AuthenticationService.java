package com.example.security.service;

import com.example.security.config.JwtService;
import com.example.security.dao.AuthenticationRequest;
import com.example.security.dao.AuthenticationResponse;
import com.example.security.dao.User;
import com.example.security.dao.UserEmail;
import com.example.security.email.ResetPasswordEmailService;
import com.example.security.exceptions.AuthenticationRequestException;
import com.example.security.exceptions.UserAlreadyExistsException;
import com.example.security.generator.ResetPasswordCode;
import com.example.security.mapper.UserEntityMapper;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserEntityMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ResetPasswordEmailService resetPasswordEmailService;
    private final String generatedPassword = String.valueOf(ResetPasswordCode.generate());

    public AuthenticationResponse register(User request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already registered");
        }
        var user = mapper.createUserFromRequest(request);
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("user with email not found"));

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public void findByEmail(UserEmail email) {
        if (userRepository.findByEmail(email.getEmail()).isEmpty()) {
            throw new UsernameNotFoundException("user with email not found");
        }
        resetPasswordEmailService.sendRestPassword(email.getEmail(), generatedPassword);

    }

    //todo need to add logic here
    public void validatePassword(String password) throws AuthenticationRequestException {
        if (password == null || !password.matches(generatedPassword)) {
            throw new AuthenticationRequestException("Invalid password");
        }
    }
}
