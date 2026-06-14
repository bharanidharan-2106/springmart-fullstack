package com.springmart.authuser.service;

import com.springmart.authuser.dto.AuthDTOs.AuthResponse;
import com.springmart.authuser.dto.AuthDTOs.LoginRequest;
import com.springmart.authuser.dto.AuthDTOs.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}