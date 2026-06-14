package com.springmart.authuser.entity; 

import jakarta.persistence.*; 
import lombok.*; 

@Entity 
@Data 
public class RefreshToken { 
	@Id @GeneratedValue 
	Long id; 
	String token;
}