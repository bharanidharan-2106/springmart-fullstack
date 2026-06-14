package com.springmart.authuser.controller;

import com.springmart.authuser.dto.AuthDTOs.AuthResponse;
import com.springmart.authuser.dto.AuthDTOs.LoginRequest;
import com.springmart.authuser.dto.AuthDTOs.RegisterRequest;
import com.springmart.authuser.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}