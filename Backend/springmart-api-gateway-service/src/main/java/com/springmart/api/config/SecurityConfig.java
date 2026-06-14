package com.springmart.api.config;
import org.springframework.context.annotation.*; 
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
@Bean 
SecurityWebFilterChain security(ServerHttpSecurity http) {
	return http.csrf(cs->cs.disable()).authorizeExchange(ex->ex.pathMatchers("/actuator/**","/auth/**").permitAll().anyExchange().authenticated()).build();
	}
}
