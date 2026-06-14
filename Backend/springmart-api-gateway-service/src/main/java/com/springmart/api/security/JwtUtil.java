package com.springmart.api.security;
import io.jsonwebtoken.*; 
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
public class JwtUtil {
	private static final SecretKey KEY=Keys.hmacShaKeyFor("springmart-secret-key-springmart-secret-key".getBytes());
	public static Claims validate(String token) {
		return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload();
	}
}
