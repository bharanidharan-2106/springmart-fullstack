package com.springmart.authuser.controller;

import com.springmart.authuser.dto.AuthDTOs.DashboardStatsResponse;
import com.springmart.authuser.dto.AuthDTOs.UpdateUserStatusRequest;
import com.springmart.authuser.dto.AuthDTOs.UpdateProfileRequest;
import com.springmart.authuser.dto.AuthDTOs.AddressDTO;
import com.springmart.authuser.dto.AuthDTOs.UserResponse;
import com.springmart.authuser.entity.User;
import com.springmart.authuser.entity.UserAddress;
import com.springmart.authuser.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/merchants")
    public List<UserResponse> getMerchants(@RequestParam(required = false) String status) {
        List<User> merchants;
        if (status != null) {
            merchants = userRepository.findByRoleAndStatus("Merchant", status);
        } else {
            merchants = userRepository.findByRole("Merchant");
        }
        return merchants.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    @PutMapping("/{uuid}/status")
    public UserResponse updateUserStatus(@PathVariable String uuid, @RequestBody UpdateUserStatusRequest request) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setStatus(request.getStatus());
        User savedUser = userRepository.save(user);
        return toUserResponse(savedUser);
    }

    @GetMapping("/{uuid}")
    public UserResponse getUserByUuid(@PathVariable String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return toUserResponse(user);
    }

    @PutMapping("/{uuid}/profile")
    public UserResponse updateProfile(@PathVariable String uuid, @RequestBody UpdateProfileRequest request) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        if (request.getAddress() != null) {
            UserAddress address = user.getAddress();
            if (address == null) {
                address = new UserAddress();
            }
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setCountry(request.getAddress().getCountry());
            address.setZipCode(request.getAddress().getZipCode());
            user.setAddress(address);
        }

        User savedUser = userRepository.save(user);
        return toUserResponse(savedUser);
    }

    @GetMapping("/dashboard/stats")
    public DashboardStatsResponse getDashboardStats() {
        long totalUsers = userRepository.count();
        long activeVendors = userRepository.findByRoleAndStatus("Merchant", "APPROVED").size();
        long pendingVendors = userRepository.findByRoleAndStatus("Merchant", "PENDING").size();
        return new DashboardStatsResponse(
                totalUsers,
                activeVendors,
                pendingVendors,
                0, // products pending — inventory service not yet available
                0.0, // platform revenue — analytics service not yet available
                0, // total products
                0.0, // total sales
                0, // active orders
                0  // products low stock
        );
    }

    private UserResponse toUserResponse(User user) {
        AddressDTO addressDTO = null;
        if (user.getAddress() != null) {
            addressDTO = new AddressDTO(
                    user.getAddress().getStreet(),
                    user.getAddress().getCity(),
                    user.getAddress().getState(),
                    user.getAddress().getCountry(),
                    user.getAddress().getZipCode()
            );
        }
        return new UserResponse(
                user.getId(),
                user.getUuid(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getPhone(),
                addressDTO
        );
    }
}
