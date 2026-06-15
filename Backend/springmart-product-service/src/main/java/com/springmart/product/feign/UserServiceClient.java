package com.springmart.product.feign;

import com.springmart.product.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SPRINGMART-AUTHUSER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/api/users/{userId}")
    ApiResponse<Object> getUserDetails(@PathVariable("userId") String userId);
}
