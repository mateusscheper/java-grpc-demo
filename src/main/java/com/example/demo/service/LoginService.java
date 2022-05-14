package com.example.demo.service;

import grpc.LoginRequest;
import io.github.majusko.grpc.jwt.service.JwtService;
import io.github.majusko.grpc.jwt.service.dto.JwtData;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class LoginService {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public LoginService(PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(LoginRequest request) {
        String test = "$2a$10$BcAP/rQyVZ3cr1UvCWI1suqJiCGN0wP39eQUCoYc0J4HMXOh6Nmfm"; // the password is 'teste'
        if (passwordEncoder.matches(request.getPassword(), test)) {
            HashSet<String> roles = new HashSet<>();
            roles.add("ADMIN");

            JwtData jwtData = new JwtData("1", roles);
            return jwtService.generate(jwtData);
        }

        return null;
    }
}
