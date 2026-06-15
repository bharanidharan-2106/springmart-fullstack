package com.springmart.authuser.config;

import com.springmart.authuser.entity.User;
import com.springmart.authuser.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin01@gmail.com")) {
            User admin = new User();
            admin.setFirstName("System");
            admin.setLastName("Administrator");
            admin.setEmail("admin01@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin@01"));
            admin.setRole("Admin");
            admin.setUuid(UUID.randomUUID().toString());
            userRepository.save(admin);
            System.out.println("Default Admin user seeded: admin01@gmail.com");
        }
    }
}
