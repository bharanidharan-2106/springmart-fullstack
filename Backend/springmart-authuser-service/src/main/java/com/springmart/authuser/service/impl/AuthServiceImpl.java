package com.springmart.authuser.service.impl;

import com.springmart.authuser.dto.AuthDTOs.AuthResponse;
import com.springmart.authuser.dto.AuthDTOs.LoginRequest;
import com.springmart.authuser.dto.AuthDTOs.RegisterRequest;
import com.springmart.authuser.entity.User;
import com.springmart.authuser.repository.UserRepository;
import com.springmart.authuser.security.JwtUtil;
import com.springmart.authuser.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "Customer");
        user.setUuid(UUID.randomUUID().toString());
        // Set status: Merchants start as PENDING, others are APPROVED
        if ("Merchant".equals(user.getRole())) {
            user.setStatus("PENDING");
        } else {
            user.setStatus("APPROVED");
        }

        User savedUser = userRepository.save(user);

        String token = null;
        if (!("Merchant".equals(savedUser.getRole()) && "PENDING".equals(savedUser.getStatus()))) {
            token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole());
        }

        return new AuthResponse(
                token,
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getUuid(),
                savedUser.getStatus()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        // Check if merchant is approved
        if ("Merchant".equals(user.getRole()) && !"APPROVED".equals(user.getStatus())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Merchant account is pending approval");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getUuid(),
                user.getStatus()
        );
    }
}