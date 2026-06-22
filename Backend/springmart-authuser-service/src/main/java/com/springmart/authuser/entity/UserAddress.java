package com.springmart.authuser.entity; 

import jakarta.persistence.*; 
import lombok.*; 

@Entity 
@Data 
public class UserAddress { 
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private String street;
	private String city; 
	private String state; 
	private String country;
	private String zipCode;
}