package com.springmart.api.filter;
import org.springframework.cloud.gateway.filter.*; 
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange; 
import reactor.core.publisher.Mono;
@Component
public class JwtAuthenticationFilter implements GlobalFilter {
	public Mono<Void> filter(ServerWebExchange ex, GatewayFilterChain chain) { 
		return chain.filter(ex);
	}
}
