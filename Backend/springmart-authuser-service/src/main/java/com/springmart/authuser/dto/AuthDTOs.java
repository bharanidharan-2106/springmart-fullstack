package com.springmart.authuser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

public class AuthDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String role;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private String token;
        private String email;
        private String role;
        private String firstName;
        private String lastName;
        private String uuid;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse {
        private Long id;
        private String uuid;
        private String firstName;
        private String lastName;
        private String email;
        private String role;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateUserStatusRequest {
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DashboardStatsResponse {
        private long totalUsers;
        private long activeVendors;
        private long pendingVendors;
        private long productsPending;
        private double platformRevenue;
        private long totalProducts;
        private double totalSales;
        private long activeOrders;
        private long productsLowStock;
    }
}
