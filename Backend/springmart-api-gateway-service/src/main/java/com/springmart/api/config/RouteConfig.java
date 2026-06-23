package com.springmart.api.config;
import org.springframework.context.annotation.*; 
import org.springframework.cloud.gateway.route.*;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
@Configuration
public class RouteConfig {
@Bean 
RouteLocator routes(RouteLocatorBuilder b) {
	return b.routes()
			.route("auth",r->r.path("/auth/**").uri("lb://SPRINGMART-AUTHUSER-SERVICE"))
			.route("users",r->r.path("/users/**").uri("lb://SPRINGMART-AUTHUSER-SERVICE"))
			.route("product",r->r.path("/api/products/**").uri("lb://SPRINGMART-PRODUCT-SERVICE"))
			.route("categories",r->r.path("/api/categories/**").uri("lb://SPRINGMART-PRODUCT-SERVICE"))
			.route("brands",r->r.path("/api/brands/**").uri("lb://SPRINGMART-PRODUCT-SERVICE"))
			.route("cart",r->r.path("/cart/**").uri("lb://SPRINGMART-CART-SERVICE"))
			.route("order",r->r.path("/orders/**").uri("lb://SPRINGMART-ORDER-SERVICE"))
			.build();
	}
}
